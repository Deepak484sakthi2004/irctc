package web.deepak.irctc.entity

import jakarta.persistence.Column
import web.deepak.irctc.enum.SeatClass
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import web.deepak.irctc.enum.SeatStatus
import java.io.Serializable

@Entity
@Table(name = "seat_table")
data class Seat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id", unique = true, nullable = false)
    val seatId: Int,

    @ManyToOne
    @JoinColumn(name = "train", nullable = false)
    var train: Train,

    var seatStatus: SeatStatus = SeatStatus.FREE,

    @Column(name = "seat_class", nullable = false)
    val seatClass: SeatClass,

    ) : Serializable

