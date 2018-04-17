FROM openjdk:8-jdk

ENV DEBIAN_FRONTEND noninteractive
RUN apt-get update && \
    apt-get -y install gcc g++ mono-mcs gradle pep8 && \
    rm -rf /var/lib/apt/lists/*

RUN apt-get -y install git

ENV SWAPSIZE=512 SWAPFILE=/var/lib/swap.img

CMD ["/usr/bin/make-swap"]

EXPOSE 8087

COPY build/libs/*.jar /app/service.jar

CMD ["java", "-jar", "/app/service.jar"]
