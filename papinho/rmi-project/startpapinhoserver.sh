#/bin/bash

java  -Djava.rmi.server.codebase=file://./build/classes/ -Djava.security.main -Djava.security.policy=./server.policy -cp ./dist/papinho.jar org.server.MainServer
