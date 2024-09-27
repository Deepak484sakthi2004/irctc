package web.deepak.irctc.controller.mutationController

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import web.deepak.irctc.entity.Station
import web.deepak.irctc.repository.StationRepo
import web.deepak.irctc.repository.TrainRepo

@Controller
class StationMController @Autowired
constructor(
    private val stationRepo: StationRepo,
    private val trainRepo : TrainRepo
)
{
    @MutationMapping
    fun addStation(@Argument name : String,
                   @Argument trainIds : List<String>) : Station
    {
        val trainLists = trainRepo.findAllById(trainIds)

        val curStation = Station(
            stationName = name,
            trains = trainLists
        )
        return stationRepo.save<Station>(curStation)
    }

    @MutationMapping
    fun updateStation(@Argument stationId:Int,
                               @Argument trainIds: List<String> ) : Station

    {
        val trainLists = trainRepo.findAllById(trainIds)

        val curStation :Station = stationRepo.findById(stationId).orElseThrow{
         RuntimeException("Station not found")
     }
        curStation.trains = trainLists
        return stationRepo.save(curStation)
    }

    @MutationMapping
    fun deleteStation(@Argument stationId: Int) : String {
        val stationInstance = stationRepo.findById(stationId)
        if(stationInstance!=null)
        {
            stationRepo.deleteById(stationId)
            return "The station has been removed!"
        }
        return "No such station with the id - $stationId"
    }

}