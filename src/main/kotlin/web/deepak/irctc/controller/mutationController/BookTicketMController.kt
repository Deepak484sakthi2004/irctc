package web.deepak.irctc.controller.mutationController

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

import web.deepak.irctc.entity.TicketRegistration
import web.deepak.irctc.entity.WaitingList
import web.deepak.irctc.enum.SeatStatus
import web.deepak.irctc.repository.RACrepo
import web.deepak.irctc.repository.SeatRepo
import web.deepak.irctc.repository.StationRepo
import web.deepak.irctc.repository.TicketRepo
import web.deepak.irctc.repository.TrainRepo
import web.deepak.irctc.repository.UserRepo
import web.deepak.irctc.repository.WaitingListRepo
import java.time.LocalDateTime

@Controller
class BookTicketMController @Autowired
constructor(
    private val trainRepo : TrainRepo,
    private val userRepo: UserRepo,
    private val stationRepo: StationRepo,

    private val seatRepo: SeatRepo,
    private val ticketRepo : TicketRepo,
    private val racRepo : RACrepo,
    private val waitingListRepo : WaitingListRepo

) {
    @MutationMapping
    fun bookTicket(@Argument uid: Long,
                   @Argument tid: String,
                   @Argument sStatid: Int,
                   @Argument endStatid: Int): String {
        val trainInstance = trainRepo.findById(tid).orElseThrow {
            NoSuchElementException("No Train with the Id $tid")
        }
        val userInstance = userRepo.findById(uid).orElseThrow {
            NoSuchElementException("No User with the Id $uid")
        }
        val startStationInstance = stationRepo.findById(sStatid).orElseThrow {
            NoSuchElementException("No Station with the Id $sStatid")
        }
        val endStationInstance = stationRepo.findById(endStatid).orElseThrow {
            NoSuchElementException("No Station with the Id $endStatid")
        }

        val availSeats = seatRepo.findSeatByTrainId(tid, SeatStatus.FREE)

        if (availSeats.isNotEmpty()) {
            val curSeat = availSeats.first()

            val userTicket = TicketRegistration(
                seat = curSeat,
                user = userInstance,
                train = trainInstance,
                startStation = startStationInstance,
                destStation = endStationInstance,
                bookedAt = LocalDateTime.now()
            )
            ticketRepo.save(userTicket)

            return "Ticket booked with the id ${userTicket.ticketId} at ${userTicket.bookedAt}"
        } else {
            val racList = trainRepo.findRacListByTrainId(tid)
            if (racList.isNotEmpty()) {
                val curRAC = racList.first()
                curRAC.status = SeatStatus.PENDING
                racRepo.save(curRAC)
                return "RAC booking successful"
            } else {
                val waitingList = WaitingList(
                    user = userInstance,
                    train = trainInstance,
                    status = SeatStatus.WAITING,
                    bookedAt = LocalDateTime.now()
                )
                waitingListRepo.save(waitingList)

                return "No seats available. You have been added to the waiting list."
            }
        }
    }

        @MutationMapping
        fun cancelTicketBooking(@Argument ticketId: Long): String {
            val ticketInstance = ticketRepo.findById(ticketId).orElseThrow {
                NoSuchElementException("No ticket with the ID $ticketId")
            }
            ticketInstance.status = SeatStatus.FREE
            ticketRepo.deleteById(ticketId)
            return "the ticket $ticketId is cancelled at ${LocalDateTime.now()}"
        }
    }

