FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

RUN mkdir -p out && \
    javac -d out $(find src/main/java -name "*.java")

CMD ["java", "-cp", "out", "net.gameonline.server.Main"]