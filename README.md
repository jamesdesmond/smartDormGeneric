#General
## Prerequisites:
* Oracle JRE 8 
* pi4J - http://pi4j.com/download.html

## Setting up the Raspberry Pi
* Hardware:
    * https://learn.adafruit.com/adafruit-16x2-character-l2. cd-plus-keypad-for-raspberry-pi/assembly
    * Base Software on the Pi:
        * Install Oracle JRE 8:
			  * https://blog.adafruit.com/2014/03/28/how-to-install-oracle-jdk-8-on-raspberry-pi-piday-raspberrypi-raspberry_pi/	
            * Ensure using ```java -version```that the Oracle JRE is in use
        * Install pi4J:
            * http://pi4j.com/download.html
        * Install and configure mutt
            * Look to the "SendText Prerequisites" section for more information

# Running Prebuilt smartDormGeneric
## Running smartDormGeneric on the Raspberry Pi once
1. Run```git clone https://github.com/jamesdesmond/smartDormGeneric.git```
2. Run```cd smartDormGeneric/```
3. Run```nano Configuration.ini```
4. Fill out the configuration file, all fields are required for smartDormGeneric
	* To get the API key follow the instructions in "ShowWeather Prerequisites"
5. Run```java -Xbootclasspath/a:/opt/pi4j/lib/pi4j-core.jar -jar /home/pi/smartDormGeneric/out/artifacts/smartDormGeneric_jar/smartDormGeneric.jar``` 
  
##Running smartDormGeneric on the Raspberry pi on startup
1. Complete steps 1 in "Running smartDormGeneric on the Raspberry Pi once"
2. Run ```sudo crontab -e```
3. Add the following entry ```@reboot cd /home/pi/smartDormGeneric && java -Xbootclasspath/a:/opt/pi4j/lib/pi4j-core.jar -jar /home/pi/smartDormGeneric/out/artifacts/smartDormGeneric_jar/smartDormGeneric.jar``` Replacing "pi" with the correct username

#Developing your own LCDApps in Java

## Libraries needed for development:
* http://hirt.se/blog/?p=464 - http://hirt.se/downloads/software/rpi/lcd.jar

## SendText Prerequisites
* Mutt installed on the raspberry pi - http://www.mutt.org/doc/manual/
* If using gmail enable Gmail’s IMAP for the account that will send text messages as emails, going to Settings → Forwarding and POP/IMAP → Enable IMAP
* If not using gmail enable IMAP access
* On the raspberry pi:
	* Copy .muttrc to /root/

## ShowWeather Prerequisites
For Weather Forecast usage: 
* Library used:
	* https://github.com/dvdme/forecastio-lib-java
* You will need an api key from https://darksky.net/dev/

## Development Instructions
1. Install Oracle JDK 8
2. Clone https://github.com/jamesdesmond/smartDormGeneric
3. Add the following libraries to your LCD Project:
    * http://hirt.se/blog/?p=464 -  http://hirt.se/downloads/software/rpi/lcd.jar
4. To emulate the screen declare a 
```
final ILCD ilcd = new MockupLCD();
```
in Runner.java
5. When ready to deploy to the Pi declare the ILCD as 
```Java
final ILCD ilcd = new RealLCD();
```
in Runner.java
* Make sure to a build a jar with libraries included for running on RPi hardware
* Run the generated jar the same way as SmartDormGeneric


