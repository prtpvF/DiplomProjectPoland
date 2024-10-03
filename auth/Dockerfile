FROM openjdk:17
WORKDIR /opt/app
EXPOSE 8089
COPY  ./target/*.jar /opt/app/*.jar
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar"]