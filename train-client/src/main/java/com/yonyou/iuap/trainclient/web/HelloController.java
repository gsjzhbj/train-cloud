package com.yonyou.iuap.trainclient.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    Logger logger = LoggerFactory.getLogger("train-client");
    @RequestMapping("/hello")
    public String hello(@RequestParam String msg){
        logger.debug(msg);
        return "Hello,"+msg+"!";
    }
}
