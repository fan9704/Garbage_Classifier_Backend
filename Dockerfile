FROM maven:3.8.3-openjdk-17

WORKDIR /app
COPY . /app
EXPOSE 8080

CMD [ "mvn","spring-boot:run" ]