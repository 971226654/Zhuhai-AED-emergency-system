package com.bnuz.aed.common.config;

import com.bnuz.aed.common.tools.TailLogThread;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Leia Liang
 */
@Data
@Component
@ServerEndpoint(value = "/webSocketLog")
public class LogWebSocketHandle {

    Logger logger = LoggerFactory.getLogger(LogWebSocketHandle.class);

    private Process process;

    private InputStream inputStream;

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        LogWebSocketHandle.applicationContext = applicationContext;
    }

    /**
     * 新的WebSocket请求开启
     */
    @OnOpen
    public void onOpen(@PathParam("id") String id, Session session) {
        String path = "";
        try {
            path = getPath();
            // 执行tail -f命令
            process = Runtime.getRuntime().exec("tail -f " + path);
            inputStream = process.getInputStream();
            // 一定要启动新的线程，防止InputStream阻塞处理WebSocket的线程
            TailLogThread thread = new TailLogThread(inputStream, session);
            thread.start();
        } catch (IOException e) {
            logger.error(String.format("read file [%s] error.%s", path, e));
        }
    }

    /**
     * WebSocket请求关闭
     */
    @OnClose
    public void onClose() {
        if(inputStream != null){
            try {
                inputStream.close();
            } catch (IOException e) {
                logger.error("close websocket error.:" + e);
            }
        }

        if(process != null){
            process.destroy();
        }
    }

    @OnError
    public void onError(Throwable thr) {
        logger.error("websocket error." + thr);
    }

    private String getPath() {
        return "/usr/local/aed/logs/aed.log";
    }

}
