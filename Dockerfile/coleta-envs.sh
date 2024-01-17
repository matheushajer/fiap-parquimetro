#!/bin/bash

POM="pom.xml"

# Busca as linhas contendo "br.com.fiap" no arquivo pom.xml
REFERENCIA=$(grep -A 2 -m 1 "<groupId>br.com.fiap</groupId>" "$POM")

# Extrai o valor de artefato da linha encontrada
ARTEFATO=$(echo "$REFERENCIA" | grep "<artifactId>" | sed -n 's/.*<artifactId>\(.*\)<\/artifactId>.*/\1/p')

# Extrai o valor de versao da linha encontrada
VERSAO=$(grep -m 1 "<version>" "$POM" | sed -n 's/.*<version>\(.*\)<\/version>.*/\1/p')

# Define as variáveis de ambiente
export ARTEFATO
export VERSAO

# Exibe os valores encontrados
echo "Artefato: $ARTEFATO"
echo "Versao: $VERSAO"

