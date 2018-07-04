FROM java:alpine
ADD  md-broker.jar /app/broker.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","-Duser.timezone=Asia/Shanghai","-Dfile.encoding=utf-8","-Duser.language=zh","-Djava.security.egd=file:/dev/./urandom","/app/broker.jar"]
CMD ["sh"]