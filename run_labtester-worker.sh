#!/usr/bin/env bash

rm log.txt
rm -rf build

gradle build
sudo docker build -f Dockerfile -t demo/oracle-java:8 .
sudo docker run --rm -p 8087:8087 -v $PWD:/build -w /build demo/oracle-java:8 java -jar build/libs/labtester-worker-0.1.0.jar >> log.txt &

#java -jar build/libs/labtester-worker-0.1.0.jar >> log.txt &