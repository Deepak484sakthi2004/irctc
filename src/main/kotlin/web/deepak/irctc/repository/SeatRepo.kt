package web.deepak.irctc.repository

import org.springframework.data.jpa.repository.JpaRepository
import web.deepak.irctc.entity.Seat
import web.deepak.irctc.enum.SeatStatus

interface SeatRepo : JpaRepository<Seat,Int>{

    fun findByTrain_TrainIdAndStatus(trainId: String, status: SeatStatus): List<Seat>


}