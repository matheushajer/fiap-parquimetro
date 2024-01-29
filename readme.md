
# FIAP Parquimetro

Este projeto foi eleborado cumprindo os objetivos da FASE 02 Pós Arquitetura e Desenvolvimento Java da FIAP

## 🚀 Começando

Para utilizar o projeto em H2 deve-se alterar as configurações que estão comentadas no pom.xml e no src/main/resources/application.properties

Este projeto foi realizado com o intuito de ser implantado em cluster Openshift, yamls e Dockerfile disponíveis em deployments.

### 📋 Pré-requisitos

Necessário para implantação:

```
Cluster Openshift funcional, podendo ser o gratuito SandBox.
```
Openshift SandBox: https://developers.redhat.com/developer-sandbox

```
OC cli ou kubectl instalado 
```
OC cli: https://docs.openshift.com/container-platform/4.8/cli_reference/openshift_cli/getting-started-cli.html


Alternativa:
```
Editar o pom.xml e application.properties para utilizar H2
```

### 🔧 Instalação em cluster Openshift

Acessar a pasta yamls-citus-db-openshift

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

Acessar a pasta yamls

```
cd deployment/parquimetro-app/yamls
```
Aplicar o kustomization:

```
oc apply -k .
```
