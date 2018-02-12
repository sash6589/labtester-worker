FROM openjdk:8-jdk

RUN apt-get -y install git g++

EXPOSE 8087

COPY build/libs/*.jar /app/service.jar

CMD ["java", "-jar", "/app/service.jar"]
