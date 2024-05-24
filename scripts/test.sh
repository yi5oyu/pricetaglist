#!/bin/bash

echo "test success"

# Option 1: 기본 해독 시도
openssl aes-256-cbc -d -k "$ENCRYPTED_PASSWORD" -in scripts/pricetaglist.pem.enc -out scripts/deploy_key.pem
if [ $? -eq 0 ] && [ -f scripts/deploy_key.pem ]; then
  chmod 400 scripts/deploy_key.pem
  echo "Option 1: Decrypt success"
  ssh -o StrictHostKeyChecking=no ec2-user@$EC2_INSTANCE_IP
else
  echo "Option 1: Failed to decrypt the file."
  rm -f scripts/deploy_key.pem

  # Option 2: -md 옵션 사용하여 해독 시도
  openssl aes-256-cbc -d -md sha256 -k "$ENCRYPTED_PASSWORD" -in scripts/pricetaglist.pem.enc -out scripts/deploy_key.pem
  if [ $? -eq 0 ] && [ -f scripts/deploy_key.pem ]; then
    chmod 400 scripts/deploy_key.pem
    echo "Option 2: Decrypt success"
    ssh -o StrictHostKeyChecking=no ec2-user@$EC2_INSTANCE_IP
  else
    echo "Option 2: Failed to decrypt the file."
    rm -f scripts/deploy_key.pem

    # Option 3: -salt 옵션 사용하여 해독 시도
    openssl aes-256-cbc -d -salt -k "$ENCRYPTED_PASSWORD" -in scripts/pricetaglist.pem.enc -out scripts/deploy_key.pem
    if [ $? -eq 0 ] && [ -f scripts/deploy_key.pem ]; then
      chmod 400 scripts/deploy_key.pem
      echo "Option 3: Decrypt success"
      ssh -o StrictHostKeyChecking=no ec2-user@$EC2_INSTANCE_IP
    else
      echo "Option 3: Failed to decrypt the file."
      rm -f scripts/deploy_key.pem
    fi
  fi
fi

#ssh -o StrictHostKeyChecking=no ec2-user@$EC2_INSTANCE_IP
echo "fin"