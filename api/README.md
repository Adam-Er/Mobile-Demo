# Pre Requisites
Install the latest go version and add it to your environment path variable

Install the latest docker version.

## Mac
# brew install protobuf
# go install google.golang.org/protobuf/cmd/protoc-gen-go@v1.28
# go install google.golang.org/grpc/cmd/protoc-gen-go-grpc@v1.2

# Update your PATH so that the protoc compiler can find the plugins:
# export PATH="$PATH:$(go env GOPATH)/bin"

Generate the server grpc code by navigating to Mobile-Demo and running 'sh protoc.sh'. This should drop the server code into the api folder as calc_grpc.pb.go and calc.pb.go

# Mac Instructions

To start the api, navigate to main directory and run 'go run main.go'

# Docker instructions
Run the following compose command to kick of the docker container:
docker-compose -p demo-app up -d

# GRPC
To update the GRPC service contract, edit the file found in proto/calc.proto
