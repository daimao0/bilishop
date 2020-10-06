FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD bilibilishop-1.0.0.jar app.jar

ENV JAVA_OPTS="\

-Dcom.sun.management.jmxremote \

-Dcom.sun.management.jmxremote.rmi.port=1099 \

-Dcom.sun.management.jmxremote.port=1099 \

-Dcom.sun.management.jmxremote.ssl=false \

-Dcom.sun.management.jmxremote.authenticate=false \

-Djava.rmi.server.hostname=192.168.43.130 "
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

RUN echo "Asia/Shanghai" > /etc/timezone;