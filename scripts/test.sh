#!/bin/bash

echo "test success"
  - docker build -t yi5oyu/pricetaglist .
  - echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
  - docker push yi5oyu/pricetaglist
echo "fin"