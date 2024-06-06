# # ビルドステージ
# FROM amazoncorretto:17 AS build
# # コンテナ内の作業ディレクトリを設定
# WORKDIR /app
# # Gradle Wrapperをコピー
# COPY gradlew .
# COPY gradle gradle
# # Gradleの依存関係をコピー
# COPY build.gradle .
# COPY settings.gradle .
# # プロジェクトのソースコードをコピー
# COPY src src
# # 依存関係を解決してビルド
# RUN ./gradlew build
# # ビルドが成功したら、JARファイルをコピー
# COPY build/libs/original-specialmove-0.0.1-SNAPSHOT.jar app.jar
# # アプリケーションを実行
# CMD ["java", "-jar", "app.jar"]

# FROM amazoncorretto:17 AS build
# COPY ./ /home/app
# USER root
# RUN chmod +x gradlew
# RUN cd /home/app && ./gradlew build

# FROM amazoncorretto:17-alpine
# COPY --from=build /home/app/build/libs/original-specialmove-0.0.1-SNAPSHOT.jar /usr/local/lib/original-specialmove.jar
# EXPOSE 8080
# ENTRYPOINT ["java", "-jar", "-Dfile.encoding=UTF-8", "/usr/local/lib/original-specialmove.jar"]

#
# Build stage
#
FROM gradle:jdk17-jammy AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

LABEL org.name="originalspecialmove"
#
# Package stage
#
FROM --platform=linux/x86_64 eclipse-temurin:17-jdk-jammy
COPY --from=build /home/gradle/src/build/libs/original-specialmove-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

