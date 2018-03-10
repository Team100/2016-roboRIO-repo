/*
 * Copyright (c) 2017, NVIDIA CORPORATION. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

/*#include "rapidjson/document.h" // has conflixts with variables declared before
#include "rapidjson/writer.h"
#include "rapidjson/stringbuffer.h"*/
 
#include <chrono>
#include <ctime>
#include <sstream>
#include <math.h>

#include <fstream>

#include "networktables/NetworkTable.h" // MAKE SURE TO INCLUDE THIS BEFORE gl

#include "gstCamera.h"
#include "glDisplay.h"

#include "glTexture.h"

#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <unistd.h>

#include <time.h> //time
#include <sys/time.h> //time

#include "cudaMappedMemory.h"
#include "cudaNormalize.h"
#include "cudaFont.h"

#include "detectNet.h"
#include <fstream>

using namespace std;

#define DEFAULT_CAMERA 1	// -1 for onboard camera, or change to index of /dev/video V4L2 camera (>=0)		
//#define NET_IP "192.168.2.102" // ip
//#define CAMERA_POS_HEIGHT 110.5 //47 inches
#define CAMERA_WIDTH 640
#define CAMERA_HEIGHT 480
#define FOV_X_RADS 0.980413654
#define FOV_Y_RADS 0.71332305
#define FOV_Y_RADS2 0.678758546 
#define FOV_Y_RADS3 0.6

#define PI 3.14159265359

#define X_OFFSET 8 // inches

string ip = "192.168.2.101";
string roborio_ip = "roborio-100-frc.local";

shared_ptr<NetworkTable> visionTable; // root networkTable

double centerPixel [2] = {1.0,1.0} ;
double bboxCoordinates [4] = {0.0, 0.0, 0.0, 0.0};
double angle = 0.0;
//double distance = 0.0;


bool signal_recieved = false;

void sig_handler(int signo)
{
	if( signo == SIGINT )
	{
		printf("received SIGINT\n");
		signal_recieved = true;
	}
}

time_t time_since_epoch() {
	auto now = std::chrono::system_clock::now();
	return std::chrono::system_clock::to_time_t(now);
}

int main( int argc, char** argv )
{	

	stringstream ss;
	double degreesPerPixels [2] = {56.17/CAMERA_WIDTH, 40.86/CAMERA_HEIGHT}; // 0.980413654 rad x 0.71332305 rad
	double radiansPerPixels [2] = {FOV_X_RADS/CAMERA_WIDTH, FOV_Y_RADS/CAMERA_HEIGHT};
	double distance = 0.0;
	double distance2 = 0.0;
	double distance3 = 0.0;
	double distance4 = 0.0;
	double calibrated_offset = 117; // pixels
	double distFromCenter = 0.0;
	bool calibrationMode = false;
	ifstream coorFile;

	double zero_pixel = 0.0;
	double zero_x_pixel = 0.0;

	double calibrationDistance = 0.0;
	ofstream outputFile;
	double CAMERA_POS_HEIGHT = 110.5;
	double displacementX = 0.0;

/*difference = 320-(bb[0]+((bb[0]+bb[2])/2)
					diffInRadians = FOV_X_RADS*difference/CAMERA_WIDTH;
					angle = atan((-PI/180)*diffInRadians/distance2)*180/PI;*/
	double difference;
	double diffInRadians;

   	NetworkTable::SetClientMode();
   	NetworkTable::SetIPAddress(llvm::StringRef(roborio_ip));
   	
	NetworkTable::Initialize();
	visionTable = NetworkTable::GetTable("VisionResults");
	
	printf("detectnet-camera\n  args (%i):  ", argc);

	for( int i=0; i < argc; i++ )
		printf("%i [%s]  ", i, argv[i]);
		
	printf("\n\n");
	//string cal;

	//double *yeet;
	//printf("***************************************\nDistance: %s", argv[argc-1]);
	
	printf("******************************************************************************");
	printf("Number of args: %i", argc);

	if (argc > 17) { // distance param detected
		calibrationMode = true;
		//cal = argv[argc-1];
		calibrationDistance = stod(string(argv[argc-3]))*2.54;
		CAMERA_POS_HEIGHT = stod(string(argv[argc-1]))*2.54;
		//string myStr(argv[argc-1]);
		//double a, b;
		outputFile.open("/home/nvidia/Documents/coordinate.txt");
		argc=17; // reset back to amount of parameters expected by detectnet
		//sscanf(argv[argc-1],"%lf,%lf",&a,&b);
	} else {
		calibrationMode = false;
		coorFile.open("/home/nvidia/Documents/coordinate.txt");
		if (!coorFile) {
    			printf("Unable to open file coordinate.txt");
    			exit(1);   // call system to stop
		}
		coorFile >> zero_pixel;	// y
		coorFile >> zero_x_pixel; // x
	}
	
	printf("\nCalibration distance: %f\nCAM_POS_HEIGHT: %f", calibrationDistance, CAMERA_POS_HEIGHT);
	

	//if (argv[argc-1] == fgf)

	/*
	 * parse network type from CLI arguments
	 */
	/*detectNet::NetworkType networkType = detectNet::PEDNET_MULTI;

	if( argc > 1 )
	{
		if( strcmp(argv[1], "multiped") == 0 || strcmp(argv[1], "pednet") == 0 || strcmp(argv[1], "multiped-500") == 0 )
			networkType = detectNet::PEDNET_MULTI;
		else if( strcmp(argv[1], "ped-100") == 0 )
			networkType = detectNet::PEDNET;
		else if( strcmp(argv[1], "facenet") == 0 || strcmp(argv[1], "facenet-120") == 0 || strcmp(argv[1], "face-120") == 0 )
			networkType = detectNet::FACENET;
	}*/
	
	if( signal(SIGINT, sig_handler) == SIG_ERR )
		printf("\ncan't catch SIGINT\n");


	/*
	 * create the camera device
	 */
	gstCamera* camera = gstCamera::Create(DEFAULT_CAMERA);
	
	if( !camera )
	{
		printf("\ndetectnet-camera:  failed to initialize video device\n");
		return 0;
	}
	
	printf("\ndetectnet-camera:  successfully initialized video device\n");
	printf("    width:  %u\n", camera->GetWidth());
	printf("   height:  %u\n", camera->GetHeight());
	printf("    depth:  %u (bpp)\n\n", camera->GetPixelDepth());
	

	/*
	 * create detectNet
	 */
	detectNet* net = detectNet::Create(argc, argv);
	
	if( !net )
	{
		printf("detectnet-camera:   failed to initialize imageNet\n");
		return 0;
	}

	/*
	 * allocate memory for output bounding boxes and class confidence
	 */
	const uint32_t maxBoxes = net->GetMaxBoundingBoxes();		printf("maximum bounding boxes:  %u\n", maxBoxes);
	const uint32_t classes  = net->GetNumClasses();
	
	float* bbCPU    = NULL;
	float* bbCUDA   = NULL;
	float* confCPU  = NULL;
	float* confCUDA = NULL;
	
	if( !cudaAllocMapped((void**)&bbCPU, (void**)&bbCUDA, maxBoxes * sizeof(float4)) ||
	    !cudaAllocMapped((void**)&confCPU, (void**)&confCUDA, maxBoxes * classes * sizeof(float)) )
	{
		printf("detectnet-console:  failed to alloc output memory\n");
		return 0;
	}
	

	/*
	 * create openGL window
	 */
	glDisplay* display = glDisplay::Create();
	glTexture* texture = NULL;
	
	if( !display ) {
		printf("\ndetectnet-camera:  failed to create openGL display\n");
	}
	else
	{
		texture = glTexture::Create(camera->GetWidth(), camera->GetHeight(), GL_RGBA32F_ARB/*GL_RGBA8*/);

		if( !texture )
			printf("detectnet-camera:  failed to create openGL texture\n");
	}
	
	
	/*
	 * create font
	 */
	cudaFont* font = cudaFont::Create();
	

	/*
	 * start streaming
	 */
	if( !camera->Open() )
	{
		printf("\ndetectnet-camera:  failed to open camera for streaming\n");
		return 0;
	}
	
	printf("\ndetectnet-camera:  camera open for streaming\n");
	
	/*
	 * processing loop
	 */
	float confidence = 0.0f;

	while( !signal_recieved )
	{
		void* imgCPU  = NULL;
		void* imgCUDA = NULL;
		
		// get the latest frame
		if( !camera->Capture(&imgCPU, &imgCUDA, 1000) )
			printf("\ndetectnet-camera:  failed to capture frame\n");

		// convert from YUV to RGBA
		void* imgRGBA = NULL;
		
		if( !camera->ConvertRGBA(imgCUDA, &imgRGBA) )
			printf("detectnet-camera:  failed to convert from NV12 to RGBA\n");

		// classify image with detectNet
		int numBoundingBoxes = maxBoxes;
	
		if( net->Detect((float*)imgRGBA, camera->GetWidth(), camera->GetHeight(), bbCPU, &numBoundingBoxes, confCPU))
		{		
//			printf("%i bounding boxes detected\n", numBoundingBoxes);
		
			int lastClass = 0;
			int lastStart = 0;
			
			/*for (int i = 0; i <= numBoundingBoxes-objects.size(); i++) { // fill in missing NetworkTables
				shared_ptr<NetworkTable> object;
				objects.push_back(object);
			}*/
			
			string visionString = "";
			
			for( int n=0; n < numBoundingBoxes; n++ )
			{
				const int nc = confCPU[n*2+1];
				float* bb = bbCPU + (n * 4);
				
				if (calibrationMode == true){
					zero_pixel = abs(bb[3]-atan(CAMERA_POS_HEIGHT/calibrationDistance)*CAMERA_HEIGHT/FOV_Y_RADS2);
					outputFile << zero_pixel; //y 
					outputFile << endl;
					outputFile << bb[0] + (bb[2]-bb[0])/2;
					outputFile.close();
					printf("Zero x pixel: %f\n", bb[0] + (bb[2]-bb[0])/2);
					printf("Zero y pixel: %f\n", zero_pixel);
					//printf("TestDistance: %f\n", CAMERA_POS_HEIGHT/(tan((abs(bb[3]-zero_pixel))*FOV_Y_RADS2/CAMERA_HEIGHT))/2.54);
					//break;
				} else {
					
					//printf("bounding box %i   (%f, %f)  (%f, %f)  w=%f  h=%f\n", n, bb[0], bb[1], bb[2], bb[3], bb[2] - bb[0], bb[3] - bb[1]); 

					centerPixel[0] = bb[0]+(bb[2]-bb[0])/2;
					centerPixel[1] = (bb[3]-bb[1])/2;
				
					//distance = (CAMERA_HEIGHT/(tan((abs(bb[3]-(CAMERA_HEIGHT/2)))*radiansPerPixels[1])))/100;
					//distance = CAMERA_POS_HEIGHT/(tan((abs(bb[3]-180))*FOV_Y_RADS2/CAMERA_HEIGHT));
					distance2 = CAMERA_POS_HEIGHT/(tan((abs(bb[3]-zero_pixel))*FOV_Y_RADS2/CAMERA_HEIGHT)); // zero_pixel

					//distance3 = CAMERA_POS_HEIGHT/(tan((abs(bb[3]-180))*FOV_Y_RADS/CAMERA_HEIGHT));
					//distance4 = CAMERA_POS_HEIGHT/(tan((abs(bb[3]-150))*FOV_Y_RADS3/CAMERA_HEIGHT));
				
				
					//distFromCenter = abs((CAMERA_WIDTH/2)-((bb[2]-bb[0])/2)+bb[0]);
					printf("*************************************************************\n");
					difference = centerPixel[0]-zero_x_pixel;
					diffInRadians = (FOV_X_RADS/CAMERA_WIDTH)*difference;
					
					angle = diffInRadians*180/PI;
					//angle = atan2(diffInRadians, distance2/2.54)*-18000/PI;

					printf("\nDif in pix: %f", difference);
					printf("/nDiff in degrees: %f", diffInRadians*180/PI);

					//angle = atan((-180*FOV_X_RADS/(CAMERA_WIDTH*PI))*(320-bb[0]-(bb[0]+bb[2])/2)/distance2);
					//angle = (atan((CAMERA_WIDTH/FOV_Y_RADS)*(320-(bb[0]+(bb[2]-bb[0])/2))/(distance2*2.54))*(-180/PI));
					//displacementX = (FOV_X_RADS/CAMERA_WIDTH)*(320-bb[0]-(bb[0]+bb[2])/2);
					displacementX = (tan(diffInRadians*1.107)*distance2*1.94*2.54/10); //inches
					angle = sqrt(distance2*distance2+displacementX*displacementX); 
					printf("\nAngle1: %f \nDisplacementX: %f in.\n", angle, displacementX);
					/*angle = (atan(0.95*(320-(bb[0]+(bb[2]-bb[0])/2))/(distance2*2.54))*(-180/PI));
					displacementX = (tan(angle*PI/180)*distance2/2.54); //inches
					printf("\nAngle2: %f \nDisplacementX: %f in.\n", angle, displacementX);
					angle = (atan(1.*(320-(bb[0]+(bb[2]-bb[0])/2))/(distance2*2.54))*(-180/PI));
					displacementX = (tan(angle*PI/180)*distance2/2.54); //inches
					printf("\nAngle3: %f \nDisplacementX: %f in.\n", angle, displacementX);
					angle = (atan(1.4*(320-(bb[0]+(bb[2]-bb[0])/2))/(distance2*2.54))*(-180/PI));
					displacementX = (tan(angle*PI/180)*distance2/2.54); //inches
					printf("\nAngle4: %f \nDisplacementX: %f in.\n", angle, displacementX);*/
					printf("************************************************************\n");
					//angle = (atan((320-(bb[0]+(bb[2]-bb[0])/2))/distance2)*(-180/PI));
					
					//angle = centerPixel[0]*FOV_X_RADS/CAMERA_WIDTH;
					//angle = atan(abs(360-centerPixel[1])/distance2);
					//angle = angle *180/PI;
				

					//angle = tan(abs(centerPixel[0]-(CAMERA_WIDTH/2)))*180/PI;
				
					//CAMERA_POS_HEIGHT/tan(abs(bb[3]-18)*FOV_Y_RADS/CAMERA_HEIGHT)

					/*bboxCoordinates[0] = *bb[0];
					bboxCoordinates[1] = *bb[1];
					bboxCoordinates[2] = *bb[2];
					bboxCoordinates[3] = *bb[3];
				
					bbLeft = *bb[0];
					bbTop= *bb[1];
					bbRight = *bb[2];
					bbBottom = *bb[3];*/
				
				
					ss << time_since_epoch();
					visionString += string("[");

					for (int i = 0; i < numBoundingBoxes; i++){
						string jsonObject = string("\n\t{\n\t\t\"angle\": ")+to_string(angle)+string(",\n\t\t\"distance\": ")+to_string(distance2)+string(",\n\t\t\"displacementX\": ")+to_string(displacementX)+string(",\n\t\t\"timestamp\": ")+ss.str()+string(",\n\t\t\"centerPixel\": [")+to_string(centerPixel[0])+string(", ")+to_string(centerPixel[1])+string("],\n\t\t\"bboxCoordinates\": [")+to_string(bb[0])+string(", ")+to_string(bb[1])+string(", ")+to_string(bb[2])+string(", ")+std::to_string(bb[3])+string("]\n\t},");
						visionString+=jsonObject;
					}
				
					//printf("\nDistance: %f", distance/2.54);
					printf("\nDistance : %f in.\nZero_Pix: %f\n", distance2/2.54, zero_pixel);
					printf("\nAngle: %f \nDisplacementX: %f in.\n", angle, displacementX);
					//printf("\nDistance3: %f", distance3/2.54);
					//printf("\nDistance4: %f", distance4/2.54);
					//printf("\nCenter Pixel[0]: %f\n", centerPixel[0]);
					//printf("\nbb[2]: %f\n", centerPixel[0]-CAMERA_WIDTH/2);
					//printf("\nFOV_X_DEGREES: %f\nCenterPix: %f\nDistFromCenter: %f px.\nAngle: %f\n degrees", FOV_X_RADS*180/PI, bb[0]+(bb[2]-bb[0])/2, 320-(bb[0]+(bb[2]-bb[0])/2), (atan((320-(bb[0]+(bb[2]-bb[0])/2))/distance2)*(-180/PI)));
				
					visionString.pop_back();
					visionString+=string("\n]");
					//printf("%s\n", visionString.c_str());

					visionTable -> PutString("VisionString", visionString);
					visionString.clear();
					//objects[n] -> PutNumber("Angle", angle);
					//objects[n] -> PutNumber("Distance", distance);
					//objects[n] -> PutNumber("Timestamp", time_since_epoch());
				}
				
				if( nc != lastClass || n == (numBoundingBoxes - 1) )
				{
					if( !net->DrawBoxes((float*)imgRGBA, (float*)imgRGBA, camera->GetWidth(), camera->GetHeight(), 
						                        bbCUDA + (lastStart * 4), (n - lastStart) + 1, lastClass) )
						printf("detectnet-console:  failed to draw boxes\n");
						
					lastClass = nc;
					lastStart = n;

					CUDA(cudaDeviceSynchronize());
				}
			}
		
			/*if( font != NULL )
			{
				char str[256];
				sprintf(str, "%05.2f%% %s", confidence * 100.0f, net->GetClassDesc(img_class));
				
				font->RenderOverlay((float4*)imgRGBA, (float4*)imgRGBA, camera->GetWidth(), camera->GetHeight(),
								    str, 10, 10, make_float4(255.0f, 255.0f, 255.0f, 255.0f));
			}*/
			
			if( display != NULL )
			{
				char str[256];
				sprintf(str, "TensorRT build %x | %s | %04.1f FPS", NV_GIE_VERSION, net->HasFP16() ? "FP16" : "FP32", display->GetFPS());
				//sprintf(str, "GIE build %x | %s | %04.1f FPS | %05.2f%% %s", NV_GIE_VERSION, net->GetNetworkName(), display->GetFPS(), confidence * 100.0f, net->GetClassDesc(img_class));
				display->SetTitle(str);	
			}	
		}	


		// update display
		if( display != NULL )
		{
			display->UserEvents();
			display->BeginRender();

			if( texture != NULL )
			{
				// rescale image pixel intensities for display
				CUDA(cudaNormalizeRGBA((float4*)imgRGBA, make_float2(0.0f, 255.0f), 
								   (float4*)imgRGBA, make_float2(0.0f, 1.0f), 
		 						   camera->GetWidth(), camera->GetHeight()));

				// map from CUDA to openGL using GL interop
				void* tex_map = texture->MapCUDA();

				if( tex_map != NULL )
				{
					cudaMemcpy(tex_map, imgRGBA, texture->GetSize(), cudaMemcpyDeviceToDevice);
					texture->Unmap();
				}

				// draw the texture
				texture->Render(100,100);		
			}

			display->EndRender();
		}
	}
	
	printf("\ndetectnet-camera:  un-initializing video device\n");
	
	
	/*
	 * shutdown the camera device
	 */
	if( camera != NULL )
	{
		delete camera;
		camera = NULL;
	}

	if( display != NULL )
	{
		delete display;
		display = NULL;
	}
	
	printf("detectnet-camera:  video device has been un-initialized.\n");
	printf("detectnet-camera:  this concludes the test of the video device.\n");
	return 0;
}


