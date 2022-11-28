#!/bin/sh
# zouliming@netwisd.com
# 停掉file服务，并删除容器，还有与之对应的镜像；
# 如果只是停止容器，直接使用docker-compse命令
# 如果没有配置环境变量，需要写绝对路径的命令
. /etc/profile
. ~/.bash_profile
echo '-----rmi image-----'
docker rmi dockerhub.kubekey.local/zhongyuanjian/incloud-base-mdm
docker build -t dockerhub.kubekey.local/zhongyuanjian/incloud-base-mdm .
echo 'build complete!'