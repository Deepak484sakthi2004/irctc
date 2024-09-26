package web.deepak.irctc.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import web.deepak.irctc.entity.Seat
import web.deepak.irctc.enum.SeatStatus


@Repository
interface SeatRepo : JpaRepository<Seat, Int> {

    @Query("SELECT s FROM Seat s WHERE s.train.trainId = :trainId AND s.seatStatus = :status")
    fun findSeatByTrainId(trainId: String, status: SeatStatus): List<Seat>
}
