import cv2


class Pipeline:
    def __init__(self):
        self.__hsv_threshold_input = None
        self.__hsv_threshold_hue = [56.6546762589928, 75.56313993174062]
        self.__hsv_threshold_saturation = [220.14388489208633, 255.0]
        self.__hsv_threshold_value = [43.57014388489208, 120.10238907849829]

        self.hsv_threshold_output = None

        self.__find_contours_input = self.hsv_threshold_output
        self.__find_contours_external_only = True

        self.find_contours_output = None

        self.__filter_contours_contours = self.find_contours_output
        self.__filter_contours_min_area = 230.0
        self.__filter_contours_min_perimeter = 120.0
        self.__filter_contours_min_width = 5.0
        self.__filter_contours_max_width = 65.0
        self.__filter_contours_min_height = 60.0
        self.__filter_contours_max_height = 250.0
        self.__filter_contours_solidity = [55.651881349413465, 100.0]
        self.__filter_contours_max_vertices = 1000000
        self.__filter_contours_min_vertices = 0
        self.__filter_contours_min_ratio = 0
        self.__filter_contours_max_ratio = 1000

        self.filter_contours_output = None

        self.__convex_hulls_contours = self.filter_contours_output

        self.convex_hulls_output = None

    def process(self, source):
        # Step HSV_Threshold0:
        self.__hsv_threshold_input = source
        (self.hsv_threshold_output) = self.__hsv_threshold(self.__hsv_threshold_input, self.__hsv_threshold_hue,
                                                           self.__hsv_threshold_saturation, self.__hsv_threshold_value)

        # Step Find_Contours0:
        self.__find_contours_input = self.hsv_threshold_output
        (self.find_contours_output) = self.__find_contours(self.__find_contours_input,
                                                           self.__find_contours_external_only)

        # Step Filter_Contours0:
        self.__filter_contours_contours = self.find_contours_output
        (self.filter_contours_output) = self.__filter_contours(self.__filter_contours_contours,
                                                               self.__filter_contours_min_area,
                                                               self.__filter_contours_min_perimeter,
                                                               self.__filter_contours_min_width,
                                                               self.__filter_contours_max_width,
                                                               self.__filter_contours_min_height,
                                                               self.__filter_contours_max_height,
                                                               self.__filter_contours_solidity,
                                                               self.__filter_contours_max_vertices,
                                                               self.__filter_contours_min_vertices,
                                                               self.__filter_contours_min_ratio,
                                                               self.__filter_contours_max_ratio)

        # Step Convex_Hulls0:
        self.__convex_hulls_contours = self.filter_contours_output
        (self.convex_hulls_output) = self.__convex_hulls(self.__convex_hulls_contours)

        return self.convex_hulls_output

    @staticmethod
    def __hsv_threshold(i, hue, sat, val):
        out = cv2.cvtColor(i, cv2.COLOR_BGR2HSV)
        return cv2.inRange(out, (hue[0], sat[0], val[0]), (hue[1], sat[1], val[1]))

    @staticmethod
    def __find_contours(source, external_only):
        if external_only:
            mode = cv2.RETR_EXTERNAL
        else:
            mode = cv2.RETR_LIST

        method = cv2.CHAIN_APPROX_SIMPLE
        im2, contours, heirarchy = cv2.findContours(source, mode=mode, method=method)
        return contours

    @staticmethod
    def __filter_contours(input_contours, min_area, min_perimeter, min_width, max_width,
                          min_height, max_height, solidity, max_vertex_count, min_vertex_count,
                          min_ratio, max_ratio):
        output = []

        for contour in input_contours:
            x, y, w, h = cv2.boundingRect(contour)
            area = cv2.contourArea(contour)
            hull = cv2.convexHull(contour)

            if cv2.contourArea(hull) == 0.0:
                continue

            solid = 100 * area / cv2.contourArea(hull)
            ratio = float(w) / h

            if w < min_width or w > max_width:
                continue
            elif h < min_height or h > max_height:
                continue
            elif area < min_area:
                continue
            elif cv2.arcLength(contour, True) < min_perimeter:
                continue
            elif solid < solidity[0] or solid > solidity[1]:
                continue
            elif len(contour) < min_vertex_count or len(contour) > max_vertex_count:
                continue
            elif ratio < min_ratio or ratio > max_ratio:
                continue

            output.append(contour)
        return output

    @staticmethod
    def __convex_hulls(input_contours):
        return [cv2.convexHull(contour) for contour in input_contours]
