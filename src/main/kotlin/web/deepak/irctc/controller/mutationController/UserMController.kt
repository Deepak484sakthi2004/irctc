package web.deepak.irctc.controller.mutationController

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping

import org.springframework.stereotype.Controller
import web.deepak.irctc.entity.User
import web.deepak.irctc.enum.ReferenceDoc
import web.deepak.irctc.service.UserService

@Controller
class UserMController @Autowired
constructor(
    private val userService: UserService
)
{
    @MutationMapping
    fun addUser(@Argument uname : String,
                @Argument refDoc : ReferenceDoc,
                @Argument refId :String) : User
    {
        return userService.createUser(uname,refDoc,refId)
    }

}