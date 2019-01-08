package com.nginx.gui.core.dashboard.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author: hengbin_wu
 * @Date: 2019/1/8 10:05
 * @Description:
 */
@Component
public class ScheduledService {

    @Scheduled(cron = "0/10 * * * * ?")
    public void scanningCpu(){
        System.out.println("测试时间");
    }
}
