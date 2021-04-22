package com.codetech.www.task;

import java.io.IOException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.codetech.www.task.EchoHandler;

@Controller
@ServerEndpoint("/admin")
public class Admin {
    private static Session admin = null;

    @OnOpen
    public void handleOpen(Session userSession) {
        if (admin != null) {
            try {
                admin.close();
            } catch (IOException e) {
            }
        }
        admin = userSession;
        for (String key : EchoHandler.getUserKeys()) {
            visit(key);
        }
    }

    @OnMessage
    public void handleMessage(String message, Session session) throws IOException {
        String[] split = message.split("#####", 2);
        String key = split[0];
        String msg = split[1];
        EchoHandler.sendMessage(key, msg);
    }

    @OnClose
    public void handleClose(Session session) {
        admin = null;
    }

    private static void send(String message) {
        if (admin != null) {
            try {
                admin.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void visit(String key) {
        send("{\"status\":\"visit\", \"key\":\"" + key + "\"}");
    }

    public static void sendMessage(String key, String message) {
        send("{\"status\":\"message\", \"key\":\"" + key + "\", \"message\":\"" + message + "\"}");
    }

    public static void bye(String key) {
        send("{\"status\":\"bye\", \"key\":\"" + key + "\"}");
    }
}
