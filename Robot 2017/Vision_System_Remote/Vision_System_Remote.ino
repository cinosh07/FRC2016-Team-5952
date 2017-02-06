#include "Arduino.h"

String separator = "::";
int baudrate = 9600;
int loopDelay = 100;
String defaultDisplayLine1 = "Robuck Team #5952";
String defaultDisplayLine2 = "Vision System Remote";
String defaultDisplayLine3 = "Version: 1.0";
String defaultDisplayLine4 = "... initializing";


int pot1MaxVal = 1023;       //
int pot2MaxVal = 1023;       //
int pot3MaxVal = 1023;       //
int pot1MinVal = 0;       //
int pot2MinVal = 0;       //
int pot3MinVal = 0;       //
int tiltMaxDegrees = 45;       //
int panMaxDegrees = 360;       //
int tiltMaxValRaw = 933;       //
int panMaxValRaw = 961;       //
int tiltMinValRaw = 143;       //
int panMinValRaw = 15;       //
int pot1ScaledRange = 255;
int pot2ScaledRange = 255;
int pot3ScaledRange = 255;


int pot1Pin = 0;    // min 0 - max 1023
int pot2Pin = 1;    // min 0 - max 1023
int pot3Pin = 2;    // min 0 - max 1023
int panPin = 3;    // min 15 trim centered 86 - max 961 trim centered 892
int tiltPin = 4;    // min 143 trim centered 207 - max 933 trim centered 869
int camSwitchPin = 5;
int modeSwitchPin = 6;
int ledPin = 13;   // select the pin for the LED


int pot1Val = 0;       // variable to store the value coming from the potentiometer 1
int pot2Val = 0;       // variable to store the value coming from the potentiometer 2
int pot3Val = 0;       // variable to store the value coming from the potentiometer 3
int tiltVal = 0;       // variable to store the value coming from the Pan Joystick potentiometer
int panVal = 0;       // variable to store the value coming from the Tilt Joystick potentiometer
boolean camSwitch = false; // variable to store the value from Cam Switch Press Button
boolean modeSwitch = false;   // variable to store the value from Mode Switch Press Button
String displayLine1 = "";    // variable to store the value to de LCD 20x4 display line 1
String displayLine2 = "";	// variable to store the value to de LCD 20x4 display line 2
String displayLine3 = "";	// variable to store the value to de LCD 20x4 display line 3
String displayLine4 = "";	// variable to store the value to de LCD 20x4 display line 4



void setup() {

  pinMode(ledPin, OUTPUT);  // declare the ledPin as an OUTPUT
  Serial.begin(baudrate);
  displayLine1 = defaultDisplayLine1;
  displayLine2 = defaultDisplayLine2;
  displayLine3 = defaultDisplayLine3;
  displayLine4 = defaultDisplayLine4;

}

void loop() {

	pot1Val = analogRead(pot1Pin);    // read the value from the sensor
	pot2Val = analogRead(pot2Pin);    // read the value from the sensor
	pot3Val = analogRead(pot3Pin);    // read the value from the sensor

	tiltVal = analogRead(tiltPin);    // read the value from the sensor
	panVal = analogRead(panPin);    // read the value from the sensor

	camSwitch = analogRead(camSwitchPin);    // read the value from the sensor
	modeSwitch = analogRead(modeSwitchPin);    // read the value from the sensor

	//Send Values to the Serial port

	// Value POT1
	Serial.print(potValuScaled(pot1Val,pot1ScaledRange,pot1MaxVal,pot1MinVal));
	Serial.print(separator);
	// Value POT2
	Serial.print(potValuScaled(pot2Val,pot2ScaledRange,pot2MaxVal,pot2MinVal));
	Serial.print(separator);
	// Value POT3
	Serial.print(potValuScaled(pot3Val,pot3ScaledRange,pot3MaxVal,pot3MinVal));
	Serial.print(separator);
	// Value TILT
	Serial.print(potValuScaled(tiltVal,tiltMaxDegrees,tiltMaxValRaw,tiltMinValRaw));
	Serial.print(separator);
	// Value PAN
	Serial.print(potValuScaled(panVal,panMaxDegrees,panMaxValRaw,panMinValRaw));
	Serial.print(separator);
	// Value CAMSWITCH
	Serial.print(camSwitch);
	Serial.print(separator);
	// Value MODESWITCH
	Serial.print(modeSwitch);

	Serial.println();

	delay(loopDelay);

}

int potValuScaled(int potValueRaw, int potMaxScaled, int potMaxRaw, int potMinRaw) {

	if (potMinRaw == 0) {

		return potValueRaw * potMaxScaled / potMaxRaw;

	} else if (potMinRaw > 0) {

		potMaxRaw = potMaxRaw - potMinRaw;
		return potValueRaw * potMaxScaled / potMaxRaw;
	}

}
