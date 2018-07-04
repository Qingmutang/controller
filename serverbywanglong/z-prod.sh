#!/usr/bin/env bash
git pull
docker rm -f api
docker rmi md-api:latest
mvn clean -am -pl :power-api  package -DskipTests -Pprod && cd api && mvn docker:build
docker rm -f api
docker run --name api -p 8080:8080 -d --link redis:redis --link mysql:mysql --link es:es  -v /data/logs:/data/logs md-api
docker logs -f api