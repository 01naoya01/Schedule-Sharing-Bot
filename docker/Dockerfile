FROM openjdk:11
ENV APP_ROOT /app
COPY . $APP_ROOT
WORKDIR $APP_ROOT
ENTRYPOINT ["sh", "./gradlew", "bootRun"]


#ENV APP_ROOT /app
#COPY . $APP_ROOT
#WORKDIR $APP_ROOT
#RUN chmod 777 $APP_ROOT/build/libs/Schedule-Sharing-Bot-0.0.1-SNAPSHOT.jar
#ENTRYPOINT ["java","-jar","app/build/libs/Schedule-Sharing-Bot-0.0.1-SNAPSHOT.jar"]


#ARG JAR_FILE=./build/libs/Schedule-Sharing-Bot-0.0.1-SNAPSHOT.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]