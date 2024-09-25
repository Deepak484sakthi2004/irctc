package web.deepak.irctc.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

import jakarta.persistence.Table
import java.io.Serializable

@Entity
@Table(name="train_table")
data class Train
    (
        @Id
            val trainId : String,

        var trainName : String,

        @ManyToOne
            @JoinColumn(name="start_station_id", nullable = false)
            var startStation : Station,

        @ManyToOne
            @JoinColumn(name="end_station_id", nullable = false)
            var endStation : Station,

        @OneToMany(mappedBy = "train", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        val seats: List<Seat> = mutableListOf(),

        @ManyToMany(fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.ALL))
        @JoinTable(
            name = "station_train",
            joinColumns = [JoinColumn(name = "train_id")],
            inverseJoinColumns = [JoinColumn(name = "station_id")])
        var stations : List<Station>  = listOf()
        ) : Serializable
