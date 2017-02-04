import cv2
import time
 
# Camera 0 is the integrated web cam on my netbook
camera_port = 0
 
#Number of frames to throw away while the camera adjusts to light levels
ramp_frames = 30
 
# Now we can initialize the camera capture object with the cv2.VideoCapture class.
# All it needs is the index to a camera port.
camera = cv2.VideoCapture(camera_port)
 
# Captures a single image from the camera and returns it in PIL format
def get_image():
 # read is the easiest way to get a full image out of a VideoCapture object.
 retval, im = camera.read()
 return im
 
# Ramp the camera - these frames will be discarded and are only used to allow v4l2
# to adjust light levels, if necessary
for i in xrange(ramp_frames):
 temp = get_image()
print("Taking image...")

# Take the actual image we want to keep

v,ig = camera.read()
#cv2.imshow('frame', ig)

time.sleep(4)

v,ig2 = camera.read()
#cv2.imshow('frame2', ig2)

newImage = cv2.subtract(ig2, ig)
cv2.imshow('subtraction', newImage)

del(camera)