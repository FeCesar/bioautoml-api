FROM openjdk:11

COPY --chown=appuser:appuser target/**.jar /home/appuser/app.jar

EXPOSE 8764

ENTRYPOINT [ "java", "-jar", "./app.jar" ]
