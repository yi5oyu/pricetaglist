  #!/bin/bash

  echo "test success"
  # ssh-agent를 시작하고 환경 변수를 설정합니다.
  eval $(ssh-agent -s)

  # 환경 변수에서 Base64로 인코딩된 키를 디코딩하여 .pem 파일 생성
  echo "$EC2_KEY_BASE64" | base64 --decode > my-ec2-key.pem
  chmod 400 my-ec2-key.pem

  # SSH 키를 ssh-agent에 추가합니다.
  ssh-add my-ec2-key.pem
  if [ $? -eq 0 ]; then
    echo "Key added to ssh-agent"

    # SSH를 통해 원격 서버에 접속합니다.
    ssh -t -o StrictHostKeyChecking=no ec2-user@$EC2_INSTANCE_IP <<EOF
    docker -v
    docker
    echo "Docker Hub 로그인"
    echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
    echo docker info | grep Username
    cd /home/project
    sudo git clone https://github.com/yi5oyu/pricetaglist.git
    cd pricetaglist

    echo "DB_URL=${DB_URL}" > .env
    echo "DB_USERNAME=${DB_USERNAME}" >> .env
    echo "DB_PASSWORD=${DB_PASSWORD}" >> .env

    docker-compose pull
    docker-compose up -d
EOF

  else
    echo "Failed to add the key to ssh-agent."
    rm -f my-ec2-key.pem
  fi

  # .pem 파일 삭제
  rm -f my-ec2-key.pem

  echo "fin"