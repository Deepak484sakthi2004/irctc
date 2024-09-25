package web.deepak.irctc.controller.queryController

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import web.deepak.irctc.entity.User
import web.deepak.irctc.enum.ReferenceDoc
import web.deepak.irctc.service.UserService

@Controller
class UserController @Autowired
constructor(
    private val userService: UserService
)
{
    @QueryMapping
    fun getUsers() : List<User>
    {
        return userService.getAllUsers()
    }

    @QueryMapping
    fun getUserById(id:Long) : User?
    {
        return userService.getUserById(id)
    }

}