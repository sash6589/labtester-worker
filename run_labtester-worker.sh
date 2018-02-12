#!/usr/bin/env bash

rm log.txt
rm -rf build

gradle build
sudo docker build -f Dockerfile -t labtester-worker/dockerize:latest .
sudo docker run --rm -p 8087:8087 labtester-worker/dockerize:latest >> log.txt &

#java -jar build/libs/labtester-worker-0.1.0.jar >> log.txt &