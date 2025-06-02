package com.pickcar.global;

import com.pickcar.vehicle.domain.FuelType;
import com.pickcar.vehicle.domain.Vehicle;
import com.pickcar.vehicle.domain.VehicleInfo;
import com.pickcar.vehicle.domain.VehicleStatus;
import com.pickcar.vehicle.infrastructure.VehicleRepository;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitData implements CommandLineRunner {

    private final VehicleRepository vehicleRepository;

    @Override
    public void run(String... args) throws Exception {

        vehicleRepository.saveAll(
                IntStream.iterate(1, i -> i + 1)
                        .limit(10)
                        .mapToObj(i -> new Vehicle(
                                new VehicleInfo("model" + i, "color" + i, "가나다" + i,
                                        "200", "Brand", FuelType.DIESEL),
                                VehicleStatus.OPERABLE,
                                true,
                                true,
                                true
                        ))
                        .toList());
    }
}
