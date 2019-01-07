package com.nginx.gui.core.dashboard.api;

import com.nginx.gui.core.dashboard.socket.WebSocket;
import com.nginx.gui.core.util.result.ResultModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: hengbin_wu
 * @Date: 2019/1/7 15:44
 * @Description:
 */
@RestController
@RequestMapping("/Test")
public class TestApi {

    @Resource
    private WebSocket webSocket;

    @RequestMapping(value = "/test" , method = RequestMethod.GET)
    public ResultModel getTest(){
        webSocket.sendMessage("测试内容 内容测试");
        return ResultModel.success("1111");
    }
}
