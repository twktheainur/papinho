#!/bin/bash

cp -r ../build/classes/* . 
rm -r org/client
jar cmf server.mf papinhoServer.jar org META-INF
rm -r META-INF org
cp papinhoServer.jar ../dist