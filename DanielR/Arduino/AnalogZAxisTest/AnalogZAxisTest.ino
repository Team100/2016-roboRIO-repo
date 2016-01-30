//testing analog into joystick

#include "Joystick.h"

const int analogInPin = A1;

int sensorValue = 0;
int buttonPressed = sensorValue/32;


void setup() {
  Joystick.begin();
  Serial.begin(9600);
}

void loop() {
  
  sensorValue = analogRead(analogInPin);
  Joystick.pressButton(sensorValue/32 );
  Joystick.releaseButton();

  Serial.print("sensor = ");
  Serial.print(sensorValue);

  delay(2);


  
}
