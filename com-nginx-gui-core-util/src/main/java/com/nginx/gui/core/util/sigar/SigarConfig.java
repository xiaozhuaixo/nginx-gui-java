package com.nginx.gui.core.util.sigar;


import lombok.extern.log4j.Log4j2;
import org.hyperic.jni.ArchNotSupportedException;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarLoader;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.*;


/**
 * @author: hengbin_wu
 * @Date: 2019/1/21 10:42
 * @Description:
 */
@Log4j2
@Configuration
public class SigarConfig {

    static {
        initSigar();
    }

    public static void initSigar(){
        SigarLoader sigarLoader = new SigarLoader(Sigar.class);
        String lib = null;
        try {
            lib = sigarLoader.getLibraryName();
            log.info("lib ======> {}" , lib);
        }catch(ArchNotSupportedException e){
            log.info("lib not found error:{}" , e.getMessage());
        }
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:/sigar/" + lib);
        try {
            if(resource.exists()){
                ClassPathResource classPathResource = new ClassPathResource("sigar/" + lib);
                String canonicalPath = classPathResource.getFile().getParentFile().getCanonicalPath();
                log.info("查找到的文件路径地址:{}" , canonicalPath);
                System.setProperty("org.hyperic.sigar.path" , canonicalPath);
                log.info("配置完毕");
            }
        }catch (IOException e){
            log.info("io not found error:{}" , e.getMessage());
        }
    }
}
