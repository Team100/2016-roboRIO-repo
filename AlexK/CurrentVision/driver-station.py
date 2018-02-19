import cv2
from json import loads
from networktables import NetworkTables
from time import sleep

NetworkTables.initialize(server="raspberrypi.local")
sleep(5)
table = NetworkTables.getTable("Camera")
camera = cv2.VideoCapture("http://raspberrypi.local:5802/?action=stream")

while True:
    g, frame = camera.read()

    cube = loads(table.getValue("JSON", "[]"))
    switch = loads(table.getValue("Switch", "[]"))

    for target in cube:
        cv2.rectangle(frame, (target["BboxCoordinates"][0], target["BboxCoordinates"][1]),
                      (target["BboxCoordinates"][0] + target["BboxCoordinates"][2],
                       target["BboxCoordinates"][1] + target["BboxCoordinates"][3]), (255, 0, 255), 2)

    for target in switch:
        cv2.rectangle(frame, (target["BboxCoordinates"][0], target["BboxCoordinates"][1]),
                      (target["BboxCoordinates"][0] + target["BboxCoordinates"][2],
                       target["BboxCoordinates"][1] + target["BboxCoordinates"][3]), (0, 255, 0), 2)

    cv2.imshow("Video", frame)
    if cv2.waitKey(1) & 0xFF == ord('q'):
        cv2.destroyAllWindows()
        camera.release()
        break