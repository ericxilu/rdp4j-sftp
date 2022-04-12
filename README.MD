This is a sample SFTP directory puller.

Build Docker Image, run below command
mvn package

it builds a docker image rdp4j-sftp:1.0-SNAPSHOT




To run Docker Image, you need:
a SQL server db: jdbc:sqlserver://192.168.1.123:1433;databaseName=eric

command to start docker container

docker run -p 9092:8080 -t rdp4j-sftp:1.0-SNAPSHOT

push to dockerhub
docker tag rdp4j-sftp:1.0-SNAPSHOT ericxilu/rdp4j-sftp:1.0-SNAPSHOT
docker push ericxilu/rdp4j-sftp:1.0-SNAPSHOT

command to stat docker container from docker hub

to test the docker container

set post request to
http://localhost:9092/api/addfileaudit

with payload
{
"fileName": "test5.txt"
}