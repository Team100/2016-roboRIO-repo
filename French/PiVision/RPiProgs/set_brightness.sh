#!/bin/bash
echo $1
sudo v4l2-ctl --set-ctrl=brightness=$1
