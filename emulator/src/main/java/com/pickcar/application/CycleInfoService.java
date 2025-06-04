package com.pickcar.application;

import com.pickcar.domain.Cycle;
import com.pickcar.domain.EventInfo;
import com.pickcar.infrastructure.CycleRepository;
import com.pickcar.presentation.dto.request.CycleInfoRequest;
import jakarta.persistence.Table;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Table(name = "cycles")
@RequiredArgsConstructor
public class CycleInfoService {

    private final CycleRepository cycleRepository;

    public void cycle(CycleInfoRequest request) {
        Cycle cycle = Cycle.builder()
                .vehicleId(request.getCarId())
                .occurredAt(request.getOccurredAt())
                .cycleCnt(request.getCycleCnt())
                .cycleInfos(request.getCycleInfos())
                .build();
        cycleRepository.save(cycle);
    }

    public List<Cycle> getCycleInfosByOffEventInfo(EventInfo offEventInfo) {
        return cycleRepository.findByVehicleIdAndOccurredAtBetween(offEventInfo.getVehicleId(),
                offEventInfo.getEngineOnTime(), offEventInfo.getEngineOffTime());
    }
}
