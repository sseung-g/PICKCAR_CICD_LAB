package com.pickcar.drivehistory.infrastructure;

import com.pickcar.drivehistory.domain.DriveHistory;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DriveHistoryRepository extends JpaRepository<DriveHistory, Long> {
    List<DriveHistory> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    List<DriveHistory> findAllByDrivingStartedAtBetween(LocalDateTime start, LocalDateTime end);

    @Query("""
            SELECT dh
            FROM DriveHistory dh
            JOIN Reservation r ON dh.reservationId = r.id
            JOIN User u ON r.userId = u.id
            WHERE u.info.name = :driverName
                        AND dh.drivingStartedAt between :startedAt AND :endedAt
            """)
    List<DriveHistory> findAllFilteredListByDriverNameAndDuration(String driverName, LocalDateTime startedAt, LocalDateTime endedAt);
}
