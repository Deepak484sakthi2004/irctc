package web.deepak.irctc.controller.queryController

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import web.deepak.irctc.entity.TicketRegistration
import web.deepak.irctc.repository.TicketRepo

@Controller
class TicketController @Autowired
constructor(
    private val ticketRepo: TicketRepo,

)
{
    @QueryMapping
    fun getTickets() : List<TicketRegistration>
    {
        return ticketRepo.findAll()
    }

    @QueryMapping
    fun getTicketById(tid:Long) : TicketRegistration
    {
        return ticketRepo.findById(tid).orElseThrow{
            RuntimeException("No ticket with the provided Id")
        }
    }


}