package com.joe.test;

import com.joe.easysocket.server.backserver.mvc.impl.context.session.Session;
import com.joe.easysocket.server.backserver.mvc.impl.data.InterfaceData;
import com.joe.easysocket.server.backserver.mvc.impl.param.Context;
import com.joe.easysocket.server.backserver.mvc.impl.param.GeneralParam;
import com.joe.easysocket.server.backserver.mvc.impl.resource.annotation.Path;
import com.joe.easysocket.server.common.protocol.ChannelProxy;
import com.joe.easysocket.server.common.protocol.PChannel;
import com.joe.utils.concurrent.ThreadUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author joe
 */
@Path("user")
public class User {
    AtomicInteger count = new AtomicInteger(0);

    @Path("login")
    public void login(@GeneralParam("account") String account, @GeneralParam("password") String password, @Context
            Session session) {
        System.out.println("\n\n\n\n\n\n\n\n账号是：" + account + "，密码是：" + password + "\n\n\n\n\n\n\n\n\n\n\n\n\n");
        Map<String, String> map = new HashMap<>();
        map.put("account", account);
        map.put("password", password);
        session.setAttribute("user", map);
        ChannelProxy channel = session.getChannel();
        new Thread(() -> {
            ThreadUtil.sleep(5);
            channel.write("测试一下", "你好啊，这是一条主动发往客户端的消息");
        }).start();
    }

    @Path("print")
    public void print(@Context Session session) {
        int i = count.getAndAdd(1);
        if ((i < 90000) && i % 1000 == 0) {
            System.out.println("当前是：" + i + ";时间是：" + System.currentTimeMillis());
        } else if (i > 90000) {
            System.out.println("当前是：" + i + ";时间是：" + System.currentTimeMillis());
        }
//        System.out.println("session中用户是:" + session.getAttribute("user"));
    }
}
