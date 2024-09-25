package web.deepak.irctc.controller.mutationController

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import web.deepak.irctc.entity.Train
import web.deepak.irctc.service.TrainService

@Controller
class TrainMController @Autowired
constructor(
    private val trainService: TrainService
)
{
    @MutationMapping
    fun addTrain(@Argument tid:String,
                 @Argument tname : String,
                 @Argument startStationId:Int,
                 @Argument endStationId : Int) : Train
    {
        return trainService.createTrain(tid,tname, startStationId,endStationId)
    }
}