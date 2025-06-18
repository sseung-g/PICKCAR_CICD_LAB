package com.pickcar.reservation.application;

import com.pickcar.auth.application.UserService;
import com.pickcar.auth.domain.User;
import com.pickcar.reservation.domain.Reservation;
import com.pickcar.reservation.domain.ReservationStatus;
import com.pickcar.reservation.exception.ReservationErrorCode;
import com.pickcar.reservation.exception.ReservationException;
import com.pickcar.reservation.infrastructure.ReservationRepository;
import com.pickcar.reservation.presentation.dto.context.ReservationContext;
import com.pickcar.reservation.presentation.dto.request.ReservationCreateRequest;
import com.pickcar.vehicle.application.VehicleService;
import com.pickcar.vehicle.domain.Vehicle;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    @Value(value = "${custom.reservation.coolDownMinutes}")
    private Long coolDownMinutes;

    private final UserService userService;
    private final VehicleService vehicleService;
    private final ReservationRepository reservationRepository;

    @Transactional
    public void create(ReservationCreateRequest request) {
        //TODO: 유효성 검사 필요
        Reservation reservation = Reservation.builder()
                .userId(request.userId())
                .vehicleId(request.vehicleId())
                .rentedAt(request.rentedAt())
                .returnedAt(null)                        //FIXME: 반납 시기를 정하기 VS 반납 했을때를 기록하기
                .status(ReservationStatus.RESERVED)     //FIXME: Default로 "예약" 상태로 생성?
                .build();

        reservationRepository.save(reservation);
    }

    public Reservation getById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] Reservation Not Found By Id " + id));
    }

    public Reservation getActiveReservationByVehicleId(Long vehicleId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime coolDownMinutesAgo = now.minusMinutes(coolDownMinutes);

        Optional<Reservation> reservation = reservationRepository.findByVehicleIdAndStatus(vehicleId,
                ReservationStatus.RESERVED);

        if (reservation.isPresent()) {
            return reservation.get();
        }

        log.warn("해당 ID를 가진 활성화된 예약을 찾지 못했습니다. 5분 기준으로 최근 예약을 탐색합니다.");

        return reservationRepository.findByVehicleIdAndUpdatedAtBetween(vehicleId, coolDownMinutesAgo, now).orElseThrow(
                () -> new ReservationException(ReservationErrorCode.NOT_FOUND_LATEST_UPDATED_RESERVATION));
    }

    public ReservationContext getReservationContextById(Long reservationId) {
        Reservation reservation = getById(reservationId);
        User user = userService.getById(reservation.getUserId());
        Vehicle vehicle = vehicleService.getById(reservation.getVehicleId());

        return new ReservationContext(reservation, user.getInfo(), vehicle.getInfo());
    }
}
