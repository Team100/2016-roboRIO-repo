import cv2
from random import choice
from switch import Pipeline
from pprint import PrettyPrinter
from time import sleep as delay

path = "../Vision/Target Images/"
extension = ".jpg"
images = ["Center", "Center_CloseRight", "Center_FarRight", "Center_MediumRight", "High_Angle_Close", "High_Angle_Far",
          "High_Angle_Med", "High_Clip", "High_Cross", "High_Far", "Straight_2ft", "Straight_3ft", "Straight_4ft",
          "Straight_5ft", "Straight_6ft", "Straight_7ft", "Straight_8ft", "Straight_9ft"]
images = [path + image + extension for image in images]
pipeline = Pipeline()
pp = PrettyPrinter(indent=4)

for img in images:
    print("Image:", img)
    working_image = cv2.imread(img, cv2.IMREAD_COLOR)
    contours = pipeline.process(working_image)

    cv2.drawContours(working_image, contours, -1, (0, 255, 0), 3)
    cv2.imshow("Contour", working_image)
    cv2.waitKey(1)
    delay(2)
    cv2.destroyAllWindows()
