FROM openjdk:11-buster
VOLUME /tmp
EXPOSE 8750
COPY ${JAR_FILE} app.jar
CMD java -Xms256m -Xmx512m -jar app.jar