package com.pickcar.gpx.application;

import com.pickcar.gpx.dto.CyclePayload;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CycleMessageListener {

    private final SeeService seeService;

    @RabbitListener(queues = "${mq.cycle.queue}")
    public void cycleMessage(CyclePayload cyclePayload) {
        seeService.pushEvent(cyclePayload);
    }
}