#!/bin/bash

# EC2 인스턴스에 SSH 접속하여 Docker 컨테이너를 업데이트
# Traivs ci에서 환경변수 설정 필요

echo "Docker Hub 로그인"
echo \$DOCKER_PASSWORD | docker login -u \$DOCKER_USERNAME --password-stdin
echo "Pull Docker image"
docker pull \$DOCKER_USERNAME/pricetaglist:latest
echo "Docker containers 종료"
docker-compose down || true
echo "Docker containers 시작"
docker-compose up -d
