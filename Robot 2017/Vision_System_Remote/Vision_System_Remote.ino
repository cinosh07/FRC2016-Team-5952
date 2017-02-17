/*-----( Import needed libraries )-----*/
#include <Wire.h>  // Comes with Arduino IDE
// Get the LCD I2C Library here:
// https://bitbucket.org/fmalpartida/new-liquidcrystal/downloads
// Move any other LCD libraries to another folder or delete them
// See Library "Docs" folder for possible commands etc.
#include <LiquidCrystal_I2C.h>

String separator = ":";
int baudrate = 9600;
int loopDelay = 100;

int currentState = 0;
int lastState = 0;

String defaultDisplayLine1 = "Robuck Team 5952";
String defaultDisplayLine2 = "Vision System Remote";
String defaultDisplayLine3 = "Version: 1.0";
String defaultDisplayLine4 = "... initializing";
String ready = "Ready !";
String connected = "Connected to Robot!";

String function1DisplayLine1 = "Fonction 1 - ligne 1";
String function1DisplayLine2 = "Fonction 1 - ligne 2";
String function1DisplayLine3 = "Fonction 1 - ligne 3";
String function1DisplayLine4 = "Fonction 1 - ligne 4";

String function2DisplayLine1 = "Fonction 2 - ligne 1";
String function2DisplayLine2 = "Fonction 2 - ligne 2";
String function2DisplayLine3 = "Fonction 2 - ligne 3";
String function2DisplayLine4 = "Fonction 2 - ligne 4";

String function3DisplayLine1 = "Fonction 3 - ligne 1";
String function3DisplayLine2 = "Fonction 3 - ligne 2";
String function3DisplayLine3 = "Fonction 3 - ligne 3";
String function3DisplayLine4 = "Fonction 3 - ligne 4";

String function4DisplayLine1 = "Fonction 4 - ligne 1";
String function4DisplayLine2 = "Fonction 4 - ligne 2";
String function4DisplayLine3 = "Fonction 4 - ligne 3";
String function4DisplayLine4 = "Fonction 4 - ligne 4";

/*-----( Declare Constants )-----*/
/*-----( Declare objects )-----*/
// set the LCD address to 0x27 for a 20 chars 4 line display
// Set the pins on the I2C chip used for LCD connections:
//                    addr, en,rw,rs,d4,d5,d6,d7,bl,blpol
LiquidCrystal_I2C lcd(0x3f, 2, 1, 0, 4, 5, 6, 7, 3, POSITIVE); // Set the LCD I2C address

int pot1MaxVal = 1023;
int pot2MaxVal = 1023;
int pot3MaxVal = 1023;
int pot1MinVal = 0;
int pot2MinVal = 0;
int pot3MinVal = 0;
int tiltMaxDegrees = 45;
int panMaxDegrees = 360;
int tiltMaxValRaw = 933;
int panMaxValRaw = 961;
int tiltMinValRaw = 143;
int panMinValRaw = 15;
int pot1ScaledRange = 255;
int pot2ScaledRange = 255;
int pot3ScaledRange = 255;

int pot1Pin = 0;    // min 0 - max 1023
int pot2Pin = 1;    // min 0 - max 1023
int pot3Pin = 2;    // min 0 - max 1023
int panPin = 3;    // min 15 trim centered 86 - max 961 trim centered 892
int tiltPin = 6;    // min 143 trim centered 207 - max 933 trim centered 869
//int camSwitchPin = 5;  // Pull UP Switch 1
//int modeSwitchPin = 6; // Pull UP Switch 2
//int ledPin = 13;   // select the pin for the LED

//int ledButton1Pin = 7; // choose the pin for the LED
int ledButton2Pin = 8; // choose the pin for the LED
int ledButton3Pin = 9; // choose the pin for the LED
int ledButton4Pin = 10; // choose the pin for the LED
int ledButton5Pin = 11; // choose the pin for the LED

//int button1Pin = 2;   // choose the input pin (for a pushbutton)
int button2Pin = 3;   // choose the input pin (for a pushbutton)
int button3Pin = 4;   // choose the input pin (for a pushbutton)
int button4Pin = 5;   // choose the input pin (for a pushbutton)
int button5Pin = 6;   // choose the input pin (for a pushbutton)

//int button1Val = 0;     // variable for reading the pin status
//int lastButton1Val = 0;
int lastButton2Val = 0;
int lastButton3Val = 0;
int lastButton4Val = 0;
int lastButton5Val = 0;
//int ledButton1State = HIGH;
int ledButton2State = HIGH;
int ledButton3State = HIGH;
int ledButton4State = HIGH;
int ledButton5State = HIGH;
int button2Val = 0;     // variable for reading the pin status
int button3Val = 0;     // variable for reading the pin status
int button4Val = 0;     // variable for reading the pin status
int button5Val = 0;     // variable for reading the pin status

int pot1Val = 0;  // variable to store the value coming from the potentiometer 1
int pot2Val = 0;  // variable to store the value coming from the potentiometer 2
int pot3Val = 0;  // variable to store the value coming from the potentiometer 3
int tiltVal = 0; // variable to store the value coming from the Pan Joystick potentiometer
int panVal = 0; // variable to store the value coming from the Tilt Joystick potentiometer
boolean camSwitch = false; // variable to store the value from Cam Switch Press Button
boolean modeSwitch = false; // variable to store the value from Mode Switch Press Button
String displayLine1 = defaultDisplayLine1; // variable to store the value to de LCD 20x4 display line 1
String displayLine2 = defaultDisplayLine2; // variable to store the value to de LCD 20x4 display line 2
String displayLine3 = defaultDisplayLine3; // variable to store the value to de LCD 20x4 display line 3
String displayLine4 = defaultDisplayLine4; // variable to store the value to de LCD 20x4 display line 4

void setup() {

	displayLine1 = defaultDisplayLine1;
	displayLine2 = defaultDisplayLine2;
	displayLine3 = defaultDisplayLine3;
	displayLine4 = defaultDisplayLine4;

	Serial.begin(baudrate);
	lcd.begin(20, 4);
	lcd.backlight(); // finish with backlight on

	setLCDStart();

	//pinMode(ledButton1Pin, OUTPUT);  // declare LED as output
	//pinMode(button1Pin, INPUT);    // declare pushbutton as input

	pinMode(ledButton2Pin, OUTPUT);  // declare LED as output
	pinMode(button2Pin, INPUT);    // declare pushbutton as input

	pinMode(ledButton3Pin, OUTPUT);  // declare LED as output
	pinMode(button3Pin, INPUT);    // declare pushbutton as input

	pinMode(ledButton4Pin, OUTPUT);  // declare LED as output
	pinMode(button4Pin, INPUT);    // declare pushbutton as input

	pinMode(ledButton5Pin, OUTPUT);  // declare LED as output
	pinMode(button5Pin, INPUT);    // declare pushbutton as input

}

void setLCDStart() {

	//-------- Write characters on the display ------------------
	// NOTE: Cursor Position: Lines and Characters start at 0
	lcd.setCursor(2, 0); //Start at character 4 on line 0
	lcd.print(displayLine1);
	delay(1000);
	lcd.setCursor(0, 1);
	lcd.print(displayLine2);
	delay(1000);
	lcd.setCursor(4, 2);
	lcd.print(displayLine3);
	lcd.setCursor(2, 3);
	delay(1000);
	lcd.print(displayLine4);
	delay(1000);

}

void setLCDDefault() {

	//-------- Write characters on the display ------------------
	// NOTE: Cursor Position: Lines and Characters start at 0
	lcd.clear();
	lcd.setCursor(2, 0); //Start at character 4 on line 0
	lcd.print(displayLine1);
	lcd.setCursor(0, 1);
	lcd.print(displayLine2);
	lcd.setCursor(4, 2);
	lcd.print(displayLine3);
	lcd.setCursor(2, 3);
	lcd.print("Connected !");

}

void setLCDButtonFunction1() {

	//-------- Write characters on the display ------------------
	// NOTE: Cursor Position: Lines and Characters start at 0
	lcd.clear();
	lcd.setCursor(2, 0); //Start at character 4 on line 0
	lcd.print(function1DisplayLine1);
	lcd.setCursor(0, 1);
	lcd.print(function1DisplayLine2);
	lcd.setCursor(4, 2);
	lcd.print(function1DisplayLine3);
	lcd.setCursor(2, 3);
	lcd.print(function1DisplayLine4);

}

void setLCDButtonFunction2() {

	//-------- Write characters on the display ------------------
	// NOTE: Cursor Position: Lines and Characters start at 0
	lcd.clear();
	lcd.setCursor(2, 0); //Start at character 4 on line 0
	lcd.print(function2DisplayLine1);
	lcd.setCursor(0, 1);
	lcd.print(function2DisplayLine2);
	lcd.setCursor(4, 2);
	lcd.print(function2DisplayLine3);
	lcd.setCursor(2, 3);
	lcd.print(function2DisplayLine4);

}

void setLCDButtonFunction3() {

	//-------- Write characters on the display ------------------
	// NOTE: Cursor Position: Lines and Characters start at 0
	lcd.clear();
	lcd.setCursor(2, 0); //Start at character 4 on line 0
	lcd.print(function3DisplayLine1);
	lcd.setCursor(0, 1);
	lcd.print(function3DisplayLine2);
	lcd.setCursor(4, 2);
	lcd.print(function3DisplayLine3);
	lcd.setCursor(2, 3);
	lcd.print(function3DisplayLine4);

}

void setLCDButtonFunction4() {

	//-------- Write characters on the display ------------------
	// NOTE: Cursor Position: Lines and Characters start at 0
	lcd.clear();
	lcd.setCursor(2, 0); //Start at character 4 on line 0
	lcd.print(function4DisplayLine1);
	lcd.setCursor(0, 1);
	lcd.print(function4DisplayLine2);
	lcd.setCursor(4, 2);
	lcd.print(function4DisplayLine3);
	lcd.setCursor(2, 3);
	lcd.print(function4DisplayLine4);

}

int checkFunctionStatus(int buttonVal, int button) {

	int ledState;

	if (buttonVal == HIGH) {     // check if the input is HIGH (button released)
		ledState = LOW;  // turn LED OFF
	} else {
		ledState = HIGH;  // turn LED ON
	}

	return ledState;
}

void loop() {

	pot1Val = analogRead(pot1Pin);    // read the value from potentiometer 1
	pot2Val = analogRead(pot2Pin);    // read the value from potentiometer 2
	pot3Val = analogRead(pot3Pin);    // read the value from potentiometer 3

	tiltVal = analogRead(tiltPin);    // read the value from Tilt potentiometer
	panVal = analogRead(panPin);    // read the value from Pan potentiometer

	//button1Val = digitalRead(button1Pin);  // read button1 value
	button2Val = digitalRead(button2Pin);  // read button2 value
	button3Val = digitalRead(button3Pin);  // read button3 value
	button4Val = digitalRead(button4Pin);  // read button4 value
	button5Val = digitalRead(button5Pin);  // read button5 value

	if (currentState == 0  && lastState != currentState) {
		setLCDDefault();
		//ledButton1State = checkButtonStatus(button1Val, lastButton1Val,
		//ledButton1State, 1);
		ledButton2State = checkButtonStatus(button2Val, lastButton2Val,
				ledButton2State, 2);
		ledButton3State = checkButtonStatus(button3Val, lastButton3Val,
				ledButton3State, 3);
		ledButton4State = checkButtonStatus(button4Val, lastButton4Val,
				ledButton4State, 4);
		ledButton5State = checkButtonStatus(button5Val, lastButton5Val,
				ledButton5State, 5);

	} else if (currentState == 1 && lastState != currentState) {

		if (!checkResetButtonFunctionStatus(button2Val, lastButton2Val, 2)) {
			buttonFunction1();
		}

	} else if (currentState == 2 && lastState != currentState) {

		if (!checkResetButtonFunctionStatus(button2Val, lastButton2Val, 2)) {
			buttonFunction1();
		}

	} else if (currentState == 3 && lastState != currentState) {

		if (!checkResetButtonFunctionStatus(button2Val, lastButton2Val, 2)) {
			buttonFunction1();
		}


	} else if (currentState == 4 && lastState != currentState) {

		if (!checkResetButtonFunctionStatus(button2Val, lastButton2Val, 2)) {
			buttonFunction1();
		}

	}

	// turns LED on if the ledState=1 or off if the ledState=0
	//digitalWrite(ledButton1Pin, ledButton1State);
	digitalWrite(ledButton2Pin, ledButton2State);
	digitalWrite(ledButton3Pin, ledButton3State);
	digitalWrite(ledButton4Pin, ledButton4State);
	digitalWrite(ledButton5Pin, ledButton5State);

	printSerial();

	receiveCommand();

	delay(loopDelay);

}

void buttonFunction1() {
	//TODO Navigation menu, commandes et menus

	ledButton3State = checkFunctionStatus(button3Val, 3);
	ledButton4State = checkFunctionStatus(button4Val, 4);
	ledButton5State = checkFunctionStatus(button5Val, 5);

}
void buttonFunction2() {
	//TODO Navigation menu, commandes et menus
	ledButton3State = checkFunctionStatus(button3Val, 3);
	ledButton4State = checkFunctionStatus(button4Val, 4);
	ledButton5State = checkFunctionStatus(button5Val, 5);

}
void buttonFunction3() {
	//TODO Navigation menu, commandes et menus
	ledButton3State = checkFunctionStatus(button3Val, 3);
	ledButton4State = checkFunctionStatus(button4Val, 4);
	ledButton5State = checkFunctionStatus(button5Val, 5);

}
void buttonFunction4() {
	//TODO Navigation menu, commandes et menus
	ledButton3State = checkFunctionStatus(button3Val, 3);
	ledButton4State = checkFunctionStatus(button4Val, 4);
	ledButton5State = checkFunctionStatus(button5Val, 5);

}

void receiveCommand() {

	if (Serial.available() > 0) {
		String command;
		command = Serial.readString();
		//Serial.print("Command receive : ");
		//Serial.print(command);
		//Serial.println();

		if (command.length() > 0) {

			int commaIndex = command.indexOf(':');
			//  Search for the next comma just after the first
			int secondCommaIndex = command.indexOf(':', commaIndex + 1);

			int thirdCommaIndex = command.indexOf(':', commaIndex + 1);

			int forthCommaIndex = command.indexOf(':', commaIndex + 1);

			int fithCommaIndex = command.indexOf(':', commaIndex + 1);

			int sixthCommaIndex = command.indexOf(':', commaIndex + 1);

			int seventhCommaIndex = command.indexOf(':', commaIndex + 1);

			int eighthCommaIndex = command.indexOf(':', commaIndex + 1);

			int ninethCommaIndex = command.indexOf(':', commaIndex + 1);

			int tenthcommaIndex = command.indexOf(':', commaIndex + 1);

			int eleventhCommaIndex = command.indexOf(':', commaIndex + 1);

			int twelvethCommaIndex = command.indexOf(':', commaIndex + 1);

			int thirteenthCommaIndex = command.indexOf(':', commaIndex + 1);

			int fourteenthCommaIndex = command.indexOf(':', commaIndex + 1);

			int fifhteenthCommaIndex = command.indexOf(':', commaIndex + 1);

			function1DisplayLine1 = command.substring(0, commaIndex);
			function1DisplayLine2 = command.substring(commaIndex + 1,
					secondCommaIndex);
			function1DisplayLine3 = command.substring(secondCommaIndex,
					thirdCommaIndex);
			function1DisplayLine4 = command.substring(thirdCommaIndex,
					forthCommaIndex);

			function2DisplayLine1 = command.substring(forthCommaIndex,
					fithCommaIndex);
			function2DisplayLine2 = command.substring(fithCommaIndex,
					sixthCommaIndex);
			function2DisplayLine3 = command.substring(sixthCommaIndex,
					seventhCommaIndex);
			function2DisplayLine4 = command.substring(seventhCommaIndex,
					eighthCommaIndex);

			function3DisplayLine1 = command.substring(eighthCommaIndex,
					ninethCommaIndex);
			function3DisplayLine2 = command.substring(ninethCommaIndex,
					tenthcommaIndex);
			function3DisplayLine3 = command.substring(tenthcommaIndex,
					eleventhCommaIndex);
			function3DisplayLine4 = command.substring(eleventhCommaIndex,
					twelvethCommaIndex);

			function3DisplayLine1 = command.substring(twelvethCommaIndex,
					thirteenthCommaIndex);
			function3DisplayLine2 = command.substring(thirteenthCommaIndex,
					fourteenthCommaIndex);
			function3DisplayLine3 = command.substring(fourteenthCommaIndex,
					fifhteenthCommaIndex);
			function3DisplayLine4 = command.substring(fifhteenthCommaIndex);

		}
	}

}



void printSerial() {

// Data Structure
// String Sended over the serial port containing potentiometers and push button values seperated by a delimitor.
// Delimitor define by delimitor variable DEFAULT :
//
// DATA by order in the string:
//
// Current State
// value from potentiometer 1
// value from potentiometer 2
// value from potentiometer 3
// value from Tilt potentiometer
// value from Pan potentiometer
// Button 2 state
// Button 3 state
// Button 4 state
// Button 5 state

//Send Values to the Serial port
// Current State
Serial.print((String) currentState);
Serial.print(separator);
// Value POT1
Serial.print(potValuScaled(pot1Val, pot1ScaledRange, pot1MaxVal, pot1MinVal));
Serial.print(separator);
// Value POT2
Serial.print(potValuScaled(pot2Val, pot2ScaledRange, pot2MaxVal, pot2MinVal));
Serial.print(separator);
// Value POT3
Serial.print(potValuScaled(pot3Val, pot3ScaledRange, pot3MaxVal, pot3MinVal));
Serial.print(separator);
// Value TILT
Serial.print(
		potValuScaled(tiltVal, tiltMaxDegrees, tiltMaxValRaw, tiltMinValRaw));
Serial.print(separator);
// Value PAN
Serial.print(potValuScaled(panVal, panMaxDegrees, panMaxValRaw, panMinValRaw));
// Value Led1State
//Serial.print(separator);
//Serial.print((String) ledButton1State);
// Value Led2State
Serial.print(separator);
Serial.print((String) ledButton2State);
// Value Led3State
Serial.print(separator);
Serial.print((String) ledButton3State);
// Value Led4State
Serial.print(separator);
Serial.print((String) ledButton4State);
// Value Led5State
Serial.print(separator);
Serial.print((String) ledButton5State);
Serial.print(separator);
Serial.println();

}

int checkButtonStatus(int buttonValue, int lastVal, int ledState, int button) {

// check if the button is pressed or released
// by comparing the buttonState to its previous state
if (buttonValue != lastVal) {

	// change the state of the led when someone pressed the button
	if (buttonValue == 1) {
		if (ledState == HIGH)
			ledState = LOW;
		else
			ledState = HIGH;
	}

	// remember the current state of the button
	if (buttonValue == 0 && ledState == HIGH) {
		switch (button) {

		case 2:
			lastButton2Val = buttonValue;
			lastState = currentState;
			currentState = 1;
			setLCDButtonFunction1();
			break;
		case 3:
			lastState = currentState;
			currentState = 2;
			lastButton3Val = buttonValue;
			setLCDButtonFunction2();
			break;
		case 4:
			lastState = currentState;
			currentState = 3;
			lastButton4Val = buttonValue;
			setLCDButtonFunction3();
			break;
		case 5:
			lastState = currentState;
			currentState = 4;
			lastButton5Val = buttonValue;
			setLCDButtonFunction4();
			break;
		}
	}

}

return ledState;

}

boolean checkResetButtonFunctionStatus(int buttonValue, int lastVal,
	int button) {

boolean reseted = false;
// check if the button is pressed or released
// by comparing the buttonState to its previous state
if (buttonValue != lastVal) {

	switch (button) {

	case 2:
		if (currentState == 1) {

			lastState = currentState;
			currentState = 0;
			setLCDDefault();
			lastButton2Val = buttonValue;
			int ledButton2State = HIGH;
			int ledButton3State = HIGH;
			int ledButton4State = HIGH;
			int ledButton5State = HIGH;
			reseted = true;
		}

		break;
	}

}
return reseted;

}

int potValuScaled(int potValueRaw, int potMaxScaled, int potMaxRaw,
	int potMinRaw) {

double result = 0.0;

if (potMinRaw == 0) {

	result = (double) potValueRaw * (double) potMaxScaled / (double) potMaxRaw;
	return (int) result;

} else if (potMinRaw > 0) {

	potMaxRaw = potMaxRaw - potMinRaw;
	result = (double) potValueRaw * (double) potMaxScaled / (double) potMaxRaw;
	return (int) result;
}

}
