package edu.uark.fayettefinds.AddEditBusinessCard

import android.nfc.NfcAdapter.EXTRA_ID
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import edu.uark.fayettefinds.FayetteFindsApplication
import edu.uark.fayettefinds.R
import edu.uark.fayettefinds.Repository.BusinessCard
import android.widget.TextView
import android.widget.ImageView
import android.widget.Button
import androidx.fragment.app.FragmentContainerView

class AddEditBusinessCardActivity: AppCompatActivity() {
    private lateinit var businessCardName: TextView
    private lateinit var typeOfBusiness: TextView
    private lateinit var businessImage: ImageView
    private lateinit var businessDescription: TextView
    private lateinit var businessTitle: TextView
    private lateinit var btnHamburgerMenu: Button
    private lateinit var btnContact: Button
    private lateinit var fragmentContainerView: FragmentContainerView

    private lateinit var businessCard: BusinessCard

    private val addEditBusinessCardViewModel: AddEditBusinessCardViewModel by viewModels {
        AddEditBusinessCardViewModel.AddEditBusinessCardViewModelFactory((application as FayetteFindsApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_details_screen)

        businessCardName = findViewById(R.id.tvBusinessName)
        typeOfBusiness = findViewById(R.id.tvTypeOfBusiness)
        businessImage = findViewById(R.id.ivBusinessImage)
        businessDescription = findViewById(R.id.tvDescription)
        businessTitle = findViewById(R.id.tvTitle)
        btnHamburgerMenu = findViewById(R.id.btnHamburgerMenu)
        btnContact = findViewById(R.id.btnContact)
        fragmentContainerView = findViewById(R.id.fragmentContainerView)



        val id = intent.getLongExtra(EXTRA_ID, -1)
        if (id == (-1).toLong()){
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

    fun populateExistingBusinessCard(id:Long)
    {
        addEditBusinessCardViewModel.start(id)
        addEditBusinessCardViewModel.businessCard.observe(this) {
            if(it != null) {
                businessCardName.setText(it.businessName)
                typeOfBusiness.setText(it.typeOfBusiness)
//                businessImage.setImageResource(it.businessImage)
                businessDescription.setText(it.description)
                businessTitle.setText(it.title)

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