  #!/bin/bash

   eval $(ssh-agent -s)

    echo "$EC2_KEY_BASE64" | base64 --decode > my-ec2-key.pem
    chmod 400 my-ec2-key.pem

    ssh-add my-ec2-key.pem

    if [ $? -eq 0 ]; then

      ssh -t -o StrictHostKeyChecking=no ec2-user@$EC2_INSTANCE_IP <<EOF
      cd ~/docker
      docker-compose down || true
      docker rmi -f yi5oyu/pricetaglist:latest
      docker rmi -f yi5oyu/nginx
      docker images -f "dangling=true" -q | xargs docker rmi -f
      docker-compose up -d --force-recreate
#      docker-compose ps
EOF
    fi

    rm -f my-ec2-key.pem