#!/bin/bash

echo "test success"
echo "$EC2_INSTANCE_IP"
echo "$DOCKER_USERNAME"
openssl aes-256-cbc -d -pbkdf2 -k "$ENCRYPTED_PASSWORD" -in scripts/pricetaglist.pem.enc -out scripts/deploy_key.pem
chmod 400 scripts/deploy_key.pem
#ssh -o StrictHostKeyChecking=no ec2-user@$EC2_INSTANCE_IP
echo "ssh 진입"