#!/usr/bin/env bash

IMAGE=anibali/pytorch:1.8.1-cuda11.1-ubuntu20.04
CONTAINER_NAME=pytorch-$RANDOM
SCRIPT_PATH=D:\\code\\ai\\src\\main\\resources\\bin\\app

#use "-t" if you don't want image exit
docker run -t -d -P \
  --name "$CONTAINER_NAME" \
  -v $SCRIPT_PATH:/app \
  $IMAGE \
  bash -c "pip install -r ./requirements.txt; python ./main.py"


