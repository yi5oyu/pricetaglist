#!/bin/bash

echo "test success"
  ssh -i scripts/pricetaglist.pem -o StrictHostKeyChecking=no ec2-user@$EC2_INSTANCE_IP
echo "fin"