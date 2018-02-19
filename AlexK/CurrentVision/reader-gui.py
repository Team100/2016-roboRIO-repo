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
NT_URI = "raspberrypi.local"  # NetworkTables server location
SWITCH = False  # Enable switch detection


def process_grip(image, table):
    cube = CubePipeline()
    ccontours = cube.process(image)
    ccontours.sort(key=cv2.contourArea, reverse=True)
    cdata, cbbox = compute_cube(ccontours)
    push_networktable(table, cdata, 0)

    sbbox = []
    if SWITCH:
        switch = SwitchPipeline()
        scontours = switch.process(image)
        scontours.sort(key=cv2.contourArea, reverse=True)
        sdata, sbbox = compute_switch(scontours)
        push_networktable(table, sdata, 1)

    return cbbox, sbbox


def compute_cube(contours):
    objects = []
    xywh = []

    for contour in contours[:1]:
        m = cv2.moments(contour)
        cx = int(m["m10"] / m["m00"])
        cy = int(m["m01"] / m["m00"])
        x, y, w, h = cv2.boundingRect(contour)
        att = round((cx - IM_CENTER) * HDPP, 4)
        dtt = calc_distance(y+h)

        objects.append(assemble_json(cx, cy, x, y, w, h, att, dtt))
        xywh.append([x, y, w, h])

    return objects, xywh


def compute_switch(contours):
    objects = []
    xywh = []

    for contour in contours:
        m = cv2.moments(contour)
        cx = int(m["m10"] / m["m00"])
        cy = int(m["m01"] / m["m00"])
        x, y, w, h = cv2.boundingRect(contour)
        att = round((cx - IM_CENTER) * HDPP, 4)
        dtt = 0

        objects.append(assemble_json(cx, cy, x, y, w, h, att, dtt))
        xywh.append([x, y, w, h])

    return objects, xywh


def calc_distance(from_horizontal):
    from_horizontal -= HORI_PIXEL
    rad_horizontal = from_horizontal / 6 / 57.3
    return round(CAMERA_HEIGHT / tan(rad_horizontal), 4)


def assemble_json(cx, cy, x, y, w, h, att, dtt):
    return {
        "CenterPixel": (cx, cy),
        "BboxCoordinates": (x, y, w, h),
        "Angle": att,
        "Distance": dtt,
        "Timestamp": time()
    }


def push_networktable(table, objects, cs):
    json = dumps(objects)
    if cs == 0:
        table.putString("JSON", json)
    else:
        table.putString("Switch", json)


if __name__ == "__main__":
    NetworkTables.initialize(server=NT_URI)
    cameraTable = NetworkTables.getTable("Camera")
    wait(5)
    camera = cv2.VideoCapture(CAMERA_URI)

    while True:
        try:
            g, frame = camera.read()

            c_bbox, s_bbox = process_grip(frame, cameraTable)

            for box in c_bbox:
                cv2.rectangle(frame, (box[0], box[1]),
                              (box[0] + box[2], box[1] + box[3]), (255, 0, 255), 2)

            for box in s_bbox:
                cv2.rectangle(frame, (box[0], box[1]),
                              (box[0] + box[2], box[1] + box[3]), (0, 255, 0), 2)

            cv2.imshow("Video", frame)
            if cv2.waitKey(1) & 0xFF == ord('q'):
                cv2.destroyAllWindows()
                camera.release()
                break

        except KeyboardInterrupt:
            cv2.destroyAllWindows()
            camera.release()
            break
