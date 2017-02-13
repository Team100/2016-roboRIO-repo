#!/usr/bin/python3

"""
Simple skeleton program for running an OpenCV pipeline generated by GRIP and using NetworkTables to send data.

Users need to:

1. Import the generated GRIP pipeline, which should be generated in the same directory as this file.
2. Set the network table server IP. This is usually the robots address (roborio-TEAM-frc.local) or localhost
3. Handle putting the generated code into NetworkTables
"""

import cv2
import numpy as np
import math
import urllib

from networktables import NetworkTable
from grip import GripPipeline  

print("OpenCV version: " + str(cv2.__version__))

version = int(cv2.__version__[:1])

ip = '127.0.0.1'
roboRio = 'roborio-100-frc.local'
piLoc = 'http://raspberrypi.local:5802/?action=stream'
beagleLoc = 'http://beaglebone.local:1180/?action=stream'

"""         Program constants       """
CAMERA_RESOLUTION = [310.0, 220.0] # 320 x 240
CAMERA_FOV_DEGREES = [52.45, 38.89] # 50.7, 39.9
HEIGHT_OF_OBJECT = 5.0 # inches
WIDTH_TO_WIDTH = 8.25 # inches

CAMERA_MATRIX = np.matrix('371.27525346 0.0 149.83727392; 0.0 347.86519908 109.9035288; 0.0 0.0 1.0'); # [[371.27525346, 0.0, 149.83727392], [0.0, 347.86519908, 109.9035288], [0.0, 0.0, 1.0]]
DISTORTION_CONSTANTS = np.array([0.13046587, -0.43432374, 0.00496624, -0.00340689, -0.42704272]);

def main():
    try:
        NetworkTable.setTeam(100) 
        NetworkTable.setIPAddress('169.254.2.14')
        NetworkTable.setClientMode()
        NetworkTable.initialize()
        print("Initializing Network Tables...")
    except:
        print("Network Tables already initialized")
        pass
    
    sd = NetworkTable.getTable('GRIP/myContoursReport')
    
    try:
        stream = urllib.urlopen(piLoc)
    except:
        print "NO CONNECTION"
        
    bytes = ''
    
    pipeline = GripPipeline()
    
    while stream:
        bytes += stream.read(1024)
        a = bytes.find('\xff\xd8')
        b = bytes.find('\xff\xd9')
        
        if a!=-1 and b!=-1:
            jpg = bytes[a:b+2]
            bytes = bytes[b+2:]
            
            if (version == 2):
                uncorrectedImage = cv2.imdecode(np.fromstring(jpg, dtype=np.uint8), cv2.CV_LOAD_IMAGE_COLOR)
            else:
                uncorrectedImage = cv2.imdecode(np.fromstring(jpg, dtype=np.uint8), cv2.IMREAD_COLOR)
            
            h, w = uncorrectedImage.shape[:2]
            
            newcameramtx, roi = cv2.getOptimalNewCameraMatrix(CAMERA_MATRIX, DISTORTION_CONSTANTS, (w, h), 1, (w, h))
            correctedImage = cv2.undistort(uncorrectedImage, CAMERA_MATRIX, DISTORTION_CONSTANTS, None, newcameramtx)
            
            pipeline.process(correctedImage)  
            
            cv2.rectangle(correctedImage, (int(CAMERA_RESOLUTION[0]/2-2), int(CAMERA_RESOLUTION[1]/2 - 2)), (int(CAMERA_RESOLUTION[0]/2+2), int(CAMERA_RESOLUTION[1]/2 + 2)), (150,150,0), 5)
            
            print("Number of Bounding Rects: " + str(len(pipeline.boundingRects)))
            
            if len(pipeline.boundingRects) == 2:
                
                if (pipeline.center != None):
                    distanceFromCenterPixels = math.fabs(CAMERA_RESOLUTION[0]/2 - pipeline.center[0])
                    degreeOffset = CAMERA_FOV_DEGREES[0] * distanceFromCenterPixels / CAMERA_RESOLUTION[0]
                    cv2.line(correctedImage, (int(CAMERA_RESOLUTION[0]/2), int(CAMERA_RESOLUTION[1]/2)), (int(pipeline.center[0]), int(CAMERA_RESOLUTION[1]/2)),(255,0,0), 3, 8, 0)

                   # print("Degrees from center: " + str(degreeOffset))
                    sd.putNumberArray("Center", pipeline.center)
                    cv2.rectangle(correctedImage, (pipeline.center[0]-2, pipeline.center[1]-2), (pipeline.center[0]+2, pipeline.center[1]+2), (0,0,255), 5)
                
                for rects in pipeline.boundingRects:
                    cv2.rectangle(correctedImage, (rects[0], rects[1]), (rects[0]+rects[2], rects[1]+rects[3]), (0,0,255), 2)
                    
                sendableCenterX = []
                sendableCenterY = []
                sendableWidth = []
                sendableHeight = []
                
                for r in pipeline.boundingRects:
                    sendableCenterX.append((r[0] + r[2]/2))
                    sendableCenterY.append((r[1] + r[3]/2))
                    sendableWidth.append(r[2])
                    sendableHeight.append(r[3])
                                
                try:
                    sd.putNumberArray("centerX", sendableCenterX)
                    sd.putNumberArray("centerY", sendableCenterY)
                    sd.putNumberArray("width", sendableWidth)
                    sd.putNumberArray("height", sendableHeight)
                    sd.putNumberArray("degreeOffset", degreeOffset)
                except:
                    pass
                 
                theta = ((CAMERA_FOV_DEGREES[1] * sendableHeight[0]) / CAMERA_RESOLUTION[1])/2
                distanceToTarget = ((HEIGHT_OF_OBJECT/2) / (math.tan(theta * math.pi / 180.0)))
                
                dist = math.fabs(sendableCenterX[0] - sendableCenterX[1]);
                
                if (dist): 
                    theta2 = ((CAMERA_FOV_DEGREES[0] * dist) / CAMERA_RESOLUTION[0])/2
                    distanceToTarget2 = ((WIDTH_TO_WIDTH/2) / (math.tan(theta2 * math.pi / 180)))
                    cv2.putText(correctedImage, "Distance2: " + str("%.1f" % distanceToTarget2), (60, 180), cv2.FONT_HERSHEY_SIMPLEX, 0.7, (250, 60, 40), 1)
                                                
                    #print(distanceToTarget2)
                    #font = cv2.InitFont(cv2.CV_FONT_HERSHEY_SIMPLEX, 1, 1, 0, 3, 8)
                    cv2.putText(correctedImage, "Distance: " + str("%.1f" % distanceToTarget), (60, 160), cv2.FONT_HERSHEY_SIMPLEX, 0.7, (250, 60, 40), 1)
                    #print("Distance to target: " + str("%.1f" % distanceToTarget) + " inches")
            
            else:
                for rects in pipeline.boundingRects:
                    cv2.rectangle(correctedImage, (rects[0], rects[1]), (rects[0]+rects[2], rects[1]+rects[3]), (0,0,255), 2)
                        
            # crop image
            x, y, w, h = roi
            correctedImage = correctedImage[y:y+h, x:x+w]
            
            cv2.imshow("correctedImage", correctedImage)
               
            if cv2.waitKey(1) & 0xFF == ord('q'):
               break;
    # When everything done, release the capture
        
if __name__ == '__main__':
    main()