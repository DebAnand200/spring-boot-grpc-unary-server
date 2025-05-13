//package com.deb.spring_boot_grpc_unary_server.service;
//
//import com.deb.spring_boot_grpc_unary_server.banking.AccountBalanceResponse;
//import com.deb.spring_boot_grpc_unary_server.banking.AccountRequest;
//import io.grpc.stub.StreamObserver;
//import org.springframework.grpc.server.service.GrpcService;
//
//@GrpcService
//public class BankAccountBalanceService extends com.deb.spring_boot_grpc_unary_server.banking.AccountBalanceServiceGrpc.AccountBalanceServiceImplBase {
//
//    @Override
//    public void getAccountBalance(AccountRequest request, StreamObserver<AccountBalanceResponse> responseObserver) {
//        AccountBalanceResponse empResponse = com.deb.spring_boot_grpc_unary_server.banking.AccountBalanceResponse.newBuilder()
//                .setAccountNumber(request.getAccountNumber())
//                .setBalance(100)
//                .build();
//
//        //set the response object
//        responseObserver.onNext(empResponse);
//
//        //mark process is completed
//        responseObserver.onCompleted();
//    }
//}
