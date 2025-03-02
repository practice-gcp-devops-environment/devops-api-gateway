# builder image
FROM amazoncorretto:17-al2-jdk AS builder

RUN mkdir /devops-api-gateway
WORKDIR /devops-api-gateway

COPY . .

RUN chmod +x gradlew
RUN ./gradlew clean bootJar

# runtime image
FROM amazoncorretto:17.0.12-al2

ENV TZ=Asia/Seoul
ENV PROFILE=${PROFILE}

RUN mkdir /devops-api-gateway
WORKDIR /devops-api-gateway

COPY --from=builder /devops-api-gateway/build/libs/devops-api-gateway-* /devops-api-gateway/app.jar

CMD ["sh", "-c", " \
    java -Dspring.profiles.active=${PROFILE} \
         -jar /devops-api-gateway/app.jar"]
