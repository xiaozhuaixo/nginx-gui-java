package com.nginx.gui.core.dashboard.scheduled;

import com.nginx.gui.core.util.scanning.DashboardUtil;
import com.nginx.gui.core.util.sigar.SigarConfig;
import lombok.extern.log4j.Log4j2;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author: hengbin_wu
 * @Date: 2019/1/8 10:05
 * @Description:
 */
@Component
@Log4j2
public class ScheduledService {

    /**
     * 扫描cpu
     */
    @Scheduled(cron = "0/5 * *  * * ? ")
    public void scanningCpu(){
        SigarConfig.initSigar();
        try {
            DashboardUtil.cpu();
        }catch (SigarException e){
            log.error(e.getMessage());
        }
    }

    /**
     * 扫描内存
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void scanningMemory(){
//        try {
//            DashboardUtil.memory();
//        }catch (SigarException e){
//            log.error(e.getMessage());
//        }
    }
}
