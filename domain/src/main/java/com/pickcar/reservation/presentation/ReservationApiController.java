package com.pickcar.reservation.presentation;

import com.pickcar.reservation.application.ReservationService;
import com.pickcar.reservation.presentation.dto.response.SearchAbleVehiclesResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
public class ReservationApiController {

    private final ReservationService reservationService;

    //FIXME: 로직도 그렇고 사실 VEHICLE이 맞는듯
    @GetMapping("/vehicles")
    public List<SearchAbleVehiclesResponse> searchAbleVehicles() {
        return reservationService.getAbleVehicles();
    }

    @PostMapping
    public void reservation() {
//        reservationService.reservation();
    }
}
