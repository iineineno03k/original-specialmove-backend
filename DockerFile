# ビルドステージ
FROM amazoncorretto:17 AS build
WORKDIR /home/app
COPY ./src /home/app/src
COPY build.gradle settings.gradle /home/app/
RUN ./gradlew build

# 実行ステージ
FROM amazoncorretto:17-alpine
WORKDIR /usr/local/lib
COPY --from=build /home/app/build/libs/original-specialmove-0.0.1-SNAPSHOT.jar original-specialmove.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dfile.encoding=UTF-8", "original-specialmove.jar"]
