FROM openjdk:21-jdk-slim
COPY target/messenger-0.0.1-SNAPSHOT.war messenger-0.0.1-SNAPSHOT.war
RUN mkdir /root/messagesAttachments
ENTRYPOINT ["java", "-jar", "messenger-0.0.1-SNAPSHOT.war"]