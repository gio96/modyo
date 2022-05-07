FROM eclipse-temurin:17
VOLUME /tmp
ADD target/modyo.jar modyo.jar
ENTRYPOINT ["java","-jar","/modyo.jar"]
EXPOSE 80