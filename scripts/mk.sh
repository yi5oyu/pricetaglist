  #!/bin/bash

   eval $(ssh-agent -s)

    # 환경 변수에서 Base64로 인코딩된 키를 디코딩하여 .pem 파일 생성
    echo "$EC2_KEY_BASE64" | base64 --decode > my-ec2-key.pem
    chmod 400 my-ec2-key.pem

    # SSH 키를 추가
    ssh-add my-ec2-key.pem
    if [ $? -eq 0 ]; then

      # SSH 접속
      ssh -t -o StrictHostKeyChecking=no ec2-user@$EC2_INSTANCE_IP <<EOF
      cd ~/docker
      docker-compose down || true
      docker rmi -f yi5oyu/pricetaglist:latest
      docker rmi -f yi5oyu/nginx
      docker images -f "dangling=true" -q | xargs docker rmi -f
      docker-compose up -d --force-recreate
      echo "확인"
      docker-compose ps
EOF

    else
      rm -f my-ec2-key.pem
    fi

    # .pem 파일 삭제
    rm -f my-ec2-key.pem

    echo "fin"