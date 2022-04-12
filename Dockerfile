FROM adoptopenjdk/openjdk11:alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-DLOCAL_REFDATA_PATH=/home/spring","-DLOCAL_REFDATA_ARCHIVE_PATH=/tmp","-jar","/app/app.jar"]