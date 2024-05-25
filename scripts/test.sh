#!/bin/bash

echo "test success"

# ssh-agent를 시작하고 환경 변수를 설정합니다.
eval $(ssh-agent -s)

# Option 5: AES-256-CFB 방식으로 복호화
openssl aes-256-cfb -d -k "$ENCRYPTED_PASSWORD" -in pricetaglist.pem.enc -out pricetaglist.pem
if [ $? -eq 0 ] && [ -f pricetaglist.pem ]; then
  chmod 400 pricetaglist.pem

  # SSH 키를 ssh-agent에 추가합니다.
  ssh-add pricetaglist.pem
  if [ $? -eq 0 ]; then
    echo "Option 5: Decrypt success"

    # SSH를 통해 원격 서버에 접속합니다.
    ssh -o StrictHostKeyChecking=no ec2-user@$EC2_INSTANCE_IP "echo 'Hello, EC2!'"
  else
    echo "Option 5: Failed to add the key to ssh-agent."
    rm -f pricetaglist.pem
  fi
else
  echo "Option 5: Failed to decrypt the file."
  rm -f pricetaglist.pem
fi

echo "fin"