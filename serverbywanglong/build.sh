#!/usr/bin/env bash

if [ ! -n "$1" ] ;then
    echo "you have not input a word!"
    exit 0
else
    echo "the word you profile is $1"
    echo "git pull"
    git pull
fi

mvnStr="mvn clean -am "
for i in $@
do
case "$i" in
   api)
    mvnStr+=" -pl :power-api "
    docker rm -f api
    docker rmi md-api:latest
    ;;
   broker)
    mvnStr+=" -pl :power-broker "
    docker rm -f broker
    docker rmi md-broker:latest
    ;;
   notifier)
    mvnStr+=" -pl :power-notifier "
    docker rm -f notifier
    docker rmi md-notifier:latest
    ;;
   job)
    mvnStr+=" -pl :power-job "
    docker rm -f job
    docker rmi md-job:latest
    ;;
esac
done

if [ "$1" == "test" ] ;then
  mvnStr+=" package -DskipTests -Ptest "
elif [ "$1" == "prod" ] ;then
  mvnStr+=" package -DskipTests -Pprod "
fi

echo $mvnStr

$mvnStr

for i in $@
do
case "$i" in
   api)

    cd api && mvn docker:build
#    docker run --name api --restart=always -p 127.0.0.1:8080:8080 -d --link redis:redis --link mysql:mysql --link es:es --link rabbit:rabbit  -v /data/logs:/data/logs md-api:latest
#    docker-compose -p api -f src/main/docker/docker-compose.yml up -d
     docker images md-api -q |sed '1,3d'|xargs --no-run-if-empty docker rmi -f
    cd ../
    ;;
   broker)

    cd broker && mvn docker:build
#    docker run --name broker --restart=always  -p 127.0.0.1:8082:8082 -d --link redis:redis --link mysql:mysql --link es:es --link rabbit:rabbit  -v /data/logs:/data/logs md-broker:latest
#    docker-compose -p broker -f src/main/docker/docker-compose.yml up -d
docker images md-broker -q |sed '1,3d'|xargs --no-run-if-empty docker rmi -f
    cd ../
    ;;
   notifier)

    cd notifier && mvn docker:build
#    docker run --name notifier --restart=always -p 127.0.0.1:8081:8081 -d  --link rabbit:rabbit   -v /data/logs:/data/logs md-notifier:latest
#    docker-compose -p notifier -f src/main/docker/docker-compose.yml up -d
docker images md-notifier -q |sed '1,3d'|xargs --no-run-if-empty docker rmi -f
    cd ../
    ;;
   job)

    cd job && mvn docker:build
#    docker run --name job --restart=always -p 127.0.0.1:8085:8085 -d --link redis:redis --link mysql:mysql --link es:es --link rabbit:rabbit  -v /data/logs:/data/logs md-job:latest
#    docker-compose -p job -f src/main/docker/docker-compose.yml up -d
docker images md-job -q |sed '1,3d'|xargs --no-run-if-empty docker rmi -f
    cd ../
    ;;
esac
done


docker-compose -p power up -d