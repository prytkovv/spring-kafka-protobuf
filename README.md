# Spring Kafka with Protobuf Example

## Overview

This project demonstrates how to integrate [Spring Kafka](https://spring.io/projects/spring-kafka) with [Protocol Buffers (Protobuf)](https://developers.google.com/protocol-buffers) for efficient message serialization and communication in a Kafka-based architecture.

## Features

- **Kafka Consumer**: Uses Spring Kafka to receive messages.
- **Kafka Producer**: Uses confluent-kafka-python to send messages.
- **Protobuf Serialization**: The Protobuf schema defines the structure of the messages.
- **Spring Boot Integration**: Built with Spring Boot for easy configuration and deployment.

## Prerequisites

To compile the Protobuf files yourself, you need [Protocol Buffers Compiler (protoc)](https://grpc.io/docs/protoc-installation/) version 24.4. You can download it from the [github release page](https://github.com/protocolbuffers/protobuf/releases/tag/v24.4).

- Java 17 or later
- Python 3.10 or later
- Apache Kafka (local or managed cluster)

## Installation

Build the Java project:
```
mvn clean install
```

Install dependencies for the Python client:
```
python3.10 -m venv producer-venv
source producer-venv/bin/activate
pip install -r requirements.txt
```

## Running the Application

Start the application:
```
mvn spring-boot:run
```

Send messages:
```
python3 producer.py -b localhost:9092 -s http://localhost:8081 -t product.issued
```
