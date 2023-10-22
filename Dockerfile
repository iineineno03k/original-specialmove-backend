# ビルドステージ
FROM amazoncorretto:17 AS build
# コンテナ内の作業ディレクトリを設定
WORKDIR /app
# Gradle Wrapperをコピー
COPY gradlew .
COPY gradle gradle
# Gradleの依存関係をコピー
COPY build.gradle .
COPY settings.gradle .
# プロジェクトのソースコードをコピー
COPY src src
# 依存関係を解決してビルド
RUN ./gradlew build
# ビルドが成功したら、JARファイルをコピー
COPY build/libs/original-specialmove-0.0.1-SNAPSHOT.jar app.jar
# アプリケーションを実行
CMD ["java", "-jar", "app.jar"]
