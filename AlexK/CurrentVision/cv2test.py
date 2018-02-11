import cv2
from networktables import NetworkTables
from time import sleep

camera = cv2.VideoCapture("http://raspberrypi.local:5802/?action=stream")
NetworkTables.initialize(server="roborio-100-frc.local")
sleep(5)
table = NetworkTables.getTable("Camera")

while True:
    g, frame = camera.read()

    rect = table.getValue("corners", (0, 0, 0, 0))
    rect = [int(x) for x in rect]
    cv2.rectangle(frame, (rect[0], rect[1]), (rect[0] + rect[2], rect[1] + rect[3]), (0, 255, 0), 2)

    cv2.imshow("Video", frame)
    if cv2.waitKey(1) & 0xFF == ord('q'):
        cv2.destroyAllWindows()
        camera.release()
        break
