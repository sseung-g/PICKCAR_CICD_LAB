package com.pickcar.application;

import com.pickcar.dto.EventPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventMessageListener {

    private final EventInfoService eventInfoService;

    @RabbitListener(queues = "${mq.event.queue}")
    public void eventMessage(EventPayload eventPayload) {
        log.info("Received event: {}", eventPayload);
        try {
            if (eventPayload.getStatus()) {
                eventInfoService.on(eventPayload);
            } else {
                eventInfoService.off(eventPayload);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}