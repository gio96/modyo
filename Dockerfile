FROM eclipse-temurin:17
EXPOSE 8080
COPY target/modyo.jar modyo.jar
ENTRYPOINT ["java","-jar","/modyo.jar"]
