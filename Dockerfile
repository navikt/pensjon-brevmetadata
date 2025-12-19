FROM gcr.io/distroless/java25
WORKDIR /app
COPY target/pensjon-brevdata.jar ./
EXPOSE 8080
USER nonroot
CMD ["pensjon-brevdata.jar"]