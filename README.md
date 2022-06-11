# Security
[rbac](https://medium.com/geekculture/role-based-access-control-rbac-with-spring-boot-and-jwt-bc20a8c51c15)
## jks
```keytool -genkey -v -keystore keystore.jks -alias jwtsigning -keyalg RSA -keysize 2048 -validity 10000```

- pwd: 123456
- others: ai


# MQ

## Create topic
```bash
(venv) PS D:\code\ai> docker ps     
CONTAINER ID   IMAGE                            COMMAND                  CREATED              STATUS              PORTS                                                NAMES
f9e079112657   hlebalbau/kafka-manager:latest   "/cmak/bin/cmak -Dpi…"   About a minute ago   Up About a minute   0.0.0.0:9000->9000/tcp                               docker_kafka_manager_1
bc25d2aa03c2   wurstmeister/kafka               "start-kafka.sh"         About a minute ago   Up About a minute   0.0.0.0:9092->9092/tcp                               kafka1
bec0c87815d4   wurstmeister/zookeeper           "/bin/sh -c '/usr/sb…"   About a minute ago   Up About a minute   22/tcp, 2888/tcp, 3888/tcp, 0.0.0.0:2181->2181/tcp   zookeeper
623b2fe87f71   kindest/node:v1.20.2             "/usr/local/bin/entr…"   2 months ago         Up 5 hours          127.0.0.1:36101->6443/tcp                            mycluster-control-plane
(venv) PS D:\code\ai> docker exec -it bc25d2aa03c2 bash
root@bc25d2aa03c2:~# kafka-topics.sh --bootstrap-server kafka1:9092 --create --topic test-events
Created topic test-events.
```

## Write messages to the topic
写入一些消息
```bash
root@bc25d2aa03c2:~# kafka-console-producer.sh --bootstrap-server kafka1:9092 --topic test-events
>hello
>world
>
```

## Read messages to the topic
同时打开一个客户端接受消息
````bash
root@bc25d2aa03c2:/# kafka-console-consumer.sh --bootstrap-server kafka1:9092 --topic test-events --from-beginning
hello
world
````
