#!/bin/bash

docker exec -it mysql-docentes mysql -psgeifsp \
 -e "CREATE DATABASE ms_docentes;" \
 -e "CREATE USER 'sgeifsp'@'%' IDENTIFIED BY 'sgeifsp';" \
 -e "CREATE USER 'sgeifsp'@'localhost' IDENTIFIED BY 'sgeifsp';" \
 -e "GRANT ALL PRIVILEGES ON ms_docentes.* to sgeifsp@localhost;" \
 -e "GRANT ALL PRIVILEGES ON ms_docentes.* to sgeifsp@'%';" \
 -e "FLUSH PRIVILEGES;"
