package web.deepak.irctc.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import web.deepak.irctc.entity.Train

@Repository
interface TrainRepo : JpaRepository<Train,String> {


//    fun findTrainsByStartStation(statId:Int) : List<Train>?
//
//    fun findTrainsByEndStation(endId:Int) : List<Train>?
    
    fun findByTrainName(trainName : String) : List<Train>?

}