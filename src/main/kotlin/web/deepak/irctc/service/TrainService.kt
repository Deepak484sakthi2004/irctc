package web.deepak.irctc.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import web.deepak.irctc.entity.Seat
import web.deepak.irctc.entity.Station
import web.deepak.irctc.entity.Train
import web.deepak.irctc.enum.SeatStatus
import web.deepak.irctc.repository.SeatRepo
import web.deepak.irctc.repository.StationRepo
import web.deepak.irctc.repository.TrainRepo

@Service
class TrainService @Autowired
constructor(
    private val trainRepo: TrainRepo,
    private val stationRepo: StationRepo,
    private val seatRepo: SeatRepo
)
{

    fun createTrain(tId: String, tName : String,startStatId : Int , endStatId : Int) : Train
    {
        val startStationInstance = stationRepo.findById(startStatId).orElseThrow{RuntimeException("No station with Id")}
        val endStationInstance = stationRepo.findById(endStatId).orElseThrow{RuntimeException("No station with Id")}

        var trainInstance : Train = Train(
           trainId = tId,
            trainName = tName,
           startStation = startStationInstance,
           endStation = endStationInstance
        )

        return trainRepo.save<Train>(trainInstance)
    }

    fun getTrainById(id: String) : Train
    {
        return trainRepo.findById(id).orElseThrow({
            NoSuchElementException("No train with the ID $id")
        })
    }

    fun getAllTrains(): List<Train>
    {
        return trainRepo.findAll()
    }

    fun getAllTrainsByName(Username:String) : List<Train>?
    {
        return trainRepo.findByTrainName(Username)
    }

//    fun findTrainsByStartStation(statId:Int) : List<Train>?
//    {
//        return trainRepo.findTrainsByStartStation(statId)
//    }
//
//    fun findTrainsByEndStation(Id:Int) : List<Train>?
//    {
//        return trainRepo.findTrainsByEndStation(Id)
//    }


    fun updateTrain(trainId: String, trainName: String? ,startStation : Int? ,endStation :Int?): Train
    {
        val trainInstance: Train = trainRepo.findById(trainId).orElseThrow { RuntimeException("train not found") }

        if (trainName != null) {
            trainInstance.trainName = trainName
        }
        if (startStation != null) {
            val startStationInstance : Station = stationRepo.findById(startStation).orElseThrow{RuntimeException("No station with Id")}
            trainInstance.startStation = startStationInstance
        }

        if (endStation != null) {
            val endStationInstance : Station = stationRepo.findById(endStation).orElseThrow{RuntimeException("No station with Id")}
            trainInstance.endStation = endStationInstance
        }
        return trainRepo.save(trainInstance)
    }

    fun removeTrain(id:String)
    {
       trainRepo.deleteById(id)
    }

    fun findByTrain_TrainIdAndStatus(trainId: String, status: SeatStatus): List<Seat>
    {
        return seatRepo.findByTrain_TrainIdAndStatus(trainId,status)
    }
}