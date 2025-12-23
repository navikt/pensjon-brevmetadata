FROM gcr.io/distroless/java25
WORKDIR /app
COPY build/libs/pensjon-brevdata-*.jar pensjon-brevdata.jar
EXPOSE 8080
USER nonroot
CMD ["pensjon-brevdata.jar"]