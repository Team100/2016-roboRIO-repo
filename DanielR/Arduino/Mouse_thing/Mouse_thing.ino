#include "Joystick.h"
#include "Keyboard.h"
#include "Mouse.h"



const int analogInPin1 = A1;
const int buttonPin = 2;
const int analogInPin0 = A0;

int buttonState = 0;
int sensorValue = 0;
int buttonPressed;
int i;
int type;
int ecks;
int whyy;
int xValue;
int yValue;

void setup() {
  Joystick.begin();
  Serial.begin(9600);
  pinMode(buttonPin, INPUT);
}


void loop() {
  ecks = analogRead(analogInPin0);
  whyy = analogRead(analogInPin1);
  xValue = map(ecks, 0, 1023, -1, 1);
  yValue = map(whyy, 0, 1023, -1, 1);
  
  Joystick.setXAxis(xValue);
  Joystick.setYAxis(yValue);
  Mouse.move(xValue,0,0);
  Mouse.move(0,yValue,0);
  buttonState = digitalRead(buttonPin);
  for (i=0;i<1;i++) {
    if (i=buttonState) {
      Mouse.press();
    } else {
      Mouse.release();
    }
    
  }
  
}
