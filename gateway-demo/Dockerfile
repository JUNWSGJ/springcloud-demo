FROM openjdk:8-jdk
ENV TZ=Asia/Shanghai \
    JAVA_OPTS="" JAVA_AGENT="" JAVA_ARGS=""

WORKDIR /

RUN sed -i s/deb.debian.org/mirrors.cloud.tencent.com/g /etc/apt/sources.list && \
    sed -i s/security.debian.org/mirrors.cloud.tencent.com/g /etc/apt/sources.list

RUN apt-get update && \
    apt-get install -y tini ca-certificates-java sysstat && \
    apt-get clean && \
    update-ca-certificates -f;

RUN wget https://arthas.aliyun.com/arthas-boot.jar

ADD target/gateway-demo-exec.jar /gateway-demo-exec.jar

ENTRYPOINT ["/usr/bin/tini", "--", "sh", "-c", "java $JAVA_OPTS $JAVA_AGENT -Djasypt.encryptor.password=${JASYPT_PASSWORD} -Djava.security.egd=file:/dev/./urandom -jar /gateway-demo-exec.jar $JAVA_ARGS" ]
EXPOSE 8080