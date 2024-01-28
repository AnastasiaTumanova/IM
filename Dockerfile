FROM openjdk:21
ADD target/IM2-0.0.1-SNAPSHOT.jar IM2-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/IM2-0.0.1-SNAPSHOT.jar"]