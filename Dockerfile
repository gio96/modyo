FROM eclipse-temurin:17
COPY target/modyo.jar modyo.jar
ENTRYPOINT ["java","-jar","/modyo.jar"]
