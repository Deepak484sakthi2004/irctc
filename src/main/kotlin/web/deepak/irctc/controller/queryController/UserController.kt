package web.deepak.irctc.controller.queryController

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import web.deepak.irctc.entity.User
import web.deepak.irctc.repository.UserRepo

@Controller
class UserController @Autowired
constructor(
    private val userRepo: UserRepo
)
{
    @QueryMapping
    fun getUsers() : List<User>
    {
        return userRepo.findAll()
    }

    @QueryMapping
    fun getUserById(id:Long) : User
    {
        return userRepo.findById(id).orElseThrow{
            RuntimeException("No User !")
        }
    }
}