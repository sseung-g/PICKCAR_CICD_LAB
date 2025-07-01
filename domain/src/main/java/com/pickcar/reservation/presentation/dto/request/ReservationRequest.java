package com.pickcar.reservation.presentation.dto.request;

//FIXME: "예약을 하다" 에 걸맞는 네이밍 필요
public record ReservationRequest(
        Long userId,
        Long vehicleId
) {
}
