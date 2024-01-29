FROM maven:3.8.4-openjdk-17
WORKDIR /app
WORKDIR /tmp

RUN git clone -b teste-db https://github.com/matheushajer/fiap-parquimetro.git

WORKDIR /tmp/fiap-parquimetro

RUN mvn clean package -DskipTests

# Copia o jar para o diretorio /app
RUN cp target/*.jar /app

# Expoe a porta 8081
EXPOSE 8081
WORKDIR /app

# Define o comando para iniciar a API
CMD java -jar $(ls | grep .jar)

