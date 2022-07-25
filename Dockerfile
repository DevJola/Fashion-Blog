FROM openjdk:17
EXPOSE 8080
ADD target/paulakerejola.jar paulakerejola.jar
ENTRYPOINT ["java", "-jar", "/paulakerejola.jar"]
