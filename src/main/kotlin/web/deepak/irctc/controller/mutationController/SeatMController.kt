package web.deepak.irctc.controller.mutationController

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import web.deepak.irctc.entity.Seat
import web.deepak.irctc.enum.SeatClass
import web.deepak.irctc.enum.SeatStatus
import web.deepak.irctc.repository.SeatRepo
import web.deepak.irctc.repository.TrainRepo

@Controller
class SeatMController @Autowired
constructor(
    private val seatRepo : SeatRepo,
    private val trainRepo: TrainRepo,
){
    @MutationMapping
    fun addSeat(
                @Argument trainId : String,
                @Argument seatClass : SeatClass) : Seat
    {
        println("in the seat add method !!!")
        val trainInstance = trainRepo.findById(trainId).orElseThrow{RuntimeException("no train with the id $trainId")}
        val curSeat = Seat(
                train = trainInstance,
                seatStatus = SeatStatus.FREE,
            seatClass = seatClass
        )

        return seatRepo.save<Seat>(curSeat)
    }

    @MutationMapping
    fun removeSeat(@Argument sid:Int, @Argument tid : String) : String
    {
        val seatInstance = seatRepo.findSeatByTrainId(tid)
        if(seatInstance!=null)
        {
            seatRepo.deleteSeatByTrainId(tid,sid)
            return "seat has been deleted successfully"
        }
        return "No such seat is associated with the train I assume!!"
    }

    @MutationMapping
    fun updateSeat(sid:Int,sClass: SeatClass) : Seat
    {
        val curSeat = seatRepo.findById(sid).orElseThrow{ RuntimeException("No such seat ") }
        curSeat.seatClass = sClass

        return seatRepo.save<Seat>(curSeat)
    }
}