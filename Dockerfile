FROM adoptopenjdk/openjdk11:alpine-jre
#WORKDIR /src/main/app
COPY target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]