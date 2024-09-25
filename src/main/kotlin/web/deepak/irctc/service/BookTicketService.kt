package web.deepak.irctc.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import web.deepak.irctc.entity.Seat
import web.deepak.irctc.entity.Station

import web.deepak.irctc.entity.TicketRegistration
import web.deepak.irctc.entity.Train
import web.deepak.irctc.entity.User
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

@Service
class BookTicketService @Autowired
constructor(

    private val trainRepo: TrainRepo,
    private val seatRepo : SeatRepo,
    private val ticketRepo : TicketRepo,
    private val racRepo : RACrepo,
    private val stationRepo: StationRepo,
    private val waitingListRepo : WaitingListRepo,
    private val userRepo: UserRepo

)

{
//    fun bookTicket(uid: Long, tid: String, startStationId: Int, endStationId: Int): String {
//
//        val trainInstance = trainRepo.findById(tid).orElseThrow{
//            NoSuchElementException("No train with the ID $tid")
//        }
//        val userInstance = userRepo.findById(uid).orElseThrow{
//            NoSuchElementException("No User with the ID $tid")
//        }
//        val startStationInstance = stationRepo.findById(startStationId).orElseThrow{
//            NoSuchElementException("No Station with the ID $tid")
//        }
//        val endStationInstance = stationRepo.findById(endStationId).orElseThrow{
//            NoSuchElementException("No Station with the ID $tid")
//        }
//
//        val availableSeats = seatRepo.findByTrain_TrainIdAndStatus(tid, SeatStatus.FREE)
//
//        return if (availableSeats.isNotEmpty()) {
//            val seatToBook = availableSeats.first()
//
//            val ticket = TicketRegistration(
//                user = userInstance,
//                train = trainInstance,
//                startStation = startStationInstance,
//                destStation =endStationInstance ,
//                seat = seatToBook,
//                status = SeatStatus.CONFIRMED
//            )
//
//            ticketRepo.save(ticket)
//
//            seatToBook.seatStatus = SeatStatus.CONFIRMED
//            seatRepo.save(seatToBook)
//
//            "Ticket booked successfully for seat ID: ${seatToBook.seatId}"
//        } else {
//
//            // No available seats; check for RAC
//            val racList = racRepo.findByTrainId(tid)
//
//            return if (racList.isNotEmpty()) {
//                // Handle booking for RAC
//                val racSeat = racList.first() // You can implement logic to determine which RAC to use
//
//                // Logic to create a RAC booking (you may need to modify this)
//                racSeat.status = SeatStatus.PENDING
//                racRepo.save(racSeat)
//
//                "RAC booking successful. Your RAC ID is: ${racSeat.racId}"
//            } else {
//                // No available seats and no RAC; place in waiting list
//                val waitingList = WaitingList(
//                    user = userInstance,
//                    train = trainInstance,
//                    status = SeatStatus.WAITING,
//                    bookedAt = LocalDateTime.now()
//                )
//                waitingListRepo.save(waitingList)
//
//                "No seats available. You have been added to the waiting list."
//            }
//        }
//    }

    fun bookTicket(uid: Long, tid: String, startStationId: Int, endStationId: Int): String {
        val trainInstance = getTrainInstance(tid)
        val userInstance = getUserInstance(uid)
        val startStationInstance = getStationInstance(startStationId)
        val endStationInstance = getStationInstance(endStationId)

        // Check for existing bookings that conflict with the new booking
        val conflictingTickets = ticketRepo.findConflictingBookings(tid, startStationId, endStationId)

        // Manage bookings and handle RAC
        if (conflictingTickets.isNotEmpty()) {
            // Handle the scenario where there are conflicting bookings
            val firstUserDropOff = conflictingTickets.first().destStation.stationId
            if (startStationId < firstUserDropOff) {
                // New booking starts before the first user disembarks
                return placeInRAC(userInstance, trainInstance)
            }
        }
        // If no conflicts, proceed with normal booking
        return bookSeat(tid, userInstance, trainInstance, startStationInstance, endStationInstance)
    }

    private fun getTrainInstance(tid: String): Train {
        return trainRepo.findById(tid).orElseThrow {
            NoSuchElementException("No train with the ID $tid")
        }
    }

    private fun getUserInstance(uid: Long): User {
        return userRepo.findById(uid).orElseThrow {
            NoSuchElementException("No User with the ID $uid")
        }
    }

    private fun getStationInstance(stationId: Int): Station {
        return stationRepo.findById(stationId).orElseThrow {
            NoSuchElementException("No Station with the ID $stationId")
        }
    }

    private fun placeInRAC(user: User, train: Train): String {
        // Check for available RAC
        val racList = racRepo.findByTrainId(train.trainId)

        return if (racList.isNotEmpty()) {
            val racSeat = racList.first() // Determine which RAC seat to use
            racSeat.status = SeatStatus.PENDING
            racRepo.save(racSeat)

            "RAC booking successful. Your RAC ID is: ${racSeat.racId}"
        } else {
            // Add to waiting list if no RAC available
            addToWaitingList(user, train)
        }
    }

    private fun bookSeat(tid: String, user: User, train: Train, startStation: Station, endStation: Station): String {
        val availableSeats = seatRepo.findByTrain_TrainIdAndStatus(tid, SeatStatus.FREE)

        return if (availableSeats.isNotEmpty()) {
            val seatToBook = availableSeats.first()
            saveTicket(user, train, startStation, endStation, seatToBook)
            "Ticket booked successfully for seat ID: ${seatToBook.seatId}"
        } else {
            placeInRAC(user, train) // Handle RAC if no seats are available
        }
    }

    private fun saveTicket(user: User, train: Train, startStation: Station, endStation: Station, seat: Seat) {
        val ticket = TicketRegistration(
            user = user,
            train = train,
            startStation = startStation,
            destStation = endStation,
            seat = seat,
            status = SeatStatus.CONFIRMED
        )
        ticketRepo.save(ticket)
        seat.seatStatus = SeatStatus.CONFIRMED
        seatRepo.save(seat)
    }

    private fun addToWaitingList(user: User, train: Train): String {
        val waitingList = WaitingList(
            user = user,
            train = train,
            status = SeatStatus.WAITING,
            bookedAt = LocalDateTime.now()
        )
        waitingListRepo.save(waitingList)
        return "No seats available. You have been added to the waiting list."
    }



    //////////////////////////////////////////////////

    fun cancelTicketBooking(ticketId : Long) : String
    {
        val ticketInstance = ticketRepo.findById(ticketId).orElseThrow{
            NoSuchElementException("No ticket with the ID $ticketId")
        }
        ticketInstance.status = SeatStatus.FREE
        ticketRepo.deleteById(ticketId)
        return "the ticket $ticketId is cancelled at ${java.time.LocalDateTime.now()}"
    }

}