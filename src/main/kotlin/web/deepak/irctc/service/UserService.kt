package web.deepak.irctc.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.stereotype.Service
import web.deepak.irctc.entity.User
import web.deepak.irctc.enum.ReferenceDoc
import web.deepak.irctc.repository.SeatRepo
import web.deepak.irctc.repository.UserRepo

@Service
class UserService @Autowired
constructor(
    private val userRepo: UserRepo,
) {

    fun createUser(Username: String, refDoc: ReferenceDoc, refId: String): User {
        var userInstance: User = User(name = Username, referenceDocs = refDoc, referenceId = refId)
        return userRepo.save<User>(userInstance)
    }

    fun getUserById(id: Long): User? {
        return userRepo.findByIdOrNull(id)
    }

    fun getAllUsers(): List<User> {
        return userRepo.findAll()
    }

    fun getAllUsersByName(Username: String): List<User>? {
        return userRepo.findByName(Username)
    }

    fun updateUser(userId: Long, username: String?, refDoc: ReferenceDoc?, refId: String?): User {
        val userInstance: User = userRepo.findById(userId).orElseThrow { RuntimeException("User not found") }

        if (username != null) {
            userInstance.name = username
        }
        if (refDoc != null) {
            userInstance.referenceDocs = refDoc
        }
        if (refId != null) {
            userInstance.referenceId = refId
        }
        return userRepo.save(userInstance)
    }

    fun removeUser(userId: Long) {
       userRepo.deleteById(userId)
    }
}