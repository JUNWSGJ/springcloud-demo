# 微服务架构的rpc， 超时，熔断，限流

本次demo中共有三个服务
* gateway-demo 网关服务，基于spring-cloud-gateway来实现
* service-a springboot应用
* service-b springboot应用

使用nacos做服务发现和注册中心，

我们通过以下两个场景来展示微服务架构下， 远程调用的相关细节

1. 网关调用后端api服务
   gateway-demo -> service-a

2. 两个服务之间的feign调用
   service-b -> service-a

使用spring-cloud-starter-openfeign框架来实现服务间的RPC调用。


gateway-demo -> service-b -> service-a


## api网关



