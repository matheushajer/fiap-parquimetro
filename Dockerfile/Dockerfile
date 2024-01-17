FROM maven:3.8.4-openjdk-17
WORKDIR /app
WORKDIR /tmp

RUN git clone https://github.com/matheushajer/fiap-parquimetro.git

WORKDIR /tmp/fiap-parquimetro

RUN chmod +x Dockerfile/coleta-envs.sh
RUN bash Dockerfile/coleta-envs.sh
# Compila o jar
RUN mvn clean package

# Copia o jar para o diretorio /app
RUN cp target/*.jar /app

# Expoe a porta 8081
EXPOSE 8081
WORKDIR /app

# Define o comando para iniciar a API
CMD java -jar $(echo $ARTEFATO)-$(echo $VERSAO).jar

