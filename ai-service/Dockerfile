FROM openjdk:17-jdk-slim

RUN apt-get update && apt-get install -y wget

ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /usr/local/bin/wait-for-it
RUN chmod +x /usr/local/bin/wait-for-it

WORKDIR /app
COPY build/libs/*.jar app.jar

ENTRYPOINT ["/bin/sh", "-c", "wait-for-it $CONFIG_HOST:$CONFIG_PORT --timeout=60 -- java -XX:-UseContainerSupport -jar app.jar"]