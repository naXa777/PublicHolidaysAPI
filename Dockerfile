FROM openjdk:11
ADD build/libs/holidays.jar holidays.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "holidays.jar"]
