import cv2
from json import dumps
from networktables import NetworkTables
from time import sleep as wait, time
from cube import Pipeline as CubePipeline
from switch import Pipeline as SwitchPipeline

import logging
logging.basicConfig(level=logging.DEBUG)

IM_CENTER = 159.5
DPP = 0.214
CAMERA_URI = "http://raspberrypi.local:5802/?action=stream"
NT_URI = "raspberrypi.local"

"""
def process_grip(image):
    cube = Pipeline()
    switch = Pipeline()
    contours = cube.process(image)
    if len(contours) == 0:
        contours = switch.process(image)

    cx, cy = 0, 0
    for contour in contours:
        M = cv2.moments(contour)
        cx += int(M["m10"] / M["m00"])
        cy += int(M["m01"] / M["m00"])

    if len(contours) != 0:
        x, y, w, h = cv2.boundingRect(contours[0])
        cx = cx/len(contours)
        cy = cy/len(contours)
    else:
        x, y, w, h = 0, 0, 0, 0

    att = round((cx - IM_CENTER) * DPP, 4)
    dtt = 0

    return (x, y, w, h), (cx, cy), att, dtt"""


def process_grip(image, table):
    cube = CubePipeline()
    contours = cube.process(image)
    contours.sort(key=cv2.contourArea, reverse=True)

    data, bbox = compute_cube(contours)
    push_networktable(table, data)

    return bbox


def compute_cube(contours):
    objects = []
    xywh = []

    for contour in contours:
        M = cv2.moments(contour)
        cx = int(M["m10"] / M["m00"])
        cy = int(M["m01"] / M["m00"])
        x, y, w, h = cv2.boundingRect(contour)
        att = round((cx - IM_CENTER) * DPP, 4)
        dtt = 0

        objects.append(assemble_json(cx, cy, x, y, w, h, att, dtt))
        xywh.append([x, y, w, h])

    return objects, xywh


def compute_switch(contours):
    objects = []

    for i, contour in enumerate(contours[::2]):
        cx, cy = 0, 0

        try:
            M1 = cv2.moments(contour)
            M2 = cv2.moments(contours[i+1])
            cx += int(M1["m10"] / M1["m00"])
            cx += int(M2["m10"] / M2["m00"])
            cy += int(M1["m01"] / M1["m00"])
            cy += int(M2["m01"] / M2["m00"])
        except IndexError:
            pass

        cx = int(cx/2)
        cy = int(cy/2)

        x, y, w, h = cv2.boundingRect(contour)
        att = round((cx - IM_CENTER) * DPP, 4)
        dtt = 0

        objects.append(assemble_json(cx, cy, x, y, w, h, att, dtt))


def assemble_json(cx, cy, x, y, w, h, att, dtt):
    return {
        "CenterPixel": (cx, cy),
        "BboxCoordinates": (x, y, w, h),
        "Angle": att,
        "Distance": dtt,
        "Timestamp": time()
    }


def push_networktable(table, objects):
    json = dumps(objects)
    table.putString("JSON", json)


if __name__ == "__main__":
    NetworkTables.initialize(server=NT_URI)
    cameraTable = NetworkTables.getTable("Camera")
    wait(5)
    camera = cv2.VideoCapture(CAMERA_URI)

    while True:
        try:
            g, frame = camera.read()
            cv2.resize(frame, (320, 240))

            #bbox = process_grip(frame, cameraTable)
            process_grip(frame, cameraTable)

            #for box in bbox:
            #    cv2.rectangle(frame, (box[0], box[1]),
            #                  (box[0] + box[2], box[1] + box[3]), (255, 0, 255), 2)

            #cv2.imshow("Video", frame)
            #if cv2.waitKey(1) & 0xFF == ord('q'):
            #    cv2.destroyAllWindows()
            #    camera.release()

        except KeyboardInterrupt:
            cv2.destroyAllWindows()
            camera.release()
            break
