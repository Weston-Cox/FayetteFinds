package edu.uark.fayettefinds.ListBusinessCards

import android.app.Activity
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import edu.uark.fayettefinds.FayetteFindsApplication

class ListBusinessCardsActivity : AppCompatActivity() {
    val startAddEditBusinessCardsActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if(result.resultCode== Activity.RESULT_OK){
            Log.d("MainActivity","Completed")
        }
    }

    private val listBusinessCardsViewModel: ListBusinessCardsViewModel by viewModels {
        ListBusinessCardsViewModel.ListBusinessCardsViewModelFactory((application as FayetteFindsApplication).repository)
    }
}