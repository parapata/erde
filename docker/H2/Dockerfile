FROM adoptopenjdk/openjdk11:alpine
RUN apk update \
  && apk add --no-cache bash \
  && cd /opt \
  && wget http://www.h2database.com/h2-2019-10-14.zip \
  && unzip h2-*.zip \
  && rm -f h2-*.zip \
  && mkdir -p /opt/h2/data
EXPOSE 9092 5435 8082
CMD ["java", "-Xmx1024M", "-cp", "/opt/h2/bin/h2-1.4.200.jar", "org.h2.tools.Server", "-tcp", "-tcpAllowOthers", "-web", "-webAllowOthers", "-pg", "-pgAllowOthers", "-baseDir", "/opt/h2/data", "-ifNotExists"]
