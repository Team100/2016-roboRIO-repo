import cv2
from networktables import NetworkTables
from time import sleep as wait
from cube import Pipeline

import logging
logging.basicConfig(level=logging.DEBUG)

IM_CENTER = 159.5
DPP = 0.214
CAMERA_URI = 0
NT_URI = "roborio-100-frc.local"


def process_grip(image):
    gp = Pipeline()
    contours = gp.process(image)

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

    return (x, y, w, h), (cx, cy), att, dtt


def push_networktables(table, rect, centers, ang, dist):
    table.putValue("corners", rect)
    table.putNumber("cx", centers[0])
    table.putNumber("cy", centers[1])
    table.putNumber("angle", ang)
    table.putNumber("distance", dist)


if __name__ == "__main__":
    NetworkTables.initialize(server=NT_URI)
    cameraTable = NetworkTables.getTable("Camera")
    wait(5)
    camera = cv2.VideoCapture(CAMERA_URI)

    while True:
        g, frame = camera.read()

        rect, centers, att, dtt = process_grip(frame)

        push_networktables(cameraTable, rect, centers, att, dtt)

        cv2.rectangle(frame, (rect[0], rect[1]),
                      (rect[0] + rect[2], rect[1] + rect[3]), (0, 255, 0), 2)

        cv2.imshow("Video", frame)
        if cv2.waitKey(1) & 0xFF == ord('q'):
            cv2.destroyAllWindows()
            camera.release()
            break
