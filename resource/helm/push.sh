docker tag minio/minio:latest dockerhub.kubekey.local/netwisd/minio:latest
docker tag nginx:latest dockerhub.kubekey.local/netwisd/nginx:latest
docker tag percona/percona-server:8.0.26 dockerhub.kubekey.local/netwisd/percona:8.0.26
docker tag redis:5.0.7 dockerhub.kubekey.local/netwisd/redis:5.0.7
docker tag apache/rocketmq:4.9.3 dockerhub.kubekey.local/netwisd/apache/rocketmq:4.9.3

docker push dockerhub.kubekey.local/netwisd/minio:latest
docker push dockerhub.kubekey.local/netwisd/nginx:latest
docker push dockerhub.kubekey.local/netwisd/percona:8.0.26
docker push dockerhub.kubekey.local/netwisd/redis:5.0.7
docker push dockerhub.kubekey.local/netwisd/apache/rocketmq:4.9.3