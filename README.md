# spring-boot-grpc-unary-server
Lightweight Spring Boot gRPC server for single request-response calls.

---

# 🌌 Building a Spring Boot gRPC Unary Server for a Defense Personnel Info System (Postman & grpcurl Ready)

---

In the modern defense sector, microservices and efficient communication between distributed components are vital. gRPC, a high-performance RPC framework by Google, is a perfect fit. In this tutorial, we’ll build a **Spring Boot gRPC Unary Server** to serve a **Defense Personnel Information System**.

We'll use only gRPC (no REST), testable via `grpcurl` or Postman's gRPC mode (v10+).

---

## 💡 What You Will Learn

* How to define a unary gRPC service in a `.proto` file
* How to implement a gRPC server in Spring Boot
* How to test it using `grpcurl` or Postman

---

## 📊 Use Case

A Defense Operations Center queries officer information by their ID:

**Request:** Officer ID
**Response:** Name, Rank, Unit

---

## 📄 Step 1: Define gRPC Service (`officer.proto`)

```proto
syntax = "proto3";

option java_multiple_files = true;
option java_package = "com.defense.grpc";
option java_outer_classname = "OfficerProto";

service OfficerService {
  rpc GetOfficerInfo (OfficerRequest) returns (OfficerResponse);
}

message OfficerRequest {
  int32 id = 1;
}

message OfficerResponse {
  string name = 1;
  string rank = 2;
  string unit = 3;
}
```

Place this in `src/main/proto/officer.proto`.

---

## ⚙️ Step 2: Maven Setup

Add the following to your `pom.xml`:

### Dependencies:

```xml
<dependencies>
  <dependency>
    <groupId>net.devh</groupId>
    <artifactId>grpc-server-spring-boot-starter</artifactId>
    <version>2.15.0.RELEASE</version>
  </dependency>
  <dependency>
    <groupId>io.grpc</groupId>
    <artifactId>grpc-stub</artifactId>
    <version>1.59.0</version>
  </dependency>
</dependencies>
```

### Protobuf Plugin:

```xml
<build>
  <extensions>
    <extension>
      <groupId>kr.motd.maven</groupId>
      <artifactId>os-maven-plugin</artifactId>
      <version>1.7.0</version>
    </extension>
  </extensions>
  <plugins>
    <plugin>
      <groupId>org.xolstice.maven.plugins</groupId>
      <artifactId>protobuf-maven-plugin</artifactId>
      <version>0.6.1</version>
      <configuration>
        <protocArtifact>com.google.protobuf:protoc:3.24.0:exe:${os.detected.classifier}</protocArtifact>
        <pluginId>grpc-java</pluginId>
        <pluginArtifact>io.grpc:protoc-gen-grpc-java:1.59.0:exe:${os.detected.classifier}</pluginArtifact>
      </configuration>
      <executions>
        <execution>
          <goals>
            <goal>compile</goal>
            <goal>compile-custom</goal>
          </goals>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```

---

## 🚀 Step 3: Implement the gRPC Server

### `OfficerServiceImpl.java`

```java
package com.defense.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Map;

@GrpcService
public class OfficerServiceImpl extends OfficerServiceGrpc.OfficerServiceImplBase {

    private static final Map<Integer, OfficerResponse> data = Map.of(
        1, OfficerResponse.newBuilder().setName("Lt. Sarah Connor").setRank("Lieutenant").setUnit("AI Defense Division").build(),
        2, OfficerResponse.newBuilder().setName("Capt. John Reese").setRank("Captain").setUnit("Special Forces").build()
    );

    @Override
    public void getOfficerInfo(OfficerRequest request, StreamObserver<OfficerResponse> responseObserver) {
        OfficerResponse response = data.getOrDefault(
            request.getId(),
            OfficerResponse.newBuilder().setName("Unknown").setRank("N/A").setUnit("N/A").build()
        );
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
```

---

## 📊 Step 4: gRPC Server Config

Add to `application.yml`:

```yaml
grpc:
  server:
    port: 9090
```

---

## ✅ Step 5: Test with `grpcurl`

```bash
grpcurl -plaintext -d '{"id": 1}' localhost:9090 OfficerService/GetOfficerInfo
```

**Expected Output:**

```json
{
  "name": "Lt. Sarah Connor",
  "rank": "Lieutenant",
  "unit": "AI Defense Division"
}
```

---

## 🔗 Step 6: Test with Postman (gRPC Mode)

1. Open Postman
2. Click **New > gRPC Request**
3. Import `officer.proto`
4. Connect to: `localhost:9090`
5. Select Method: `OfficerService/GetOfficerInfo`
6. Message Body:

```json
{
  "id": 2
}
```

---

## 🔄 Conclusion

You just built a fully gRPC-native Spring Boot server suitable for a defense environment, with no REST dependencies. This is production-grade architecture for internal microservices with low-latency needs.

---

## 🚀 Next Steps

* Add TLS to secure gRPC traffic
* Convert to bidirectional streaming for live updates
* Integrate with a real database

---

If you found this helpful, follow me for more backend and microservices deep-dives!

---
