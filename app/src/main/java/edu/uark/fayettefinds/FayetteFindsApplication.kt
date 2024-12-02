package edu.uark.fayettefinds

import android.app.Application
import edu.uark.fayettefinds.Repository.FayetteFindsRepository
import edu.uark.fayettefinds.Repository.FayetteFindsRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class FayetteFindsApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { FayetteFindsRoomDatabase.getDatabase(this,applicationScope)}
    val repository by lazy{ FayetteFindsRepository(database.businessCardDao()) }
}