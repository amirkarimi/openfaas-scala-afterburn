FROM java:8-jre-alpine
# Use any image as your base image, or "scratch"
# Add fwatchdog binary via https://github.com/openfaas/faas/releases/
# Then set fprocess to the process you want to invoke per request - i.e. "cat" or "my_binary"

#ADD https://github.com/openfaas/faas/releases/download/0.6.9/fwatchdog /usr/bin
COPY ./of-watchdog /usr/bin/fwatchdog
RUN chmod +x /usr/bin/fwatchdog

COPY ./openfaas-scala-afterburn-assembly-0.1.0-SNAPSHOT.jar /

# Run the main program
ENV mode=afterburn
ENV fprocess="java -jar /openfaas-scala-afterburn-assembly-0.1.0-SNAPSHOT.jar"

HEALTHCHECK --interval=5s CMD [ -e /tmp/.lock ] || exit 1
CMD ["fwatchdog"]
