#!/bin/bash

POM="pom.xml"

# Busca as linhas contendo "br.com.fiap" no arquivo pom.xml
REFERENCIA=$(grep -A 2 -m 1 "<groupId>br.com.fiap</groupId>" "$POM")

# Extrai o valor de parquimetro da linha encontrada
ARTEFATO=$(echo "$REFERENCIA" | grep "<artifactId>" | sed -n 's/.*<artifactId>\(.*\)<\/artifactId>.*/\1/p')

# Extrai o valor de versao da linha abaixo da linha do artifactId
VERSAO=$(grep -A 1 -m 1 "<artifactId>$ARTEFATO</artifactId>" "$POM" | grep "<version>" | sed -n 's/.*<version>\(.*\)<\/version>.*/\1/p')

# Define as variáveis de ambiente
export PARQUIMETRO
export VERSAO

# Exibe os valores encontrados
echo "Parquimetro: $ARTEFATO"
echo "Versao: $VERSAO"

