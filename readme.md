
# 🅿️🚘🎫 FIAP Parquimetro

Este projeto foi eleborado cumprindo os objetivos da FASE 02 Pós Arquitetura e Desenvolvimento Java da FIAP

Que consiste em um conjunto de APIs para o gerenciamento de um sistema de parquímetro escalável. 
Adotamos o uso da extensão Citus do Postgresql por ser uma versão escalável deste banco de dados. 
```
https://www.citusdata.com/
```
Para tornar as APIs escaláveis utilizamos o deploy utilizando HPA em OpenShift que é um Kubernetes modificado pela RedHat.
```
https://www.redhat.com/pt-br/technologies/cloud-computing/openshift
```
```
https://docs.openshift.com/container-platform/4.8/nodes/pods/nodes-pods-autoscaling.html
```

Visando facilitar os testes criamos também um docker compose para a utilização do projeto de forma local.
## 🚀 Começando

### Abaixo traremos formas de implantar utilizando cluster Openshift e Docker Compose

OBS: Os arquivos referentes deploy se encontram em: https://github.com/matheushajer/fiap-parquimetro/tree/master/deployment

## 1️⃣ Alternativa 1

### 📋 Pré-requisitos

```
Cluster Openshift funcional, podendo ser o gratuito SandBox.
```
Openshift SandBox: https://developers.redhat.com/developer-sandbox

```
OC cli ou kubectl instalado 
```
OC cli: https://docs.openshift.com/container-platform/4.8/cli_reference/openshift_cli/getting-started-cli.html

### 🔧 Instalação em cluster Openshift

Acessar a pasta yamls-citus-db-openshift (https://github.com/matheushajer/fiap-parquimetro/tree/master/deployment/yamls-citus-db-openshift)

```
cd deployment/yamls-citus-db-openshift
```
Aplicar o secret:

```
oc apply -f citus-secrets.yaml
```
Aplicar o kustomization:

```
oc apply -k .
```

Acessar a pasta yamls para fazer o deploy do parquimetro-app

```
cd deployment/parquimetro-app/yamls
```
Aplicar o kustomization:

```
oc apply -k .
```
Após as etapas acima você verá o ambiente pronto para uso e com o HPA ativado conforme abaixo:

![image](https://github.com/matheushajer/fiap-parquimetro/assets/102033685/ff11a4be-64ca-4923-89bb-f87ae611cc03)

Conforme informado anteriormente, através do HPA podemos ter a aplicação parquimetro-app escalável que neste caso ocorre quando ela atinge o pico
de CPU ou RAM acima de 80% e 70% respectivamente, teremos a criação de mais uma replica e por assim subsequente até o máximo até então estipulado como 3 réplicas.

![Imagem do WhatsApp de 2024-01-22 à(s) 20 52 12_f6cdaef8](https://github.com/matheushajer/fiap-parquimetro/assets/102033685/0acd24ec-a2db-488e-b448-5f2701e2aa17)

![Imagem do WhatsApp de 2024-01-22 à(s) 20 54 29_9e81d4fc](https://github.com/matheushajer/fiap-parquimetro/assets/102033685/532f978c-e86c-4e48-a034-091610c5d311)

Após a aplicação se estabilizar abaixo do alvo, ela voltará a apenas 1 réplica conforme os indicadores dos eventos abaixo:

![Imagem do WhatsApp de 2024-01-22 à(s) 21 04 30_7d8f0c28](https://github.com/matheushajer/fiap-parquimetro/assets/102033685/c4ccd4db-6330-4b65-bde5-8e2ae4b1fccf)

## 2️⃣ Alternativa 2

### 📋 Pré-requisitos


```
Docker instalado
```
Documentação: https://docs.docker.com/

```
Docker compose instalado
```
Documentação docker compose: https://docs.docker.com/compose/

### 🔧 Instalação em docker compose:

Acessar a pasta docker-compose (https://github.com/matheushajer/fiap-parquimetro/tree/master/deployment/docker-compose)

```
cd deployment/docker-compose
```
Rodar o docker compose:

```
docker compose up
```

![image](https://github.com/matheushajer/fiap-parquimetro/assets/102033685/af73b748-dffb-4b0a-b832-0a919838a53c)

Containers em execução:

![image](https://github.com/matheushajer/fiap-parquimetro/assets/102033685/8947322a-f214-45fd-98fe-eeaa80c60850)

**OBS**: Para utilizar o projeto em H2 deve-se alterar as configurações que estão comentadas no pom.xml e no src/main/resources/application.properties

## ⚙️ Realização de testes

### 🔩 Toda a documentação de como utilizar as APIs se encontram na collection anexada ao PDF da entrega

Vide exemplo abaixo:
![image](https://github.com/matheushajer/fiap-parquimetro/assets/102033685/ce8c34c4-a088-4e6e-987f-e9ae755a5192)

## 🛠️ Construído com

- [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/) - Framework principal
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/) - Gerenciamento de dados
- [Spring Boot Starter Validation](https://docs.spring.io/spring-boot/docs/current/reference/html/) - Validação
- [Spring Boot Starter Web](https://docs.spring.io/spring-boot/docs/current/reference/html/web.html) - Facilitação de Web apps
- [PostgreSQL JDBC Driver](https://jdbc.postgresql.org/documentation/head/) - Conexão com PostgreSQL
- [Project Lombok](https://projectlombok.org/features/all) - Utilizado annotations para reduzir verbosidade.
- [MapStruct](https://mapstruct.org/documentation/stable/reference/html/) - Mapeamento de objetos
- [Spring Data REST WebMVC](https://docs.spring.io/spring-data/rest/docs/current/reference/html/) - Exposição RESTful
- [Unirest for Java](http://kong.github.io/unirest-java/) - Cliente HTTP usado no envio de SMS
- [SendGrid Java Library](https://sendgrid.com/docs/for-developers/sending-email/v3-java-code-example/) - Integração de email
- [Maven Compiler Plugin](https://maven.apache.org/plugins/maven-compiler-plugin/) - Compilação Java
- [Spring Boot Maven Plugin](https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/html/) - Plugin Maven para Spring Boot


## 😥 Desafios encontrados:

### ⚠️ Como tornar a aplicação escalável?
✅ Pensamos em utilizar o OpenShift por ser uma aplicação de orquestração de containers
baseada em Kubernetes. 

Ele é altamente utilizado no mercado e devido a sua facilidade de escalabilidade
utilizando o HPA.

Para o banco de dados o desafio foi encontrar uma que fosse escalável e o Citus e bastante utilizado
no mercado devido a sua fácil escalabilidade e grande desempenho para grandes volumes de dados.


### ⚠️ Dificuldades no deploy do Citus no OpenShift
✅ Foi necessário adaptar o deploy de acordo com a plataforma disponibilizada pela RedHat,

Neste caso criamos "InitContainers" que rodam antes dos containers principais entrarem em execuçao.
Com isso, os utilizamos para garantir que após a reinicialização, o pod tenham todas as permissões necessárias. 

Exemplo em: deployment/yamls-citus-db-openshift/citus-statefullsets.yaml initContainer fix-permissions-pgdata.


### ⚠️ Como viabilizar as notificações via SMS? 
✅ Para as notificações via SMS é utilizado o Unirest-JAVA como cliente HTTP para o envio de SMS devido a sua simplicidade
no uso e abstração de requisições via API.

### ⚠️ Como viabilizar as notificações via e-mail?
✅ O SendGrid foi escolhido devido à sua confiabilidade, fácil escalabilidade e capacidade de lidar com grandes volumes. Monitoramento detalhado, APIs fáceis de integrar e segurança robusta.

## ✒️ Autores


* **Cleyton Sales**
* **Déborah Souza**
* **Karoline Leite**
* **Matheus Hajer**
* **Yuri Sena**

