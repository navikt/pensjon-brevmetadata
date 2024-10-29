FROM gcr.io/distroless/java21
WORKDIR /app
COPY target/pensjon-brevdata.jar.jar ./
EXPOSE 8080
USER nonroot
CMD ["pensjon-brevdata.jar"]