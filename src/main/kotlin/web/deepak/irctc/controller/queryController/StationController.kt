package web.deepak.irctc.controller.queryController

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import web.deepak.irctc.entity.Station
import web.deepak.irctc.service.StationService

@Controller
class StationController @Autowired
constructor(
    private val stationService: StationService,
)
{
    @QueryMapping
    fun getStations() : List<Station>
    {
        return stationService.getAllStation()
    }
}