package web.deepak.irctc.entity

import web.deepak.irctc.enum.SeatStatus
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.io.Serializable
import java.time.LocalDateTime

@Entity
@Table(name = "rac_table")
data class RAC(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val racId: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne
    @JoinColumn(name = "train_id", nullable = false)
    val train: Train,

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    val seat: Seat,

    var status: SeatStatus = SeatStatus.PENDING,

    val bookedAt: LocalDateTime = LocalDateTime.now()
) : Serializable
