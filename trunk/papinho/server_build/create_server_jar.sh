#!/bin/bash

cp -r ../build/classes/* . 
cp org/client/PapinhoClient.class .
rm -rf org/client
mkdir org/client
mv PapinhoClient.class org/client
jar cmf server.mf papinhoServer.jar org META-INF
rm -r META-INF org
cp papinhoServer.jar ../dist