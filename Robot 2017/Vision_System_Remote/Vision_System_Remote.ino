#include "Arduino.h"

int pot1Pin = 0;    // min 0 - max 1023
int pot2Pin = 1;    // min 0 - max 1023
int pot3Pin = 2;    // min 0 - max 1023

int panPin = 3;    // min 15 trim centered 86 - max 961 trim centered 892
int tiltPin = 4;    // min 143 trim centered 207 - max 933 trim centered 869


int camSwitchPin = 5;
int modeSwitchPin = 6;

int ledPin = 13;   // select the pin for the LED

int pot1Val = 0;       // variable to store the value coming from the sensor
int pot2Val = 0;       // variable to store the value coming from the sensor
int pot3Val = 0;       // variable to store the value coming from the sensor
int tiltVal = 0;       // variable to store the value coming from the sensor
int panVal = 0;       // variable to store the value coming from the sensor

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

boolean camSwitch = false;
boolean modeSwitch = false;



void setup() {
  pinMode(ledPin, OUTPUT);  // declare the ledPin as an OUTPUT
  Serial.begin(9600);
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
	Serial.print("::");
	// Value POT2
	Serial.print(potValuScaled(pot2Val,pot2ScaledRange,pot2MaxVal,pot2MinVal));
	Serial.print("::");
	// Value POT3
	Serial.print(potValuScaled(pot3Val,pot3ScaledRange,pot3MaxVal,pot3MinVal));
	Serial.print("::");
	// Value TILT
	Serial.print(potValuScaled(tiltVal,tiltMaxDegrees,tiltMaxValRaw,tiltMinValRaw));
	Serial.print("::");
	// Value PAN
	Serial.print(potValuScaled(panVal,panMaxDegrees,panMaxValRaw,panMinValRaw));
	Serial.print("::");
	// Value CAMSWITCH
	Serial.print(camSwitch);
	Serial.print("::");
	// Value MODESWITCH
	Serial.print(modeSwitch);

	Serial.println();

	delay(100);

}

int potValuScaled(int potValueRaw, int potMaxScaled, int potMaxRaw, int potMinRaw) {

	if (potMinRaw == 0) {

		return potValueRaw * potMaxScaled / potMaxRaw;

	} else if (potMinRaw > 0) {

		potMaxRaw = potMaxRaw - potMinRaw;
		return potValueRaw * potMaxScaled / potMaxRaw;
	}

}
