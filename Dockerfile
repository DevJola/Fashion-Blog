FROM openjdk:17
EXPOSE 8081
ADD target/paulakerejola.jar paulakerejola.jar
ENTRYPOINT ["java", "-jar", "/paulakerejola.jar"]
