#include "Joystick.h"

int i;


void setup() {
  Joystick.begin();
  Serial.begin(9600);
  pinMode(2, INPUT_PULLUP);
  pinMode(3, INPUT_PULLUP);
  pinMode(4, INPUT_PULLUP);
  pinMode(5, INPUT_PULLUP);
}
  
void loop() {
  int sensorVal1 = (digitalRead(2) *8);
  int sensorVal2 = (digitalRead(3) *4);
  int sensorVal3 = (digitalRead(4) *2);
  int sensorVal4 = digitalRead(5);

  int buttonPressed = ((sensorVal1 + sensorVal2 + sensorVal3 + sensorVal4)*-1 +15); 

  for (i=0;i<16;i++) {
    if (i==buttonPressed) {
      Joystick.pressButton(buttonPressed);
    } else {
      Joystick.releaseButton(i);
    }
  }
      
  Serial.print("Value = ");
  Serial.println(buttonPressed);
}
