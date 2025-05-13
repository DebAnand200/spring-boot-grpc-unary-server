package com.deb.spring_boot_grpc_unary_server.service;

import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;
import com.deb.spring_boot_grpc_unary_server.officer.OfficerServiceGrpc;
import com.deb.spring_boot_grpc_unary_server.officer.OfficerResponse;
import com.deb.spring_boot_grpc_unary_server.officer.OfficerRequest;

import java.util.Map;

@GrpcService
public class OfficerServiceImpl extends OfficerServiceGrpc.OfficerServiceImplBase {

    private static final Map<Integer, OfficerResponse> officerData = Map.of(
        1, OfficerResponse.newBuilder().setName("Captain Jane Doe").setRank("Captain").setUnit("Cyber Command").build(),
        2, OfficerResponse.newBuilder().setName("Major John Smith").setRank("Major").setUnit("Signal Corps").build()
    );

    @Override
    public void getOfficerInfo(OfficerRequest request, StreamObserver<OfficerResponse> responseObserver) {
        OfficerResponse response = officerData.getOrDefault(
            request.getId(),
            OfficerResponse.newBuilder().setName("Unknown").setRank("N/A").setUnit("N/A").build()
        );
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
