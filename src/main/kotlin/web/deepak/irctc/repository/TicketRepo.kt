package web.deepak.irctc.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import web.deepak.irctc.entity.TicketRegistration

@Repository
interface TicketRepo : JpaRepository<TicketRegistration,Long>{
}