package web.deepak.irctc.controller.mutationController

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Service
import web.deepak.irctc.entity.Station
import web.deepak.irctc.service.StationService

@Controller
class StationMController @Autowired
constructor(
    private val stationService: StationService,
)
{
    @MutationMapping
    fun addStation(@Argument name : String) : Station
    {
        return stationService.createStation(name)
    }

    @MutationMapping
    fun     updateStation(@Argument stationId:Int,
                          @Argument stationName : String) : Station

    {
        return stationService.updateStation(stationId,stationName)
    }

    @MutationMapping
    fun deleteStation(@Argument stationId: Int) : String {
        val stationInstance = stationService.getStationById(stationId)
        if(stationInstance!=null)
        {
            stationService.removeStation(stationId)
            return "The station has been removed!"
        }
        return "No such station with the id - $stationId"
    }

}