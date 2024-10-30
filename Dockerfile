FROM gcr.io/distroless/java21
WORKDIR /app
COPY target/pensjon-brevdata.jar ./
EXPOSE 8080
USER nonroot
CMD ["pensjon-brevdata.jar"]