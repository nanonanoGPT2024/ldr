#!/bin/bash
java -jar ldr_management.jar \
  -Dspring.datasource.url=jdbc:mysql://localhost:3306/ldr_v2 \
  -Dspring.datasource.username=root \
  -Dspring.datasource.password= \
  -Djwt.secret=mySecretKey12345678901234567890123456789012345678901234567890 \
  -Dserver.port=8080 \
  -Dfile.upload.dir=uploads/attachments/ 
