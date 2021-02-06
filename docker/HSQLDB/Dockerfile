FROM adoptopenjdk/openjdk11:alpine
RUN apk update \
  && apk add --no-cache bash \
  && wget https://sourceforge.net/projects/hsqldb/files/hsqldb/hsqldb_2_5/hsqldb-2.5.1.zip \
  && unzip hsqldb-2.5.1.zip \
  && mkdir -p /opt/hsqldb \
  && cp hsqldb-2.5.1/hsqldb/lib/hsqldb.jar /opt/hsqldb/ \
  && cp hsqldb-2.5.1/hsqldb/lib/sqltool.jar /opt/hsqldb/ \
  && rm hsqldb-2.5.1.zip \
  && rm -r hsqldb-2.5.1
EXPOSE 9001
CMD ["java", "-Dhsqldb.method_class_names=\"\"", "-Xmx512M", "-cp", "/opt/hsqldb/hsqldb.jar", "org.hsqldb.Server", "-database.0", "file:/opt/hsqldb/data/demo", "-dbname.0", "demo"]
