FROM ubuntu
MAINTAINER luismoramedina@gmail.com

RUN apt-get -y update
RUN apt-get install -y openjdk-7-jdk

ENV JAVA_HOME /usr/lib/jvm/java-7-openjdk-amd64
EXPOSE 8080

WORKDIR /

ADD target/demo-0.0.1-SNAPSHOT.jar /
RUN echo 'java -jar demo-0.0.1-SNAPSHOT.jar'

#To run it
# docker build -t spring-boot-demo
# docker run -p 8080:8080 spring-boot-demo java -jar demo-0.0.1-SNAPSHOT.jar
# http://%boot2docker ip%:8080/
