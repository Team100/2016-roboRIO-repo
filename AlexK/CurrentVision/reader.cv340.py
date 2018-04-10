import cv2
from math import tan
from json import dumps
from networktables import NetworkTables
from time import sleep as wait, time
from cube import Pipeline as CubePipeline
from switch import Pipeline as SwitchPipeline

import logging
logging.basicConfig(level=logging.DEBUG)

IM_CENTER = 159.5  # Image width divided by 2 & subtracted by 0.5
HDPP = 0.1962  # Number of degrees
VPPD = 6  # Number of pixels
CAMERA_HEIGHT = 17  # In inches
HORI_PIXEL = 4  # Pixel horizontal (180 degrees) with camera
CAMERA_URI = "http://raspberrypi.local:5802/?action=stream"  # MJPG URL or camera number
NT_URI = "roborio-100-frc.local"  # NetworkTables server location
SWITCH = False  # Enable switch detection


def process_grip(image, table):
    # Check for cubes
    cube = CubePipeline()
    ccontours = cube.process(image)
    # Sort by contour size
    ccontours.sort(key=cv2.contourArea, reverse=True)
    # Push data from contours to NetworkTables
    cdata, cbbox = compute_cube(ccontours)
    push_networktable(table, cdata, 0)

    # Check if switch needs to be detected
    sbbox = []
    if SWITCH:
        # Check for switch vision targets
        switch = SwitchPipeline()
        scontours = switch.process(image)
        # Sort by contour size
        scontours.sort(key=cv2.contourArea, reverse=True)
        # Push data from contours to NetworkTables
        sdata, sbbox = compute_switch(scontours)
        push_networktable(table, sdata, 1)

    # Return bounding boxes
    return cbbox, sbbox


def compute_cube(contours):
    # Create lists for targets' data and bounding boxes
    objects = []
    xywh = []

    # Iterate over all contours
    for contour in contours:
        # Get center x and y
        m = cv2.moments(contour)
        cx = int(m["m10"] / m["m00"])
        cy = int(m["m01"] / m["m00"])
        # Create bounding box
        x, y, w, h = cv2.boundingRect(contour)
        # Get angle and distance to target
        att = round((cx - IM_CENTER) * HDPP, 4)
        dtt = calc_distance(y+h)

        # Add to respective lists
        objects.append(assemble_json(cx, cy, x, y, w, h, att, dtt))
        xywh.append([x, y, w, h])

    # Return target data and bounding boxes
    return objects, xywh


def compute_switch(contours):
    # Create lists for targets' data and bounding boxes
    objects = []
    xywh = []

    # Iterate over all contours
    for contour in contours:
        # Get center x and y
        m = cv2.moments(contour)
        cx = int(m["m10"] / m["m00"])
        cy = int(m["m01"] / m["m00"])
        # Create bounding box
        x, y, w, h = cv2.boundingRect(contour)
        # Get angle and distance to target
        att = round((cx - IM_CENTER) * HDPP, 4)
        dtt = calc_distance(y+h)

        # Add to respective lists
        objects.append(assemble_json(cx, cy, x, y, w, h, att, dtt))
        xywh.append([x, y, w, h])

    # Return target data and bounding boxes
    return objects, xywh


def calc_distance(from_horizontal):
    # Account for horizontal pixel offset
    from_horizontal -= HORI_PIXEL
    # Convert to radians per pixel
    rad_horizontal = from_horizontal / 6 / 57.3
    # Return distance to target
    return round((CAMERA_HEIGHT / tan(rad_horizontal)) - 2, 4)


def assemble_json(cx, cy, x, y, w, h, att, dtt):
    # Return dict of all passed in data
    return {
        "CenterPixel": (cx, cy),
        "BboxCoordinates": (x, y, w, h),
        "Angle": att,
        "Distance": dtt,
        "Timestamp": time()
    }


def push_networktable(table, objects, cs):
    # Convert to JSON
    json = dumps(objects)
    # Check if cube or switch
    if cs == 0:
        # Put in cube field
        table.putString("Cube", json)
    else:
        # Put in switch field
        table.putString("Switch", json)


if __name__ == "__main__":
    # Connect to NetworkTables
    NetworkTables.initialize(server=NT_URI)
    wait(5)
    cameraTable = NetworkTables.getTable("Camera")
    # Open camera stream
    camera = cv2.VideoCapture(CAMERA_URI)

    # Process frames forever
    while True:
        try:
            # Read frame
            _, frame = camera.read()
            # Process through GRIP code
            process_grip(frame, cameraTable)

        # Shutdown on SIGKILL
        except KeyboardInterrupt:
            cv2.destroyAllWindows()
            camera.release()
            break
