FROM eclipse-temurin:17
EXPOSE 8080
#RUN echo $PWD
#ADD ./target/modyo.jar modyo.jar
#ADD target/modyo.jar modyo.jar
ADD target/*.jar modyo.jar
#ADD modyo.jar modyo.jar
ENTRYPOINT ["java","-jar","/modyo.jar"]
#ENTRYPOINT ["java","-jar","modyo.jar"]


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