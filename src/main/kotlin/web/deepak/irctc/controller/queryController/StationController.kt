package web.deepak.irctc.controller.queryController

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import web.deepak.irctc.entity.Station
import web.deepak.irctc.repository.StationRepo

@Controller
class StationController @Autowired
constructor(
    private val stationRepo : StationRepo,
)
{
    @QueryMapping
    fun getStations() : List<Station>
    {
        return stationRepo.findAll()
    }
}