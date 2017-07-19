#!/bin/bash

echo "启动web服务"
cd ../
mvn clean install
cd plat-web
mvn clean jetty:run