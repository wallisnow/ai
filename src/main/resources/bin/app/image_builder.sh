#!/usr/bin/env bash

IMAGE_NAME=${1:-demo_image}
TAG=${2:-latest}

CURRENT_IMAGE="$IMAGE_NAME":"$TAG"

#Build image if needed
if [[ "$(docker images -q "$CURRENT_IMAGE" 2> /dev/null)" == "" ]]; then
  echo "IMAGE $CURRENT_IMAGE NOT FOUND, BUILD A NEW IMAGE"
  docker build -t "$CURRENT_IMAGE" .
fi




