#!/usr/bin/env bash

docker rmi hello-demo

if [[ "$(docker images -q hello-demo:latest 2> /dev/null)" == "" ]]; then
  docker build -t hello-demo .
fi

docker run -it --rm hello-demo "$1"

