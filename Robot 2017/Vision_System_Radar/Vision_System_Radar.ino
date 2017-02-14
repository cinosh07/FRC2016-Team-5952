#include <avr/io.h>

#include "Arduino.h"

#include "config.h"
#include "def.h"
#include "types.h"
#include "MultiWii.h"
#include "EEPROM.h"
#include "IMU.h"
#include "Sensors.h"
#include <Wire.h>
#include "ITG3200.h"
#include "I2Cdev.h"
#include "HMC5883L.h"

#define BMA180 0x40  //address of the accelerometer
#define RESET 0x10
#define PWR 0x0D
#define BW 0X20
#define RANGE 0X35
#define DATA 0x02
//
int offx = 31;
int offy = 47;
int offz = -23;

String delimitor = ":";

long radarDuration, radarInches, radarCm;
long backDuration, backInches, backCm;

// this constant won't change.  It's the pin number
// of the sensor's output:
const int radarPingPin = 2;
const int backPingPin = 3;

ITG3200 gyro = ITG3200();
float x, y, z;
int ix, iy, iz;

// class default I2C address is 0x1E
// specific I2C addresses may be passed as a parameter here
// this device only supports one I2C address (0x1E)
HMC5883L mag;

int16_t mx, my, mz;

#define LED_PIN 13
bool blinkState = false;

void setup() {
	// initialize serial communication:
	Serial.begin(9600);

	Wire.begin(); // if experiencing gyro problems/crashes while reading XYZ values
				  // please read class constructor comments for further info.
	delay(1000);
	gyro.reset();
	// Use ITG3200_ADDR_AD0_HIGH or ITG3200_ADDR_AD0_LOW as the ITG3200 address
	// depending on how AD0 is connected on your breakout board, check its schematics for details
	gyro.init(ITG3200_ADDR_AD0_LOW);

	Serial.print("zeroCalibrating...");
	gyro.zeroCalibrate(2500, 2);
	Serial.println("done.");

	AccelerometerStart();

	mag.initialize();

	// verify connection
	Serial.println("Testing device connections...");
	Serial.println(
			mag.testConnection() ?
					"HMC5883L connection successful" :
					"HMC5883L connection failed");

	// configure Arduino LED for
	pinMode(LED_PIN, OUTPUT);
}

void loop() {
	// establish variables for duration of the ping,
	// and the distance result in inches and centimeters:

	calculateRadarDistance();
	calculateRobotBackDistance();
	PrintSerialData();

	delay(100);
}

void calculateRadarDistance() {

	// The PING))) is triggered by a HIGH pulse of 2 or more microseconds.
	// Give a short LOW pulse beforehand to ensure a clean HIGH pulse:
	pinMode(radarPingPin, OUTPUT);
	digitalWrite(radarPingPin, LOW);
	delayMicroseconds(2);
	digitalWrite(radarPingPin, HIGH);
	delayMicroseconds(5);
	digitalWrite(radarPingPin, LOW);

	// The same pin is used to read the signal from the PING))): a HIGH
	// pulse whose duration is the time (in microseconds) from the sending
	// of the ping to the reception of its echo off of an object.
	pinMode(radarPingPin, INPUT);
	radarDuration = pulseIn(radarPingPin, HIGH);

	// convert the time into a distance
	radarInches = microsecondsToInches(radarDuration);
	radarCm = microsecondsToCentimeters(radarDuration);

}

void calculateRobotBackDistance() {

	// The PING))) is triggered by a HIGH pulse of 2 or more microseconds.
	// Give a short LOW pulse beforehand to ensure a clean HIGH pulse:
	pinMode(backPingPin, OUTPUT);
	digitalWrite(backPingPin, LOW);
	delayMicroseconds(2);
	digitalWrite(backPingPin, HIGH);
	delayMicroseconds(5);
	digitalWrite(backPingPin, LOW);

	// The same pin is used to read the signal from the PING))): a HIGH
	// pulse whose duration is the time (in microseconds) from the sending
	// of the ping to the reception of its echo off of an object.
	pinMode(backPingPin, INPUT);
	backDuration = pulseIn(backPingPin, HIGH);

	// convert the time into a distance
	backInches = microsecondsToInches(backDuration);
	backCm = microsecondsToCentimeters(backDuration);

}

void PrintSerialData() {

	// Data Structure
	// String Sended over the serial port containing sensors values delimited seperated by a delimitor.
	// Delimitor define by delimitor variable DEFAULT :
	//
	// DATA by order in the string:
	//
	// Radar Ultrasonic sensor distance in inches
	// Radar Ultrasonic sensor distance in cm
	// Robot Back Ultrasonic sensor distance in inches
	// Robot Back Ultrasonic sensor distance in cm
	// Gyro G's X axis
	// Gyro G's Y axis
	// Gyro G's Z axis
	// Accelerometer X axis
	// Accelerometer Y axis
	// Accelerometer Z axis
	// Magnetometer X axis
	// Magnetometer Y axis
	// Magnetometer Z axis=-
	// Magnetometer heading

	Serial.print(radarInches);
	Serial.print(delimitor);
	Serial.print(radarCm);
	Serial.print(delimitor);

	Serial.print(backInches);
	Serial.print(delimitor);
	Serial.print(backCm);
	Serial.print(delimitor);

	/*
	 // Reads calibrated raw values from the sensor
	 gyro.readGyroRawCal(&ix,&iy,&iz);
	 Serial.print("X2:");
	 Serial.print(ix);
	 Serial.print("  Y:");
	 Serial.print(iy);
	 Serial.print("  Z:");
	 Serial.println(iz);
	 */

// Reads calibrated values in deg/sec
	gyro.readGyro(&x, &y, &z);
	Serial.print(x);
	Serial.print(delimitor);
	Serial.print(y);
	Serial.print(delimitor);
	Serial.print(z);
	Serial.print(delimitor);
	AccelerometerRead();
	ReadMag();
	Serial.println();

}

void ReadMag() {
	// read raw heading measurements from device
	mag.getHeading(&mx, &my, &mz);

	// display tab-separated gyro x/y/z values
	Serial.print(mx);
	Serial.print(delimitor);
	Serial.print(my);
	Serial.print(delimitor);
	Serial.print(mz);
	Serial.print(delimitor);

	// To calculate heading in degrees. 0 degree indicates North
	float heading = atan2(my, mx);
	if (heading < 0)
		heading += 2 * M_PI;
	Serial.print(heading * 180 / M_PI);

	// blink LED to indicate activity
	blinkState = !blinkState;
	digitalWrite(LED_PIN, blinkState);
}

void AccelerometerStart() {
	byte temp[1];
	byte temp1;
	//
	writeTo(BMA180, RESET, 0xB6);
	//wake up mode
	writeTo(BMA180, PWR, 0x10);
	// low pass filter,
	readFrom(BMA180, BW, 1, temp);
	temp1 = temp[0] & 0x0F;
	writeTo(BMA180, BW, temp1);
	// range +/- 2g
	readFrom(BMA180, RANGE, 1, temp);
	temp1 = (temp[0] & 0xF1) | 0x04;
	writeTo(BMA180, RANGE, temp1);
}

void AccelerometerRead() {
	// read in the 3 axis data, each one is 14 bits
	// print the data to terminal
	int n = 6;
	byte result[5];
	readFrom(BMA180, DATA, n, result);

	int x = ((result[0] | result[1] << 8) >> 2) + offx;
	float x1 = x / 4096.0;
	Serial.print(x1);
	Serial.print(delimitor);
	//
	int y = ((result[2] | result[3] << 8) >> 2) + offy;
	float y1 = y / 4096.0;
	Serial.print(y1);
	Serial.print(delimitor);
	//
	int z = ((result[4] | result[5] << 8) >> 2) + offz;
	float z1 = z / 4096.0;
	Serial.print(z1);
	Serial.print(delimitor);
}
void writeTo(int DEVICE, byte address, byte val) {
	Wire.beginTransmission(DEVICE);   //start transmission to ACC
	Wire.write(address);               //send register address
	Wire.write(val);                   //send value to write
	Wire.endTransmission();           //end trnsmisson
}
//reads num bytes starting from address register in to buff array
void readFrom(int DEVICE, byte address, int num, byte buff[]) {
	Wire.beginTransmission(DEVICE); //start transmission to ACC
	Wire.write(address);            //send reguster address
	Wire.endTransmission();        //end transmission

	Wire.beginTransmission(DEVICE); //start transmission to ACC
	Wire.requestFrom(DEVICE, num);  //request 6 bits from ACC

	int i = 0;
	while (Wire.available())        //ACC may abnormal
	{
		buff[i] = Wire.read();        //receive a byte
		i++;
	}
	Wire.endTransmission();         //end transmission
}
long microsecondsToInches(long microseconds) {
	// According to Parallax's datasheet for the PING))), there are
	// 73.746 microseconds per inch (i.e. sound travels at 1130 feet per
	// second).  This gives the distance travelled by the ping, outbound
	// and return, so we divide by 2 to get the distance of the obstacle.
	// See: http://www.parallax.com/dl/docs/prod/acc/28015-PING-v1.3.pdf
	return microseconds / 74 / 2;
}

long microsecondsToCentimeters(long microseconds) {
	// The speed of sound is 340 m/s or 29 microseconds per centimeter.
	// The ping travels out and back, so to find the distance of the
	// object we take half of the distance travelled.
	return microseconds / 29 / 2;
}

