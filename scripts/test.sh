#!/bin/bash

echo "test success"

# -pbkdf2 옵션을 사용하여 암호 해독 시도
openssl aes-256-cbc -pbkdf2 -d -k "$ENCRYPTED_PASSWORD" -in scripts/pricetaglist.pem.enc -out scripts/deploy_key.pem

# 해독이 성공하면 파일 권한 설정
if [ -f scripts/deploy_key.pem ]; then
  chmod 400 scripts/deploy_key.pem
  echo "Decrypt success"
  # SSH 접속 (실제로 사용하려면 주석 해제)
   ssh -o StrictHostKeyChecking=no ec2-user@$EC2_INSTANCE_IP
   echo "ssh 진입"
else
  echo "Failed to decrypt the file."
fi

#ssh -o StrictHostKeyChecking=no ec2-user@$EC2_INSTANCE_IP
echo "fin"