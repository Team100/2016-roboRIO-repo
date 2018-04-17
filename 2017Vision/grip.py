import cv2
import numpy as np
import math

class GripPipeline:
        
    def __init__(self):
        
        self.__hsv_threshold_hue = [57.0, 77.0]
        self.__hsv_threshold_saturation = [190.0, 255.0] # 0, 18
        self.__hsv_threshold_value = [205.0, 255.0]
        
        self.center = None
        self.boundingRects = None
                
        # Local Image
        #self.__hsv_threshold_hue = [44, 142]
        #self.__hsv_threshold_saturation = [0, 17.2]
        #self.__hsv_threshold_value = [91.0, 255.0]

        self.hsv_threshold_output = None

        self.__find_contours_input = self.hsv_threshold_output
        self.__find_contours_external_only = False

        self.find_contours_output = None

        self.__filter_contours_contours = self.find_contours_output
        self.__filter_contours_min_area = 0.1
        self.__filter_contours_min_perimeter = 0
        self.__filter_contours_min_width = 0
        self.__filter_contours_max_width = 1000
        self.__filter_contours_min_height = 0.0
        self.__filter_contours_max_height = 1000
        self.__filter_contours_solidity = [0, 100]
        self.__filter_contours_max_vertices = 1000000
        self.__filter_contours_min_vertices = 0
        self.__filter_contours_min_ratio = 0
        self.__filter_contours_max_ratio = 10000

        self.filter_contours_output = None

    def process(self, source0):

        """
        Runs the pipeline and sets all outputs to new values.
        """
        
        # Step HSV_Threshold0:
        self.__hsv_threshold_input = source0
        (self.hsv_threshold_output) = self.__hsv_threshold(self.__hsv_threshold_input, self.__hsv_threshold_hue, self.__hsv_threshold_saturation, self.__hsv_threshold_value)

        # Step Find_Contours0:
        self.__find_contours_input = self.hsv_threshold_output
        (self.find_contours_output) = self.__find_contours(self.__find_contours_input, self.__find_contours_external_only)

        # Step Filter_Contours0:
        self.__filter_contours_contours = self.find_contours_output
        (self.filter_contours_output) = self.__filter_contours(self.__filter_contours_contours, self.__filter_contours_min_area, self.__filter_contours_min_perimeter, self.__filter_contours_min_width, self.__filter_contours_max_width, self.__filter_contours_min_height, self.__filter_contours_max_height, self.__filter_contours_solidity, self.__filter_contours_max_vertices, self.__filter_contours_min_vertices, self.__filter_contours_min_ratio, self.__filter_contours_max_ratio)
        
        #print self.filter_contours_output
        
        tempRects = []
        self.boundingRects = []

        for cont in self.filter_contours_output:
            myRect = cv2.boundingRect(cont)
            tempRects.append(myRect)
        
        if (len(tempRects) == 3):
            largestArea = tempRects[0][2] * tempRects[0][3]
            largestRect = tempRects[0]
            
            for rect in tempRects:
                area = rect[2] * rect[3]
                if (area > largestArea):
                    largestArea = area
                    largestRect = rect
                    
            self.boundingRects.append(largestRect)
            newRects = []
            
            for rect in tempRects:
                if rect != largestRect:
                    newRects.append(rect)
            
            if (math.fabs(newRects[0][0] - newRects[1][0]) <= 8): # means the two small ones are co-linear
                if (newRects[0][1] < newRects[1][1]):
                    hr = newRects[0]
                    lr = newRects[1]
                    
                    newContArray = np.array([[hr[0], hr[1]], [hr[0]+hr[2], hr[1]], [lr[0], lr[1]+lr[3]], [lr[0]+lr[2], lr[1]+lr[3]]])
                    newCont = newContArray.reshape(-1, 1, 2).astype(np.int32)                    
                    newRect = cv2.boundingRect(newCont)
                    self.boundingRects.append(newRect)
                else:
                    hr = newRects[1]
                    lr = newRects[0]
                    newContArray = np.array([[hr[0], hr[1]], [hr[0]+hr[2], hr[1]], [lr[0], lr[1]+lr[3]], [lr[0]+lr[2], lr[1]+lr[3]]])
                    newCont = newContArray.reshape(-1, 1, 2).astype(np.int32)                    
                    newRect = cv2.boundingRect(newCont)
                    self.boundingRects.append(newRect)
            else:   # NO Co-Linear rects!
                pass 
        else:
            for rect in tempRects:
                if ((float(rect[2]) / float(rect[3])) >= 0.3 and (float(rect[2]) / float(rect[3])) <= 0.7 ):
                    self.boundingRects.append(rect)
            
        if (len(self.boundingRects) == 2):
            centerX = (self.boundingRects[0][0] + self.boundingRects[0][2]/2 + self.boundingRects[1][0] + self.boundingRects[1][2]/2)/2
            centerY = (self.boundingRects[0][1] + self.boundingRects[0][3]/2 + self.boundingRects[1][1] + self.boundingRects[1][3]/2)/2
            self.center = [centerX, centerY]  
        elif (len(self.boundingRects) == 3):
            pass
    
    @staticmethod
    def __hsv_threshold(input, hue, sat, val):
        """Segment an image based on hue, saturation, and value ranges.
        Args:
            input: A BGR numpy.ndarray.
            hue: A list of two numbers the are the min and max hue.
            sat: A list of two numbers the are the min and max saturation.
            lum: A list of two numbers the are the min and max value.
        Returns:
            A black and white numpy.ndarray.
        """
                
        out = cv2.cvtColor(input, cv2.COLOR_BGR2HSV)
        testVar = cv2.inRange(out, (hue[0], sat[0], val[0]),  (hue[1], sat[1], val[1]))
        
        return testVar

    @staticmethod
    def __find_contours(input, external_only):
        """Sets the values of pixels in a binary image to their distance to the nearest black pixel.
        Args:
            input: A numpy.ndarray.
            external_only: A boolean. If true only external contours are found.
        Return:
            A list of numpy.ndarray where each one represents a contour.
        """
       
        #cv2.namedWindow("Eureka")
        #cv2.imshow("Eureka", input)
                                
        if(external_only):
            mode = cv2.RETR_EXTERNAL
        else:
            mode = cv2.RETR_LIST
        method = cv2.CHAIN_APPROX_SIMPLE
        
        try:
            contours, hierarchy = cv2.findContours(input, mode=mode, method=method)
        except:
            u, contours, hierarchy = cv2.findContours(input, mode=mode, method=method)
        return contours

    @staticmethod
    def __filter_contours(input_contours, min_area, min_perimeter, min_width, max_width,
                        min_height, max_height, solidity, max_vertex_count, min_vertex_count,
                        min_ratio, max_ratio):
        """Filters out contours that do not meet certain criteria.
        Args:
            input_contours: Contours as a list of numpy.ndarray.
            min_area: The minimum area of a contour that will be kept.
            min_perimeter: The minimum perimeter of a contour that will be kept.
            min_width: Minimum width of a contour.
            max_width: MaxWidth maximum width.
            min_height: Minimum height.
            max_height: Maximimum height.
            solidity: The minimum and maximum solidity of a contour.
            min_vertex_count: Minimum vertex Count of the contours.
            max_vertex_count: Maximum vertex Count.
            min_ratio: Minimum ratio of width to height.
            max_ratio: Maximum ratio of width to height.
        Returns:
            Contours as a list of numpy.ndarray.
        """
        output = []
        for contour in input_contours:
            x,y,w,h = cv2.boundingRect(contour)
            if (w < min_width or w > max_width):
                continue
            if (h < min_height or h > max_height):
                continue
            area = cv2.contourArea(contour)
            if (area < min_area):
                continue
            if (cv2.arcLength(contour, True) < min_perimeter):
                continue
            hull = cv2.convexHull(contour)
            solid = 100 * area / cv2.contourArea(hull)
            if (solid < solidity[0] or solid > solidity[1]):
                continue
            if (len(contour) < min_vertex_count or len(contour) > max_vertex_count):
                continue
            ratio = (float)(w) / h
            if (ratio < min_ratio or ratio > max_ratio):
                continue
            output.append(contour)
            
        return output
# end Pipeline