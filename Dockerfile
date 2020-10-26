FROM openjdk:8-alpine

RUN apk --update add curl

RUN mkdir /usr/local/sombrero

ADD target/sombrero-1.0-SNAPSHOT.jar /usr/local/sombrero

RUN chmod +x /usr/local/sombrero/sombrero-1.0-SNAPSHOT.jar

CMD ["java","-jar","/usr/local/sombrero/sombrero-1.0-SNAPSHOT.jar"]

EXPOSE 8080
