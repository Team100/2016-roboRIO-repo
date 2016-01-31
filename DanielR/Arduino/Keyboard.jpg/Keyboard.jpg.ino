//testing analog into joystick

#include "Joystick.h"
#include "Keyboard.h"

const int analogInPin = A1;
const int buttonPin = 2;

int buttonState = 0;
int sensorValue = 0;
int buttonPressed;
int i;
int type;

void setup() {
  Joystick.begin();
  Serial.begin(9600);
  pinMode(buttonPin, INPUT);
}

void loop() {
  
  sensorValue = analogRead(analogInPin);
  buttonPressed = sensorValue/32 + 1;
  for (i=1;i<32;i++) {
    if (i==buttonPressed){
      Joystick.pressButton(buttonPressed);
    }
    else {
      Joystick.releaseButton(i);
    }
  }
  buttonState = digitalRead(buttonPin);

  if (buttonState == HIGH) {
    Joystick.pressButton(0);
    type = 1;
  } else {
    Joystick.releaseButton(0);
    type = 0;
  }

  if (type == 1) {
    Keyboard.press(buttonPressed + 64);
    Keyboard.release(buttonPressed + 64);
    delay(100);
  }
  Serial.print(" \n sensor = ");
  Serial.print(buttonPressed);

  delay(2);


  
}
