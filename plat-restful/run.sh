#!/bin/bash

echo "启动restful服务"
cd ../
mvn clean install
cd plat-restful
mvn clean jetty:run