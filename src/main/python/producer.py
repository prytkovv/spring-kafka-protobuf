import time
import argparse
import datetime
from google.protobuf.timestamp_pb2 import Timestamp
from confluent_kafka import Producer
from confluent_kafka.serialization import MessageField
from confluent_kafka.serialization import SerializationContext
from confluent_kafka.schema_registry import SchemaRegistryClient
from confluent_kafka.schema_registry.protobuf import ProtobufSerializer

from product_pb2 import Product
from product_pb2 import Image


def on_delivery(err, msg):
    if err is not None:
        print("Delivery failed with error: {}".format(err))
        return
    print('Record {} successfully produced to {} [{}] at offset {}'
          .format(msg.key(), msg.topic(), msg.partition(), msg.offset()))


def datetime_to_timestamp(dt):
    ts = dt.timestamp()
    return Timestamp(seconds=int(ts), nanos=int(ts % 1 * 1e9))


def main(args):
    topic = args.topic
    schema_registry_conf = {'url': args.schema_registry}
    schema_registry_client = SchemaRegistryClient(schema_registry_conf)
    protobuf_serializer = ProtobufSerializer(Product, schema_registry_client, {'use.deprecated.format': False})
    producer_conf = {'bootstrap.servers': args.bootstrap_servers}
    producer = Producer(producer_conf)
    while True:
        producer.poll(0.0)
        try:
            image = Image(content=b'content-sample',
                          width=1920,
                          height=1080,
                          format='jpeg',
                          camera_id='camera-id')
            product = Product(image=image,
                              barcode='barcode-sample',
                              category_id=42,
                              manufacturer_id=17,
                              manufactured_at=datetime.datetime.now(),
                              created_at=datetime.datetime.now())
            producer.produce(topic=topic,
                             partition=0,
                             value=protobuf_serializer(product, SerializationContext(topic, MessageField.VALUE)),
                             on_delivery=on_delivery)
            time.sleep(1)
        except (KeyboardInterrupt, EOFError):
            break
    producer.flush()


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('-b', dest="bootstrap_servers", required=True, help="Bootstrap broker(s) (host[:port])")
    parser.add_argument('-s', dest="schema_registry", required=True, help="Schema Registry (http(s)://host[:port]")
    parser.add_argument('-t', dest="topic", default="topic", help="Topic name")
    main(parser.parse_args())
