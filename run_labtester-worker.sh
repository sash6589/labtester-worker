#!/usr/bin/env bash

if [ -z "$1" ]
  then
    echo "run ./run_labtester-worker.sh DOTOKEN"
    exit
fi

$DOTOKEN=$1

rm log.txt
rm -rf build

gradle build
sudo docker build -f Dockerfile -t labtester-worker/dockerize:latest .

curl -L https://github.com/docker/machine/releases/download/v0.13.0/docker-machine-`uname -s`-`uname -m` >/tmp/docker-machine && sudo install /tmp/docker-machine /usr/local/bin/docker-machine
for i in 1 2 3; do docker-machine create --driver digitalocean --digitalocean-image  ubuntu-16-04-x64 --digitalocean-access-token $DOTOKEN node-$i; done
docker-machine ssh node-1 "ufw allow 22/tcp"
docker-machine ssh node-1 "ufw allow 2376/tcp"
docker-machine ssh node-1 "ufw allow 2377/tcp"
docker-machine ssh node-1 "ufw allow 7946/tcp"
docker-machine ssh node-1 "ufw allow 7946/udp"
docker-machine ssh node-1 "ufw allow 4789/udp"
docker-machine ssh node-1 "ufw enable"
docker-machine ssh node-1 "systemctl restart docker"
docker-machine ssh node-2 "ufw allow 22/tcp"
docker-machine ssh node-2 "ufw allow 2376/tcp"
docker-machine ssh node-2 "ufw allow 7946/tcp"
docker-machine ssh node-2 "ufw allow 7946/udp"
docker-machine ssh node-2 "ufw allow 4789/udp"
docker-machine ssh node-2 "ufw enable"
docker-machine ssh node-2 "systemctl restart docker"
docker-machine ssh node-3 "ufw allow 22/tcp"
docker-machine ssh node-3 "ufw allow 2376/tcp"
docker-machine ssh node-3 "ufw allow 7946/tcp"
docker-machine ssh node-3 "ufw allow 7946/udp"
docker-machine ssh node-3 "ufw allow 4789/udp"
docker-machine ssh node-3 "ufw enable"
docker-machine ssh node-3 "systemctl restart docker"

#sudo docker run --rm -p 8087:8087 labtester-worker/dockerize:latest >> log.txt &

#java -jar build/libs/labtester-worker-0.1.0.jar >> log.txt &