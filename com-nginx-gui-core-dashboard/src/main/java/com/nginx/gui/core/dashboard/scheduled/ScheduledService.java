package com.nginx.gui.core.dashboard.scheduled;

import com.alibaba.fastjson.JSONObject;
import com.nginx.gui.core.dashboard.socket.WebSocket;
import com.nginx.gui.core.util.scanning.DashboardUtil;
import com.nginx.gui.core.util.sigar.SigarConfig;
import lombok.extern.log4j.Log4j2;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author: hengbin_wu
 * @Date: 2019/1/8 10:05
 * @Description:
 */
@Component
@Log4j2
public class ScheduledService {

    @Resource
    private WebSocket webSocket;

    /**
     * 扫描cpu
     */
    @Scheduled(cron = "0/5 * *  * * ? ")
    public void scanningCpu(){
        SigarConfig.initSigar();
        try {
            JSONObject resultJson = DashboardUtil.cpu();
            webSocket.sendMessage(String.format("%s:%s" , "CPU" , resultJson.toJSONString()));
        }catch (SigarException e){
            log.error(e.getMessage());
        }
    }

    /**
     * 扫描内存
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void scanningMemory(){
        try {
            JSONObject jsonObject = DashboardUtil.memory();
            webSocket.sendMessage(String.format("%s:%s" , "memory" , jsonObject.toJSONString()));
        }catch (SigarException e){
            log.error(e.getMessage());
        }
    }
}
