package edu.uark.fayettefinds.Repository

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.github.javafaker.Faker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

// Annotates class to be a Room Database with a table (entity) of the ToDoItem class
@Database(entities = arrayOf(BusinessCard::class), version = 2 , exportSchema = false)
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
                    "businesscard_database"
                )
                    .addCallback(FayetteFindsDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private open class FayetteFindsDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    database.businessCardDao().deleteAll()
                    populateDatabase(database.businessCardDao())
                }
            }
        }

        suspend fun populateDatabase(businessCardDao: BusinessCardDao) {
            val addresses = arrayOf(
                "459 N Campus Walk, Fayetteville, AR 72701",
                "660 M.L.K. Jr Blvd, Fayetteville, AR 72701",
                "1325 N Shiloh Dr, Fayetteville, AR 72704",
                "5 S Happy Hollow Rd, Fayetteville, AR 72701",
                "2615 S School Ave, Fayetteville, AR 72701",
                "801 W Clydesdale Dr, Fayetteville, AR 72701",
                "1100 N College Ave, Fayetteville, AR 72703",
                "4127 W Wedington Dr, Fayetteville, AR 72704",
                "123 W Dickson St, Fayetteville, AR 72701",
                "1 University of Arkansas Fayetteville, AR 72701",
                "1255 S Razorback Rd, Fayetteville, AR 72701",
                "1270 Nolan Richardson Dr, Fayetteville, AR 72701",
                "350 N Razorback Rd, Fayetteville, AR 72701",
                "1788 M.L.K. Jr Blvd, Fayetteville, AR 72701",
                "2615 S School Ave, Fayetteville, AR 72701",
                "2875 M.L.K. Jr Blvd, Fayetteville, AR 72704"
            )

            val faker = Faker()

            for (i in 1..16) {
                val businessCard = BusinessCard(
                    id = null,
                    businessName = faker.company().name(),
                    typeOfBusiness = faker.company().industry(),
                    title = faker.job().title(),
                    description = faker.lorem().paragraph(),
                    phone = faker.phoneNumber().phoneNumber(),
                    email = faker.internet().emailAddress(),
                    website = "https://google.com",
                    address = addresses[i-1]
                )
                businessCardDao.insert(businessCard)
                Log.d("Business Added with business Name:", "Business Name: ${businessCard.title}")
            }
        }
    }
}