//package web.deepak.irctc.service
//
//class booking {
//
//    fun bookTicket(uid: Long, tid: String, startStationId: Int, endStationId: Int): String {
//        // Fetch the train instance
//        val trainInstance = trainRepo.findById(tid).orElseThrow {
//            NoSuchElementException("No train with the ID $tid")
//        }
//
//        // Validate start and end stations
//        val startStation = trainInstance.stoppingStations.find { it.id == startStationId }
//            ?: throw IllegalArgumentException("Invalid start station ID: $startStationId")
//
//        val endStation = trainInstance.stoppingStations.find { it.id == endStationId }
//            ?: throw IllegalArgumentException("Invalid end station ID: $endStationId")
//
//        // Ensure booking is made before the train starts
//        if (isTrainStarted(trainInstance)) {
//            return "Booking cannot be made after the train has started."
//        }
//
//        // Check for seat availability between the start and end stations
//        val availableSeats = seatRepo.findByTrain_TrainIdAndStatus(tid, SeatStatus.FREE)
//
//
//        if (availableSeats.isNotEmpty()) {
//            // Book a seat
//            val seatToBook = availableSeats.first()
//            val ticket = TicketRegistration(
//                user = userRepo.findById(uid).orElseThrow { NoSuchElementException("No user found") },
//                train = trainInstance,
//                startStation = startStation,
//                destStation = endStation,
//                seat = seatToBook,
//                status = SeatStatus.CONFIRMED
//            )
//
//            ticketRepo.save(ticket)
//            seatToBook.seatStatus = SeatStatus.CONFIRMED
//            seatRepo.save(seatToBook)
//
//            return "Ticket booked successfully for seat ID: ${seatToBook.seatId}."
//        } else {
//            // No available seats; check for RAC
//            val racList = racRepo.findByTrainId(tid)
//
//            return if (racList.isNotEmpty()) {
//                // Handle booking for RAC
//                val racSeat = racList.first() // Implement logic to determine which RAC to use
//                racSeat.status = SeatStatus.PENDING
//                racRepo.save(racSeat)
//
//                "RAC booking successful. Your RAC ID is: ${racSeat.racId}."
//            } else {
//                // No seats available and no RAC; place in waiting list
//                val waitingList = WaitingList(
//                    user = userRepo.findById(uid).orElseThrow { NoSuchElementException("No user found") },
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
//
//    private fun isTrainStarted(train: Train): Boolean {
//        // Implement logic to determine if the train has started its journey.
//        // This might involve checking the current time against the scheduled departure time.
//        return false // Placeholder implementation
//    }
//
//    -----------------------------------------------------------
//
//    fun disembark(ticketId: Long, currentStationId: Int): String {
//        val ticketInstance = ticketRepo.findById(ticketId).orElseThrow {
//            NoSuchElementException("No ticket found with ID: $ticketId")
//        }
//
//        // Validate the current station
//        val currentStation = ticketInstance.train.stoppingStations.find { it.id == currentStationId }
//            ?: throw IllegalArgumentException("Invalid station ID: $currentStationId")
//
//        // Check if the passenger can disembark
//        if (ticketInstance.currentStation.id != currentStationId) {
//            return "Passenger cannot disembark at this station."
//        }
//
//        // Update the seat status to FREE
//        val seat = ticketInstance.seat
//        seat.seatStatus = SeatStatus.FREE
//        seatRepo.save(seat)
//
//        // Update the ticket status
//        ticketInstance.status = SeatStatus.CANCELLED // Mark as cancelled
//        ticketRepo.save(ticketInstance)
//
//        return "Passenger disembarked successfully at station: ${currentStation.name}. Seat ID: ${seat.seatId} is now free."
//    }
//
//}