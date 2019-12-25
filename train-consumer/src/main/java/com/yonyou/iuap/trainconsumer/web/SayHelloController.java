package com.yonyou.iuap.trainconsumer.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SayHelloController {

    @Autowired
    LoadBalancerClient loadBalancerClient;
    @Autowired
    RestTemplate restTemplate;

    Logger logger = LoggerFactory.getLogger("train-consumer");
    @RequestMapping("/say")
    public String sayHello(@RequestParam String msg){
        String result=restTemplate.getForObject(getUrl("train-client","/hello?msg="+msg),String.class);
        logger.debug(result);
        return result;
    }
    /**
     * 获取指定url
     * @param clientApplicationName 指定的服务提供名
     * @param interfaceName 需要消费的接口名
     * @return
     */
    private String getUrl(String clientApplicationName, String interfaceName) {
        // 使用loadBalancerClient的choose函数来负载均衡的选出一个eurekaClient的服务实例
        ServiceInstance serviceInstance = loadBalancerClient.choose(clientApplicationName);
        // 获取之前eurekaClient /all接口地址
        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + interfaceName;
        logger.debug(url);
        return url;
    }
}
