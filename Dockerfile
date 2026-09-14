FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY libs/Java-WebSocket-1.6.0.jar libs/
COPY src/main/java src/main/java

RUN mkdir -p out

RUN javac \
    -cp "libs/Java-WebSocket-1.6.0.jar" \
    -d out \
    $(find src/main/java -name "*.java")

CMD ["java", "-cp", "out:libs/Java-WebSocket-1.6.0.jar", "net.gameonline.server.Main"]