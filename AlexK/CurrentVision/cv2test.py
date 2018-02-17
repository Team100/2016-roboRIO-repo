import cv2
from json import loads
from networktables import NetworkTables
from time import sleep

camera = cv2.VideoCapture("http://raspberrypi.local:5802/?action=stream")
NetworkTables.initialize(server="raspberrypi.local")
sleep(5)
table = NetworkTables.getTable("Camera")

while True:
    g, frame = camera.read()

    json = loads(table.getValue("JSON", "[]"))

    for target in json[:1]:
        cv2.rectangle(frame, (target["BboxCoordinates"][0], target["BboxCoordinates"][1]),
                      (target["BboxCoordinates"][0] + target["BboxCoordinates"][2],
                       target["BboxCoordinates"][1] + target["BboxCoordinates"][3]), (255, 0, 255), 2)
        cv2.putText(frame, "Largest Target Data:", (0, 195), cv2.FONT_HERSHEY_COMPLEX_SMALL, 0.6, (0, 255, 0))
        cv2.putText(frame,
                    "Center Pixel: (" + str(target["CenterPixel"][0]) + ", " + str(target["CenterPixel"][1]) + ")",
                    (0, 235), cv2.FONT_HERSHEY_COMPLEX_SMALL, 0.6, (0, 255, 0))
        cv2.putText(frame, "Timestamp: " + str(round(target["Timestamp"], 2)), (0, 225),
                    cv2.FONT_HERSHEY_COMPLEX_SMALL, 0.6, (0, 255, 0))
        cv2.putText(frame, "Distance: " + str(target["Distance"]), (0, 215),
                    cv2.FONT_HERSHEY_COMPLEX_SMALL, 0.6, (0, 255, 0))
        cv2.putText(frame, "Angle: " + str(target["Angle"]), (0, 205), cv2.FONT_HERSHEY_COMPLEX_SMALL, 0.6, (0, 255, 0))

    cv2.imshow("Video", frame)
    if cv2.waitKey(1) & 0xFF == ord('q'):
        cv2.destroyAllWindows()
        camera.release()
        break
