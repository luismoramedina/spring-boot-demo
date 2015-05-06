FROM ubuntu
MAINTAINER luismoramedina@gmail.com

RUN apt-get -y update

RUN apt-get install -y openjdk-7-jdk
RUN apt-get -y install maven
RUN apt-get -y install git

ENV JAVA_HOME /usr/lib/jvm/java-7-openjdk-amd64
EXPOSE 8080

WORKDIR /

ADD target/demo-0.0.1-SNAPSHOT.jar /
RUN echo 'java -jar demo-0.0.1-SNAPSHOT.jar'
