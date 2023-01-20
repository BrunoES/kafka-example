# kafka-example - Subindo projeto no Docker:

* Buildando projeto:
mvn clean install -DskipTests

* Construindo imagem
docker image build . --tag kafka-example

* Subindo docker-compose
docker-compose up

* No log de subida, a fila KAFKA-EXAMPLE.QUEUE será alimentada e posteriormente consumida, exemplos:

Enviado com sucesso: topic:KAFKA-EXAMPLE.QUEUE - partition: 0
Mensagem ecee9631-023e-4ad4-90be-a6e8decf44d6 adicionada � fila.

Encontrei registros.
Consumindo mensagem KAFKA-EXAMPLE.QUEUE
6813bfa3-1fa9-4d03-9975-4b99a99c75c2
com.example.kafkaexample.messaging.serialize.dto.DataDTO@6f6745d6
0
3