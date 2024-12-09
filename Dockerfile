FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} eval1-0.0.1-SNAPSHOT.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app-0.0.1-SNAPSHOT.jar"]
