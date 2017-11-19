FROM java:8-jre-alpine
# Use any image as your base image, or "scratch"
# Then set fprocess to the process you want to invoke per request - i.e. "cat" or "my_binary"

RUN apk add --no-cache curl && \
    curl -sfL \
    https://github.com/openfaas-incubator/of-watchdog/releases/download/0.0.4/of-watchdog > /usr/bin/fwatchdog && \
    chmod +x /usr/bin/fwatchdog

COPY ./function/openfaas-scala-afterburn-assembly-0.1.0-SNAPSHOT.jar /openfaas-scala-afterburn-assembly-0.1.0-SNAPSHOT.jar

ENV fprocess="java -jar /app.jar"
ENV afterburn=true
ENV mode=afterburn
RUN touch /tmp/.lock

HEALTHCHECK --interval=5s CMD [ -e /tmp/.lock ] || exit 1
EXPOSE 8080
CMD ["fwatchdog"]
