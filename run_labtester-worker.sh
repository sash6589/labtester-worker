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

sudo docker build -f Dockerfile -t labtestersystem/labtester:latest .
docker login --username labtestersystem --password labtester
docker push labtestersystem/labtester:latest

curl -L https://github.com/docker/machine/releases/download/v0.13.0/docker-machine-`uname -s`-`uname -m` >/tmp/docker-machine && sudo install /tmp/docker-machine /usr/local/bin/docker-machine
for i in 1 2 3; do docker-machine create --driver digitalocean --digitalocean-image  ubuntu-16-04-x64 --digitalocean-access-token $1 node-$i; done
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

leader_ip=$(docker-machine ip node-1)

echo "### Initializing Swarm mode ..."
eval $(docker-machine env node-1)
docker swarm init --advertise-addr $leader_ip

worker_token=$(docker swarm join-token worker -q)

for c in {2..3} ; do
    eval $(docker-machine env node-$c)
    docker swarm join --token $worker_token $leader_ip:2377
done

docker-machine ssh node-1 "
    docker login --username labtestersystem --password labtester;
    docker service create -p 8087:8087 --with-registry-auth --name labtester-worker labtestersystem/labtester:latest
"


#sudo docker run --rm -p 8087:8087 labtester-worker/dockerize:latest >> log.txt &

#java -jar build/libs/labtester-worker-0.1.0.jar >> log.txt &