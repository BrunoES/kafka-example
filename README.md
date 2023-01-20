# kafka-example - Subindo projeto no Docker:

* Buildando projeto:
mvn clean install -DskipTests

* Construindo imagem
docker image build . --tag kafka-example

* Subindo docker-compose
docker-compose up