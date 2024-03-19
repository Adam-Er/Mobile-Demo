package main

import (
	"log"
	"net"
	"sync"

	"golang.org/x/net/context"
	"google.golang.org/grpc"
	"google.golang.org/grpc/reflection"

	proto "com.delta.MobileApp"
)

type server struct {
	proto.UnimplementedCalculatorServer
	mu sync.Mutex
}

func (s *server) Plus(ctx context.Context, in *proto.CalcRequest) (*proto.CalcReply, error) {
	log.Printf("Plus request received: %d %d", in.NumberA, in.NumberB)
	result := in.NumberA + in.NumberB
	return &proto.CalcReply{Result: result}, nil
}

func newServer() *server {
	s := &server{}
	return s
}

func main() {
	log.Printf("Start gRPC server")

	lis, err := net.Listen("tcp", "0.0.0.0:4300")
	if err != nil {
		log.Fatalf("Can not listen the port：%v", err)
	}

	s := grpc.NewServer()

	proto.RegisterCalculatorServer(s, newServer())

	reflection.Register(s)
	if err := s.Serve(lis); err != nil {
		log.Fatalf("Can not provide service：%v", err)
	}
}
