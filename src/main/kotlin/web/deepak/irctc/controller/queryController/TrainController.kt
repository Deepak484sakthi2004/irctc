package web.deepak.irctc.controller.queryController

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Service
import web.deepak.irctc.entity.Train
import web.deepak.irctc.service.TrainService

@Controller
class TrainController @Autowired
constructor(
    private val trainService: TrainService
)
{
    @QueryMapping
    fun getTrains() : List<Train> {
        return trainService.getAllTrains()
    }

}