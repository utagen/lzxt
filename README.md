# lzxt
在线学堂（微服务项目，使用dubbo）


## 项目启动方式

### 启动流程总结

1. redis（我是直接用的windows版的，可以docker配个linux版的用）

2. nginx（16.1版本）

3. 前端 npm run serve（前置是nodejs14版本）

4. 虚拟机启动docker

   /bin/systemctl start docker.service

5. 在docker中启动mongodb和rocketmq（和依赖里配的版本一样就行）

6. 启动spring boot项目（admin，log，sso，ssodubbo，web一共5个服务）

7. 启动nacos（2.0.3，这个注意要是在windows下运行的话，环境变量的jdk版本一定要是64位的，这个很重要）
