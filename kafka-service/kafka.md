# Kafka

## Help

### Useful Comnands



## Installation

### Download
va
For standalone installation download version 2.12-2.5.0 of Kafka from here:-

https://kafka.apache.org/downloads

Kafka contains standalone version of Apache Zookeeper. 

Note: install Kafka in root directory as it concatenates the classpath a lot in windows.

If you want to download a multi node version download 3.6.1 of zookeepr from here:-

https://zookeeper.apache.org/releases.html

### Update Directory Properties

Update the Kafka log.dirs in the config/server.properties file.

Update the Zookeeper dataDir in the config/zookeeper.Properties file

### Start Zookeeper

Open an admin CMD window

cd C:\Kafka\bin\windows

Then run

zookeeper-server-start.bat ..\..\config\zookeeper.properties

## Start Kafka

Open an admin CMD window

cd C:\Kafka\bin\windows

Then run

 kafka-server-start.bat ..\..\config\server.properties

By default, the Kafka server starts on localhost:9092.
