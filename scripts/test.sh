#!/bin/bash

echo "test success"
ssh -i pricetaglist.pem ec2-user@$EC2_INSTANCE_IP
echo "fin"