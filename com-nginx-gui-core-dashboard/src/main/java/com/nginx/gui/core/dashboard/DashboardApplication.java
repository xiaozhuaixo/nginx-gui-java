package com.nginx.gui.core.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

/**
 * @author: hengbin_wu
 * @Date: 2019/1/7 11:54
 * @Description:
 */

@SpringBootApplication
@EnableWebSocket
@EnableScheduling
public class DashboardApplication {
    public static void main(String[] args){
        SpringApplication.run(DashboardApplication.class , args);
    }
}
