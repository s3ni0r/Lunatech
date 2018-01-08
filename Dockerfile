FROM anapsix/alpine-java:jdk8

ENV SBT_VERSION 0.13.15
ENV CHECKSUM 18b106d09b2874f2a538c6e1f6b20c565885b2a8051428bd6d630fb92c1c0f96

# Install sbt
RUN apk add --update bash curl openssl ca-certificates && \
  curl -L -o /tmp/sbt.zip \
    https://dl.bintray.com/sbt/native-packages/sbt/${SBT_VERSION}/sbt-${SBT_VERSION}.zip && \
  openssl dgst -sha256 /tmp/sbt.zip \
    | grep ${CHECKSUM} \
    || (echo 'shasum mismatch' && false) && \
  mkdir -p /opt/sbt && \
  unzip /tmp/sbt.zip -d /opt/sbt && \
  rm /tmp/sbt.zip && \
  chmod +x /opt/sbt/sbt/bin/sbt && \
  ln -s /opt/sbt/sbt/bin/sbt /usr/bin/sbt && \
  rm -rf /tmp/* /var/cache/apk/*

RUN mkdir -p /usr/src/api
WORKDIR /usr/src/api
RUN sbt compile
EXPOSE 9000
CMD ["sbt"]