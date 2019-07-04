#!/bin/bash
export PROJECTS_PATH="$(dirname "$(pwd)")"

cd $PROJECTS_PATH/sge-docker-platform

printf "\n> Building image: MS-ANALYTICS \n"
mvn -f ../ms-analytics/pom.xml package dockerfile:build -DskipTests

printf "\n> Building image: MS-ATIVIDADES \n"
mvn -f ../ms-atividades/pom.xml package dockerfile:build -DskipTests

printf "\n> Building image: MS-CONTRATOS \n"
mvn -f ../ms-contratos/pom.xml package dockerfile:build -DskipTests

printf "\n> Building image: MS-RELATORIOS \n"
mvn -f ../ms-relatorios/pom.xml package dockerfile:build -DskipTests

printf "\n> Building image: MS-NOTIFICADOR \n"
mvn -f ../ms-notificador/pom.xml package dockerfile:build -DskipTests

printf "\n> Building image: MS-DOCUMENTOS \n"
mvn -f ../ms-documentos/pom.xml package dockerfile:build -DskipTests

printf "\n> Building image: MS-DOCENTES \n"
mvn -f ../ms-docentes/pom.xml package dockerfile:build -DskipTests


