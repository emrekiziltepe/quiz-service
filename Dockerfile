FROM eclipse-temurin:21-jdk-alpine
EXPOSE 8080
ADD target/quiz-app.jar quiz-app.jar
ENTRYPOINT ["java","-jar","/quiz-app.jar"]