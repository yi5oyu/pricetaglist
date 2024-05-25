#!/bin/bash

echo "test success"

# Option 1: 기본 해독 시도 (AES-256-CBC)
openssl aes-256-cbc -d -k "$ENCRYPTED_PASSWORD" -in pricetaglist.pem.enc -out pricetaglist.pem
if [ $? -eq 0 ] && [ -f pricetaglist.pem ]; then
  chmod 400 pricetaglist.pem
  echo "Option 1: Decrypt success"
  ssh -i pricetaglist.pem -o StrictHostKeyChecking=no ec2-user@$EC2_INSTANCE_IP
else
  echo "Option 1: Failed to decrypt the file."
  rm -f pricetaglist.pem

  # Option 2: -md 옵션 사용하여 해독 시도 (AES-256-CBC with SHA256)
  openssl aes-256-cbc -d -md sha256 -k "$ENCRYPTED_PASSWORD" -in pricetaglist.pem.enc -out pricetaglist.pem
  if [ $? -eq 0 ] && [ -f pricetaglist.pem ]; then
    chmod 400 pricetaglist.pem
    echo "Option 2: Decrypt success"
    ssh -i pricetaglist.pem -o StrictHostKeyChecking=no ec2-user@$EC2_INSTANCE_IP
  else
    echo "Option 2: Failed to decrypt the file."
    rm -f pricetaglist.pem

    # Option 3: -salt 옵션 사용하여 해독 시도 (AES-256-CBC with salt)
    openssl aes-256-cbc -d -salt -k "$ENCRYPTED_PASSWORD" -in pricetaglist.pem.enc -out pricetaglist.pem
    if [ $? -eq 0 ] && [ -f pricetaglist.pem ]; then
      chmod 400 pricetaglist.pem
      echo "Option 3: Decrypt success"
      ssh -i pricetaglist.pem -o StrictHostKeyChecking=no ec2-user@$EC2_INSTANCE_IP
    else
      echo "Option 3: Failed to decrypt the file."
      rm -f pricetaglist.pem

      # Option 4: 다른 암호화 방식 시도 (AES-128-CBC)
      openssl aes-128-cbc -d -k "$ENCRYPTED_PASSWORD" -in pricetaglist.pem.enc -out pricetaglist.pem
      if [ $? -eq 0 ] && [ -f pricetaglist.pem ]; then
        chmod 400 pricetaglist.pem
        echo "Option 4: Decrypt success"
        ssh -i pricetaglist.pem -o StrictHostKeyChecking=no ec2-user@$EC2_INSTANCE_IP
      else
        echo "Option 4: Failed to decrypt the file."
        rm -f pricetaglist.pem

        # Option 5: 다른 암호화 방식 시도 (AES-256-CFB)
        openssl aes-256-cfb -d -k "$ENCRYPTED_PASSWORD" -in pricetaglist.pem.enc -out pricetaglist.pem
        if [ $? -eq 0 ] && [ -f pricetaglist.pem ]; then
          chmod 400 pricetaglist.pem
          echo "Option 5: Decrypt success"
          ssh -i pricetaglist.pem -o StrictHostKeyChecking=no ec2-user@$EC2_INSTANCE_IP
        else
          echo "Option 5: Failed to decrypt the file."
          rm -f pricetaglist.pem

          # Option 6: 다른 암호화 방식 시도 (AES-256-GCM)
          openssl aes-256-gcm -d -k "$ENCRYPTED_PASSWORD" -in pricetaglist.pem.enc -out pricetaglist.pem
          if [ $? -eq 0 ] && [ -f pricetaglist.pem ]; then
            chmod 400 pricetaglist.pem
            echo "Option 6: Decrypt success"
            ssh -i pricetaglist.pem -o StrictHostKeyChecking=no ec2-user@$EC2_INSTANCE_IP
          else
            echo "Option 6: Failed to decrypt the file."
            rm -f pricetaglist.pem
          fi
        fi
      fi
    fi
  fi
fi



#  ssh -i pricetaglist.pem -o StrictHostKeyChecking=no ec2-user@$EC2_INSTANCE_IP
echo "fin"