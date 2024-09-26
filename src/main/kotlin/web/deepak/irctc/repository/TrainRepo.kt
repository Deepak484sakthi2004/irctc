package web.deepak.irctc.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import web.deepak.irctc.entity.RAC
import web.deepak.irctc.entity.Train
import web.deepak.irctc.entity.WaitingList

@Repository
interface TrainRepo : JpaRepository<Train, String> {

    fun findByTrainName(trainName: String): List<Train>?

    @Query("SELECT t.racList FROM Train t WHERE t.trainId = :trainId")
    fun findRacListByTrainId(trainId: String): List<RAC>

}



/*

You're correct; the `findRacListByTrainId` method as defined in the `TrainRepo` interface will not automatically retrieve the RAC list because it's not supported directly by Spring Data JPA in the way you have it written. The method would need to be implemented separately to leverage the relationship defined in your entity. Here's how you can achieve this:

### Option 1: Use JPA Query Methods

You can write a query in the `TrainRepo` to retrieve the `RAC` list directly from the `Train` entity. Since you already have a `OneToMany` relationship defined, you can leverage the query capabilities of Spring Data JPA.

Here's how to do it:

#### Modify TrainRepo

```kotlin
package web.deepak.irctc.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import web.deepak.irctc.entity.RAC
import web.deepak.irctc.entity.Train

@Repository
interface TrainRepo : JpaRepository<Train, String> {

    fun findByTrainName(trainName: String): List<Train>?

    // Custom query to find RAC list by train ID
    @Query("SELECT t.racList FROM Train t WHERE t.trainId = :trainId")
    fun findRacListByTrainId(trainId: String): List<RAC>
}
```

### Option 2: Retrieve Through Entity

As previously mentioned, another approach is to fetch the `Train` entity by its ID and then access the `racList`:

```kotlin
package web.deepak.irctc.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import web.deepak.irctc.entity.RAC
import web.deepak.irctc.repository.TrainRepo

@Service
class TrainService {

    @Autowired
    private lateinit var trainRepo: TrainRepo

    fun getRacListByTrainId(trainId: String): List<RAC>? {
        val train = trainRepo.findById(trainId)
        return train?.racList // Return the RAC list if the train exists
    }
}
```

### Summary

- **Direct Query Method**: The `findRacListByTrainId` can use a JPQL query to directly fetch the `RAC` list associated with a specific `trainId`. This approach is concise and directly leverages the repository.
- **Entity Retrieval**: Alternatively, you can retrieve the entire `Train` object and access its `racList`. This is a straightforward way if you need additional data from the `Train` entity.

Both approaches are valid; choose the one that best fits your design and requirements. If you only need the `RAC` list, the first approach might be preferable for clarity and efficiency. If you also need other train details, the second approach would be suitable.
 */