#!/bin/bash

echo "test success"
  ssh -i scripts/pricetaglist.pem ec2-user@$EC2_INSTANCE_IP
echo "fin"