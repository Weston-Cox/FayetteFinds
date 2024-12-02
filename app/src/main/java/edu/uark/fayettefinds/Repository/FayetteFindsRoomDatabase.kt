package edu.uark.fayettefinds.Repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.github.javafaker.Faker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random

// Annotates class to be a Room Database with a table (entity) of the ToDoItem class
@Database(entities = arrayOf(BusinessCard::class), version = 1, exportSchema = false)
public abstract class FayetteFindsRoomDatabase : RoomDatabase() {

    abstract fun businessCardDao(): BusinessCardDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: FayetteFindsRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): FayetteFindsRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FayetteFindsRoomDatabase::class.java,
                    "todolist_database"
                )
                    .addCallback(FayetteFindsDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class FayetteFindsDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.businessCardDao())
                }
            }
        }

        suspend fun populateDatabase(businessCardDao: BusinessCardDao) {
            // Delete all content here.
//            businessCardDao.deleteAll()
//
//            // Add sample words.
//            val toDoItem = BusinessCard(null,"", "", "", "", "", "", "", "", 0.0, 0.0, )
//            businessCardDao.insert(toDoItem)
            businessCardDao.deleteAll()
            val faker = Faker()
            val businesses = mutableListOf<BusinessCard>()

            for (i in 1..8) {
                businesses.add(
                    BusinessCard(
                        id = null,
                        businessName = faker.company().name(),
                        typeOfBusiness = faker.company().industry(),
                        title = faker.job().title(),
                        content = faker.lorem().paragraph(),
                        phone = faker.phoneNumber().phoneNumber(),
                        email = faker.internet().emailAddress(),
                        website = faker.internet().domainName(),
                        address = faker.address().fullAddress()
                    )
                )
            }
        }
    }
}