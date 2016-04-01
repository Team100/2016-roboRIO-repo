#include "Joystick.h"
int u;
int i;
int o;
int a;
int e;
int y;







void setup() {
  Joystick.begin();
  
  Serial.begin(57600);
  pinMode(6, INPUT_PULLUP);
  pinMode(7, INPUT_PULLUP);
  pinMode(2, INPUT_PULLUP);
  pinMode(3, INPUT_PULLUP);
  pinMode(4, INPUT_PULLUP);
  pinMode(8, INPUT_PULLUP);
  pinMode(10, INPUT_PULLUP);
  pinMode(13, OUTPUT);
  pinMode(A4, INPUT);
}

void loop() {
  int fwrv = (digitalRead(6) +16);
  int mano = (digitalRead(7) +18);
  int sw3 = (digitalRead(10) +20);
  Serial.print("FW Reversal = ");
  Serial.println(fwrv-16);
  
  
  int sensorVal1 = (digitalRead(2) *8);
  int sensorVal2 = (digitalRead(3) *4);
  int sensorVal3 = (digitalRead(4) *2);
  int sensorVal4 = digitalRead(8);
  
  int buttonPressed = ((sensorVal1 + sensorVal2 + sensorVal3 + sensorVal4)*-1 +15); 
  
  for (i=0;i<16;i++) {
    if (i==buttonPressed) {
      Joystick.pressButton(buttonPressed);
    } else {
      Joystick.releaseButton(i);
    }
  }
  for (u=16;u<18;u++) {
    if (u==fwrv) {
      Joystick.pressButton(fwrv);
    } else {
      Joystick.releaseButton(u);
    }
  }
  for (o=18;o<20;o++) {
    if (o==mano) {
      Joystick.pressButton(mano);
    } else {
      Joystick.releaseButton(o);
    }
  }
  for (a=21;a<23;a++) {
    if (a==sw3) {
      Joystick.pressButton(sw3);
    } else {
      Joystick.releaseButton(a);
    }
  }
  
  
  /*
  int nino = map(analogRead(4), 0, 1023, 128, 255);
  Joystick.setThrottle(nino);
  */
  
  Serial.print("Autonomous Mode = ");
  Serial.println(buttonPressed);
  Serial.print("Manual Override = ");
  Serial.println(mano-18);
/*Serial.print("Shooter Power = ");
  Serial.println(nino/2 );
*/
}
