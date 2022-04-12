This is a sample SFTP directory puller.

Build Docker Image, run below command
mvn package

it builds a docker image rdp4j-sftp:1.0-SNAPSHOT




To run Docker Image, you need:
a SQL server db: jdbc:sqlserver://192.168.1.123:1433;databaseName=eric

command to start docker containr

docker run -p 9092:8080 -t rdp4j-sftp:1.0-SNAPSHOT