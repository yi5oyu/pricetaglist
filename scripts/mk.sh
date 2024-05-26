  #!/bin/bash

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
      docker login

      docker pull yi5oyu/pricetaglist:latest

      docker run -d -p 8888:8888 yi5oyu/pricetaglist:latest

      docker ps
EOF

    else
      echo "Failed to add the key to ssh-agent."
      rm -f my-ec2-key.pem
    fi

    # .pem 파일 삭제
    rm -f my-ec2-key.pem

    echo "fin"