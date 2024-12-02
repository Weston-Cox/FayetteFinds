package edu.uark.fayettefinds

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.androidbrowserhelper.trusted.NotificationUtils
import edu.uark.fayettefinds.AddEditBusinessCard.AddEditBusinessCardActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val id = intent.getIntExtra(AddEditBusinessCardActivity.EXTRA_ID,0)
        Log.d("BroadcastReceiver", id.toString())
        (context.applicationContext as FayetteFindsApplication).applicationScope.launch {
            val toDoItem = (context.applicationContext as FayetteFindsApplication).repository.getToDoItem(id)
            if (toDoItem.content.length > 100)
                NotificationUtils().createNotification(context,"ToDoItem \"${toDoItem.title}\" is due!",toDoItem.content.substring(0,100)+"...",id)
            else
                NotificationUtils().createNotification(context,"ToDoItem \"${toDoItem.title}\" is due!",toDoItem.content,id)
        }
    }
}