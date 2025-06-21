package com.pickcar.application;

import com.pickcar.dto.EventPayload;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventMessageListener {

    private final EventInfoService eventInfoService;

    @RabbitListener(queues = "${mq.event.queue}")
    public void cycleMessage(EventPayload eventPayload, @Header("traceId") String traceId) {
        MDC.put("traceId", traceId);
        try {
            if (eventPayload.getStatus()) {
                eventInfoService.on(eventPayload);
            } else {
                eventInfoService.off(eventPayload);
            }
        } finally {
            MDC.clear();
        }
    }
}