#!/bin/bash

# EC2 인스턴스에 SSH 접속하여 Docker 컨테이너를 업데이트
# Traivs ci에서 환경변수 설정 필요
ssh -o StrictHostKeyChecking=no ec2-user@$EC2_INSTANCE_IP << 'EOF'
echo \$DOCKER_PASSWORD | docker login -u \$DOCKER_USERNAME --password-stdin
docker pull $DOCKER_USERNAME/pricetaglist
docker-compose down || true
docker-compose up -d
EOF