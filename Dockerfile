FROM openjdk:21-jdk

EXPOSE 8081

ADD ./build/libs/*.jar checking-0.0.1-SNAPSHOT.jar

ENV TZ=Asia/Seoul

ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "checking-0.0.1-SNAPSHOT.jar", "sh", "-c"]