# syntax=docker/dockerfile:1

FROM golang:1.22 as dev

# Set destination for COPY

# Download Go modules
WORKDIR /app

COPY go.mod go.sum ./
COPY proto ./proto
COPY *.go ./
RUN go mod download




# # Copy the source code. Note the slash at the end, as explained in
# # https://docs.docker.com/reference/dockerfile/#copy

# Build
RUN CGO_ENABLED=0 GOOS=linux go build -o /docker-gs-ping

# Optional:
# To bind to a TCP port, runtime parameters must be supplied to the docker command.
# But we can document in the Dockerfile what ports
# the application is going to listen on by default.
# https://docs.docker.com/reference/dockerfile/#expose
EXPOSE 8080

# Run
CMD ["/docker-gs-ping"]