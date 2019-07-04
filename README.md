# SGE-IFSP
Sistema Gerenciador de Estágios do IFSP Hortolândia desenvolvido na arquitetura de microsserviços

# Tutorial para rodar o projeto SGE

 1 - Instalar o Docker : https://docs.docker.com/install/linux/docker-ce/ubuntu/
Obs.: Certifique-se de executar os passos de pós instalação: 

- https://docs.docker.com/install/linux/linux-postinstall/

Esses passos são necessários para que o Docker não precise ser executado como root - sem precisar do “sudo” na frente dos comandos.

 2 - Realizar o clone do repositório: https://github.com/LenilsonTeixeira/SGE-IFSP
 
 #Procedimentos a ser executatos no diretório: sge-docker-platform

 3 - Navegar via 'terminal' até o diretório: **sge-docker-platform** e criar as seguintes pastas: **mysql-data** , **mysql-data-docentes**, **postgres** (Obs: Não criar nenhum arquivo dentro das pastas, pois elas estão ligadas diretamente ao sistema de volume do Docker).
 
 4 - Navegar via 'terminal' até o diretório: **sge-docker-platform** e executar o comando: **sh docker-build.sh** (Esse script é responsável por compilar as imagens Docker)
 
 5- Ainda no diretório: **sge-docker-platform**, executar o seguinte comando após o passo 4: **sh docker-star.sh** (Esse comando é responsável por subir os containers Docker)
 
 - Executar o comando **docker ps** para se certificar se os containers estão rodando
 
 6 - Navegar até o diretório **sgefront** e executar o seguinte comando: **ng serve** (Comando responsável por compilar e subir o front-end)
 
 
 --------------------------------------------------------------------------------------------------------------------
  Importante: O microsserviço MS-DOCENTES cria automaticamente o banco porém não cria automaticamente a tabela no banco de dados POSTGRES. Caso deseje ativar esse microsserviço siga os seguintes procedimentos: 
 - copiar o script de criação de tabela do MS-DOCUMENTOS que está localizado na pasta de **scripts** e em seguida acessar a URL: http://localhost:15432/browser/ 
 Login: **user@domain.com**
 Senha: **admin**
 
 - Após criar a tabela executar o seguinte comando **docker restart ms-docentes**
 
 **O passo acima não interfere na execução do sistema, somente nas funcionalidades de CRUD do MS-Docentes**
 
 #Informações:
 - Dentro da pasta do projeto existe uma imagem que representa o mapeamento das portas TCP do projeto
 - A collection: **SGE-IFSP.postman_collection.json** foi desenvolvida para ser importada na ferramenta Postman para realizar chamadas nos serviços REST, caso deseje.
 - Para verificar as mensagens no kafka siga os seguintes passos:
  1 - Acessar o container do zookeeper através do comando: **docker exec -it zookeeper bash**
  2 - Navegar até o diretório: **/usr/bin**
  3 - Executar o comando **./kafka-console-consumer --bootstrap-server kafka:9092 --topic ms-contratos-data** (Tópico MS-CONTRATOS-DATA)
  
 ./kafka-console-consumer --bootstrap-server kafka:9092 --topic ms-contratos-metrics (Tópico de Métricas)
 
 ---------------------------------------------------------------------------------------------------------------------------
 As classes responsáveis por produzir a mensagem para o Apache Kafka no MS-Contratos está localizada no pacote: **com.ifsp.edu.hto.sge.contratos.event**
 
 No microsserviço ms-relatorios as classes responsáveis por consumir a mensagem estão localizadas no diretório: com.ifsp.edu.hto.sge.relatorios.service, especificamente no KafkaReceiver.java
 
 
  
 
 

 
 - 
 


