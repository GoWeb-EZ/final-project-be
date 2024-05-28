# 사용할 base 이미지를 정의합니다.
FROM openjdk:17-jdk-alpine

# 작업 디렉토리를 설정합니다.
WORKDIR /usr/src/app

# 호스트의 JAR 파일을 컨테이너의 작업 디렉토리로 복사합니다.
COPY ./build/libs/*.jar /app/application.jar

# 컨테이너가 실행될 때 실행될 명령을 정의합니다.
CMD ["java", "-jar", "application.jar"]