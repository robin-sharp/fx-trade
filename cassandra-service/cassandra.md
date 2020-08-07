# Cassandra

## Help

### Start Cassandra

From the CMD Window Run as Administrator 

cd "C:\Program Files\apache-cassandra-3.11.6\bin"

Then to start the database run

.\cassandra.bat -f

### Useful cqlsh commands

cd "C:\Program Files\apache-cassandra-3.11.6\bin"
cqlsh

cqlsh> use system;
cqlsh:system> select data_center from local;

DESCRIBE KEYSPACES;
DESCRIBE KEYSPACE fxtrade;
DESCRIBE KEYSPACE entity;

USE fxtrade;
USE entity;

DESCRIBE TABLES;
DESCRIBE TABLE fxtrade;
DESCRIBE TABLE tradeparty;

SELECT * from fxtrade;
SELECT * from tradeparty;


## Installation

### Installing Cassandra (on Windows)

Download and Extract the Cassandra Community Edition :-

https://cassandra.apache.org/download/

Install in "C:\Program Files\apache-cassandra-3.11.6" (for example)

Add C:\Program Files\apache-cassandra-3.11.6\bin to the PATH

cd "C:\Program Files\apache-cassandra-3.11.6\bin"

Ensure you are using a 64bit version of Java
Edit cassandra.bat and add JAVA_HOME below the UNINSTALL line

set UNINSTALL="UNINSTALL"
set JAVA_HOME=C:\Program Files\Java\jre1.8.0_251

Further down after setting the CLASSPATH add the following to stop a triger path error

REM set JAVA_OPTS=%JAVA_OPTS% "-Dcassandra.triggers_dir=%CASSANDRA_HOME%\conf\triggers"

Using CMD as administrator to run Cassandra 

.\cassandra.bat -f


### Install Python 2.x (on Windows)

Install Latest Python 2.x from here (to run Cassandra cqlsh.bat)

https://www.python.org/downloads/windows/

Install in "C:\Program Files\python-27" (for example)

Add "C:\Program Files\python-27" to the PATH


# Install the Database

The following directories contain the schema definitions which can be loaded using cqlsh.bat

trade-server/src/main/resources/cassandra/fxtrade

entity-server/src/main/resources/cassandra/entity

# Loading the Data

To load the party data run the following

entity-batch/SwiftPartyBatchRunner 