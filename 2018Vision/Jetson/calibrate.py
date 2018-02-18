import cv2

cap = cv2.VideoCapture(1)
cv2.namedWindow("frame")
screenX = 0
screenY = 0

def getPoint(event, x, y, flags, param):
	print("xy: (" + str(x) + ", " + str(y) + ")")
	#print("(x, y)= (" + str(x) + ", " + str(y)  + ")\nScreen_x,y=("+str(screenX)+", "+str(screenY)+")")
	#cv2.putText(frame, "hello", (40, 50), cv2.FONT_HERSHEY_SIMPLEX, 1, (244, 70, 54), 2, 1)	

cv2.setMouseCallback("frame", getPoint)

while(True):
	ret, frame = cap.read()
	if ret:	
		cv2.imshow("frame", frame)
	if cv2.waitKey(1) & 0xFF == ord('q'):
		cap.release()
		break
cv2.destroyAllWindows()	
