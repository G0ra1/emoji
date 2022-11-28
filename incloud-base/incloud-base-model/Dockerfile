FROM amazoncorretto:11
#作者
MAINTAINER zouliming@netwisd.com
#工作空间
WORKDIR /root/incloud
#时区同步
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
#对外映射的端口
EXPOSE 8012
ADD ./target/incloud-base-model.jar ./
#-Djava.security.egd=file:/dev/./urandom参数是优化tomcat启动速度的，我们工程中使用undertom，不需要这玩意儿
CMD java -Xms128m -Xmx1024m -jar incloud-base-model.jar
