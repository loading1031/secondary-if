FROM openjdk:17-jdk-slim

WORKDIR /app

# Dockerfile이 위치한 디렉토리의 내용을 컨테이너의 /app 디렉토리로 복사합니다.
COPY . .

# gradlew 스크립트에 실행 권한 부여 및 빌드 실행
RUN chmod +x ./gradlew
RUN ./gradlew clean build

# JAR 파일을 app.jar로 이름 변경 및 이동
ARG JAR_FILE=build/libs/*.jar
RUN mv $JAR_FILE app.jar

# Java 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]

