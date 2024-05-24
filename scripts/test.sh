#!/bin/bash

echo "test success"

# Option 1: 기본 해독 시도
openssl aes-256-cbc -d -k "$ENCRYPTED_PASSWORD" -in scripts/pricetaglist.pem.enc -out scripts/deploy_key.pem
if [ -f scripts/deploy_key.pem ]; then
  chmod 400 scripts/deploy_key.pem
  echo "Option 1: Decrypt success"
else
  echo "Option 1: Failed to decrypt the file."
  # Option 2: -pbkdf2 옵션 추가 시도
  openssl aes-256-cbc -pbkdf2 -d -k "$ENCRYPTED_PASSWORD" -in scripts/pricetaglist.pem.enc -out scripts/deploy_key.pem
  if [ -f scripts/deploy_key.pem ]; then
    chmod 400 scripts/deploy_key.pem
    echo "Option 2: Decrypt success"
  else
    echo "Option 2: Failed to decrypt the file."
  fi
fi
#ssh -o StrictHostKeyChecking=no ec2-user@$EC2_INSTANCE_IP
echo "fin"