package web.deepak.irctc.controller.queryController

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import web.deepak.irctc.entity.Train
import web.deepak.irctc.repository.TrainRepo

@Controller
class TrainController @Autowired
constructor(
    private val trainRepo : TrainRepo
)
{
    @QueryMapping
    fun getTrains() : List<Train> {
        return trainRepo.findAll()
    }

}