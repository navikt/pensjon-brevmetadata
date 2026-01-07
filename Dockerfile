# syntax=docker/dockerfile:1
FROM gcr.io/distroless/java25-debian13
COPY --exclude=*pensjon-brevdata-*.jar build/install/pensjon-brevdata/lib /app/lib
COPY build/install/pensjon-brevdata/lib/pensjon-brevdata-*.jar /app/lib
EXPOSE 8080
ENTRYPOINT ["java", "-cp", "/app/lib/*", "no.nav.pensjonbrevdata.PensjonBrevdataApplicationKt"]