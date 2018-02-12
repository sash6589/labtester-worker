FROM openjdk:8-jdk

ENV DEBIAN_FRONTEND noninteractive
RUN apt-get update && \
    apt-get -y install gcc g++ mono-mcs && \
    rm -rf /var/lib/apt/lists/*

RUN apt-get -y install git

EXPOSE 8087

COPY build/libs/*.jar /app/service.jar

CMD ["java", "-jar", "/app/service.jar"]
