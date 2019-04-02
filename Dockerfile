FROM openjdk:8

ADD . /app
WORKDIR /app
ENV PATH /app:$PATH
RUN chmod +x gradlew
EXPOSE 9000
