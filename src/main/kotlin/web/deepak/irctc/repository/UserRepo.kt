package web.deepak.irctc.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import web.deepak.irctc.entity.User

@Repository
interface UserRepo : JpaRepository<User,Long>{
    fun findByName(name :String) : List<User>?
}