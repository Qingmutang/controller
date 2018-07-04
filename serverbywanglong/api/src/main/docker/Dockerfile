FROM gaobin2496/java:alpine
#RUN apk --update add ttf-dejavu
ADD  md-boot.jar /app/api.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Duser.timezone=Asia/Shanghai","-Dfile.encoding=utf-8","-Duser.language=zh","-Djava.security.egd=file:/dev/./urandom","/app/api.jar"]
CMD ["sh"]