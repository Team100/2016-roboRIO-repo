const int sensorPin = A1;
const int ledPin = 13;
int sensorvalue;
int outputvalue;


void setup() {
  pinMode(ledPin, OUTPUT);
  Serial.begin(9600);
}

void loop() {
  sensorvalue = analogRead(sensorPin);
  outputvalue = map(sensorvalue, 0, 1023, -1, 1);
  analogWrite(ledPin, outputvalue);
  Serial.print("sensor = ");
  Serial.print(sensorvalue);
  Serial.print("\t output = ");
  Serial.println(outputvalue);
  delay (2);  

}
