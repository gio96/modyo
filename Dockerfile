FROM eclipse-temurin:17
EXPOSE 8080
RUN echo "$PWD"
ADD ./target/modyo.jar modyo.jar
ENTRYPOINT ["java","-jar","/modyo.jar"]
