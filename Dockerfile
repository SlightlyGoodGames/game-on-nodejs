FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY libs/* libs/
COPY src/main/java src/main/java

RUN mkdir -p out

RUN echo "=== LIBS ===" && ls -lah libs

RUN javac \
    -cp "libs/*"  \
    -d out \
    $(find src/main/java -name "*.java")

CMD ["java", "-cp", "out:libs/*", "net.gameonline.server.Main"]