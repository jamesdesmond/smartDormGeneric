# Generic Prerequisites:
Oracle JRE 8  -  
pi4J - http://pi4j.com/download.htmll
# Generic libraries needed for development and on the pi:
http://hirt.se/blog/?p=464 - http://hirt.se/downloads/software/rpi/lcd.jar
# SendText Prerequisites
Mutt installed on the raspberry pi - http://www.mutt.org/doc/manual/
INSERT CONFIG INSTRUCTIONS FOR MUTT


# Development Instructions
1. Install Oracle JDK 8
2. Clone https://github.com/jamesdesmond/smartDormGeneric
3. Add the following libraries to your LCD Project:
    1. http://hirt.se/blog/?p=464 -  http://hirt.se/downloads/software/rpi/lcd.jar
    2. For Weather Forecast usage:
        * If Weather is unnecessary just comment out or delete showWeather.java
        * https://github.com/dvdme/forecastio-lib-java
        * You will need an api key from https://darksky.net/dev/
This api key will need to be in Configuration.java before the jar is built.
  4. To emulate the screen declare a final ILCD ilcd = new MockupLCD();
  5. When ready to deploy to the Pi declare the ILCD as final ILCD ilcd = new RealLCD();;
     * Make sure to a build a jar with libraries included for running on RPi hardware


# Setting up the Raspberry Pi
1. Hardware:
    1. https://learn.adafruit.com/adafruit-16x2-character-l2. cd-plus-keypad-for-raspberry-pi/assembly
    1. Base Software on the Pi:
        1. Install Oracle JRE 8:
            * INSTALL GUIDE HERE
        1. Install pi4J:
            * http://pi4j.com/download.html
        1. Install and configure mutt
            * INSTALL AND CONFIG GUIDE HERE


# Running the software on the Raspberry PI
1. Download your generated .jar file to a location on the raspberry pi
2. CONTINUE GUIDE WITH BASH FILE AND CRONTAB
