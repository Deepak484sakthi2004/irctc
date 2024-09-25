package web.deepak.irctc.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import web.deepak.irctc.enum.ReferenceDoc
import java.io.Serializable

@Entity
@Table(name="user_table")
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val UserId : Long? = null,
    var referenceDocs : ReferenceDoc,
    var referenceId : String,
    var name : String,

) : Serializable
