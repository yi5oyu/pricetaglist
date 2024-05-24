#!/bin/bash

echo "test success"
ssh -o StrictHostKeyChecking=no ec2-user@$EC2_INSTANCE_IP
echo "ssh 진입"