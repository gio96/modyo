FROM eclipse-temurin:17
#ARG PROFILE
#ENV PROFILE_VAR=$PROFILE
VOLUME /tmp
## Add the built jar for docker image building
ADD target/modyo.jar modyo.jar
#ENTRYPOINT ["/bin/bash", "-c", "java","-Dspring.profiles.active=$PROFILE_VAR","-jar","/modyo.jar"]
ENTRYPOINT ["java","-jar","/modyo.jar"]
## DO NOT USE(The variable would not be substituted): ENTRYPOINT ["java","-Dspring.profiles.active=$PROFILE_VAR","-jar","/hello-world-docker-action.jar"]
## CAN ALSO USE: ENTRYPOINT java -Dspring.profiles.active=$PROFILE_VAR -jar /hello-world-docker-action.jar
EXPOSE 80


#FROM eclipse-temurin:17
#EXPOSE 8080
#COPY target/modyo.jar modyo.jar
#ENTRYPOINT ["java","-jar","/modyo.jar"]


#FROM eclipse-temurin:17
#EXPOSE 8080
##RUN echo $PWD
##ADD ./target/modyo.jar modyo.jar
##ADD target/modyo.jar modyo.jar
#VOLUME /tmp
##ADD target/*.jar modyo.jar
##COPY target/modyo.jar modyo.jar
#COPY artifacts/modyo.jar modyo.jar
##ADD modyo.jar modyo.jar
#ENTRYPOINT ["java","-jar","/modyo.jar"]
##ENTRYPOINT ["java","-jar","modyo.jar"]


#FROM eclipse-temurin:17
#WORKDIR /app
#EXPOSE 8080
#RUN echo $PWD
#CMD echo $PWD
#CMD echo "$PWD"
#CMD ["ls", "-l"]
#ARG JAR_FILE=target/modyo.jar
#COPY ${JAR_FILE} /app/modyo.jar
##ADD ./target/modyo.jar modyo.jar
#ENTRYPOINT ["java","-jar","/modyo.jar"]