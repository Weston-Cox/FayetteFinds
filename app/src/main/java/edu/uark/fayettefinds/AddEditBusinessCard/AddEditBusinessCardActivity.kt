package edu.uark.fayettefinds.AddEditBusinessCard

import android.nfc.NfcAdapter.EXTRA_ID
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import edu.uark.fayettefinds.FayetteFindsApplication
import edu.uark.fayettefinds.R
import edu.uark.fayettefinds.Repository.BusinessCard

class AddEditBusinessCardActivity: AppCompatActivity() {

    private lateinit var businessCard: BusinessCard

    private val addEditBusinessCardViewModel: AddEditBusinessCardViewModel by viewModels {
        AddEditBusinessCardViewModel.AddEditBusinessCardViewModelFactory((application as FayetteFindsApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_details_screen)
        val id = intent.getIntExtra(EXTRA_ID, -1)
        if (id == -1){
            populateNewBusinessCard()
        }
        else
        {
            populateExistingBusinessCard(id)
        }
    }

    fun populateNewBusinessCard() {
        businessCard = BusinessCard(null, "", "", "", "", "", "", "", "")
        updateViewUI()
    }

    fun populateExistingBusinessCard(id:Int)
    {
        addEditBusinessCardViewModel.start(id)
        addEditBusinessCardViewModel.businessCard.observe(this) {
            if(it != null) {
                businessCard = it
                updateViewUI()
            }
        }
    }

    fun updateViewUI()
    {
        Log.d("Hell yeah", "Yay!")
    }

    companion object{
        val EXTRA_ID = "FayetteFinds.AddEditBusinessCardActivity.id"
    }
}