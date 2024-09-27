package web.deepak.irctc.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import java.io.Serializable

@Entity
@Table(name ="station_table")
data class Station
    (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val stationId: Int?=null,

        var stationName : String,

        @ManyToMany(mappedBy = "stations", fetch = FetchType.LAZY)
        var trains: MutableList<Train> = mutableListOf(),
            ): Serializable


//    @ManyToMany (fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.ALL))
//    @JoinTable(
//        name = "station_train",
//        joinColumns = [JoinColumn(name = "station_id")],
//        inverseJoinColumns = [JoinColumn(name = "train_id")])
//    var trains : List<Train>  = listOf()