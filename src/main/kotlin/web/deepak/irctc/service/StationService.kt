package web.deepak.irctc.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import web.deepak.irctc.entity.Station
import web.deepak.irctc.repository.StationRepo

@Service
class StationService @Autowired
constructor(
    private val stationRepo: StationRepo,
)
{

    fun createStation(stationName: String): Station{
        var stationInstance: Station = Station(stationName = stationName)
        println("Inside")
        return stationRepo.save(stationInstance)
    }

    fun getStationById(id: Int): Station? {
        return stationRepo.findByIdOrNull(id)
    }

    fun getAllStation(): List<Station> {
        return stationRepo.findAll()
    }



    fun updateStation(stationId: Int, stationName: String): Station {
        var stationInstance: Station = stationRepo.findById(stationId).orElseThrow { RuntimeException("User not found") }

        stationInstance.stationName = stationName

        return stationRepo.save(stationInstance)
    }

    fun removeStation(id: Int) {
        stationRepo.deleteById(id)
        }
}

