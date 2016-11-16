Generic Prerequisites:
Oracle JRE 8  -  
pi4J - http://pi4j.com/download.htmll
Generic libraries needed for development and on the pi:
http://hirt.se/blog/?p=464 - http://hirt.se/downloads/software/rpi/lcd.jar
SendText Prerequisites
Mutt installed on the raspberry pi - http://www.mutt.org/doc/manual/
INSERT CONFIG INSTRUCTIONS FOR MUTT


Development Instructions
Install Oracle JDK 8
Clone https://github.com/jamesdesmond/smartDormGeneric
Add the following libraries to your LCD Project:
http://hirt.se/blog/?p=464 - http://hirt.se/downloads/software/rpi/lcd.jar
For Weather Forecast usage:
If Weather is unnecessary just comment out or delete showWeather.java
https://github.com/dvdme/forecastio-lib-java
You will need an api key from https://darksky.net/dev/
This api key will need to be in Configuration.java before the jar is built.
To emulate the screen declare a final ILCD ilcd = new MockupLCD();
When ready to deploy to the Pi declare the ILCD as final ILCD ilcd = new RealLCD();;
Make sure to a build a jar with libraries included for running on RPi hardware


Setting up the Raspberry Pi
Hardware:
https://learn.adafruit.com/adafruit-16x2-character-lcd-plus-keypad-for-raspberry-pi/assembly
Base Software on the Pi:
Install Oracle JRE 8:
INSTALL GUIDE HERE
Install pi4J:
http://pi4j.com/download.html
Install and configure mutt
INSTALL AND CONFIG GUIDE HERE


Running the software on the Raspberry PI
Download your generated .jar file to a location on the raspberry pi
CONTINUE GUIDE WITH BASH FILE AND CRONTAB
