package web.deepak.irctc.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import web.deepak.irctc.entity.Station

@Repository
interface StationRepo : JpaRepository<Station,Int> {
    fun findStationByStationName(stationName : String) : List<Station>?
}