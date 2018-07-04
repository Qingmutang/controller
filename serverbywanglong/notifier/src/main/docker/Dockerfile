FROM java:alpine
ADD  md-notifier.jar /app/notifier.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","-Duser.timezone=Asia/Shanghai","-Dfile.encoding=utf-8","-Duser.language=zh","-Djava.security.egd=file:/dev/./urandom","/app/notifier.jar"]
CMD ["sh"]