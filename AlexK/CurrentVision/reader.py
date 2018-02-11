import numpy as np
import cv2
from requests import get as get_stream
from time import sleep as wait
from threading import Thread, ThreadError
from switch import Pipeline
from json import dumps as exportjson

IM_CENTER = 159.5
DPP = 0.214


class Stream(object):
    def __init__(self, u):
        self.stream = get_stream(u, stream=True)
        self.thread_cancelled = False
        self.thread = Thread(target=self.run)
        print("Stream Initialized")

    def start(self):
        self.thread.start()
        print("Receiving Stream")

    def run(self):
        recvd = b""

        while not self.thread_cancelled:
            try:
                recvd += self.stream.raw.read(1024)
                a = recvd.find(b'\xff\xd8')
                b = recvd.find(b'\xff\xd9')

                if a != -1 and b != -1:
                    jpg = recvd[a:b+2]
                    recvd = recvd[b+2:]

                    npimg = np.fromstring(jpg, dtype=np.uint8)
                    img = cv2.imdecode(npimg, cv2.IMREAD_COLOR)

                    #contours, center, att, dtt = process_grip(img)

                    #cv2.drawContours(img, contours, -1, (0, 255, 0), 3)
                    cv2.imshow("Camera", img)
                    if cv2.waitKey(1) & 0xFF == ord('q'):
                        exit(0)

            except ThreadError:
                self.thread_cancelled = True

    def is_running(self):
        return self.thread.isAlive()

    def shut_down(self):
        self.thread_cancelled = True
        while self.thread.isAlive():
            wait(1)
        return True


def process_grip(image):
    gp = Pipeline()
    contours = gp.process(image)

    cx, cy = 0, 0
    for contour in contours:
        M = cv2.moments(contour)
        cx += int(M["m10"] / M["m00"])
        cy += int(M["m01"] / M["m00"])

    cx = cx/len(contours)
    cy = cy/len(contours)

    att = (cx - IM_CENTER) * DPP
    dtt = 0

    return contours, (cx, cy), att, dtt


if __name__ == "__main__":
    url = "http://localhost:5802/?action=stream"
    stream = Stream(url)
    stream.start()
