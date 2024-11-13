# 빌드 전용 단계
FROM openjdk:17-jdk-slim AS build

WORKDIR /app

# 소스 파일을 복사하고 빌드 실행
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test

# 실제 실행을 위한 이미지
FROM openjdk:17-jdk-slim

WORKDIR /app

# 빌드 단계에서 생성된 JAR 파일만 복사
COPY --from=build /app/build/libs/*.jar app.jar

# Java 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]