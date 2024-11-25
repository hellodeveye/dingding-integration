package com.xiaocm.integration.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.dingtalk.open.app.api.OpenDingTalkStreamClientBuilder;
import com.dingtalk.open.app.api.security.AuthClientCredential;
import com.dingtalk.open.app.stream.protocol.event.EventAckStatus;
import com.xiaocm.integration.config.DingConfigProperties;


import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DingStreamListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private DingConfigProperties dingConfigProperties;

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent readyEvent) {
        try {
            // 在这里添加SpringBoot容器启动后需要执行的逻辑
            OpenDingTalkStreamClientBuilder
                    .custom()
                    .credential(new AuthClientCredential(dingConfigProperties.getClientId(), dingConfigProperties.getClientSecret()))
                    //注册事件监听
                    .registerAllEventListener(e -> {
                        try {
                            log.info("eventId: {}, eventType: {}, bornTime: {}", e.getEventId(), e.getEventType(), e.getEventBornTime());
                            log.info("bizData: {}", e.getData());
                            //消费成功
                            return EventAckStatus.SUCCESS;
                        } catch (Exception exception) {
                            log.error("DingStreamListener error: {}", exception.getMessage());
                            //消费失败
                            return EventAckStatus.LATER;
                        }
                    })
                    .build().start();
            log.info("DingStreamListener started");

        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }
}
