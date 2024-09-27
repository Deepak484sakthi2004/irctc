package web.deepak.irctc.controller.queryController

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import web.deepak.irctc.entity.Seat
import web.deepak.irctc.repository.SeatRepo

@Controller
class SeatController @Autowired
constructor(
    private val seatRepo: SeatRepo,
)

{
    @QueryMapping
    fun getSeatsByTrain(@Argument tid:String) : List<Seat>
    {
        return seatRepo.findSeatByTrainId(tid)
    }
}