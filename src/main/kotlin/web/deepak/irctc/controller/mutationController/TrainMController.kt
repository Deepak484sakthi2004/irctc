package web.deepak.irctc.controller.mutationController

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import web.deepak.irctc.entity.Train
import web.deepak.irctc.repository.StationRepo
import web.deepak.irctc.repository.TrainRepo
import web.deepak.irctc.service.TrainService

@Controller
class TrainMController @Autowired
constructor(
    private val trainRepository: TrainRepo,
    private val stationRepo : StationRepo
)
{

    @MutationMapping
    fun createTrain(@Argument tid: String,
                    @Argument tname: String,
                    @Argument startStationId: Int,
                    @Argument endStationId: Int,
                    @Argument stationIds: List<Int>): Train {
        val startStation = stationRepo.findById(startStationId).orElseThrow { Exception("Start Station not found") }
        val endStation = stationRepo.findById(endStationId).orElseThrow { Exception("End Station not found") }
        val stations = stationRepo.findAllById(stationIds)

        val train = Train(
            trainId = tid,
            trainName = tname,
            startStation = startStation,
            endStation = endStation,
            stations = stations
        )

        return trainRepository.save(train)
    }

}