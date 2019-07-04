#!/bin/bash

docker exec -it mysql mysql -psgeifsp \
 -e "CREATE DATABASE ms_contratos;" \
 -e "CREATE USER 'sgeifsp'@'%' IDENTIFIED BY 'sgeifsp';" \
 -e "CREATE USER 'sgeifsp'@'localhost' IDENTIFIED BY 'sgeifsp';" \
 -e "GRANT ALL PRIVILEGES ON ms_contratos.* to sgeifsp@localhost;" \
 -e "GRANT ALL PRIVILEGES ON ms_contratos.* to sgeifsp@'%';" \
 -e "FLUSH PRIVILEGES;"
