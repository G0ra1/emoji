FROM amazoncorretto:11
#作者
MAINTAINER zouliming@netwisd.com
#工作空间
WORKDIR /root/incloud
#时区同步
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

#对外映射的端口
EXPOSE 8037
ADD ./target/incloud-biz-asset.jar ./
CMD java -Xms256m -Xmx1024m -jar incloud-biz-asset.jar
