FROM java:alpine
ADD  md-job.jar /app/job.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","-Duser.timezone=Asia/Shanghai","-Dfile.encoding=utf-8","-Duser.language=zh","-Djava.security.egd=file:/dev/./urandom","/app/job.jar"]
CMD ["sh"]