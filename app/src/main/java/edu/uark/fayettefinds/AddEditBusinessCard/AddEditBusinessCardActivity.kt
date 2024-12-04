package edu.uark.fayettefinds.AddEditBusinessCard

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.nfc.NfcAdapter.EXTRA_ID
import android.os.Build
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
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentContainerView
import edu.uark.fayettefinds.Util.LocationUtilCallback
import edu.uark.fayettefinds.Util.createLocationCallback
import edu.uark.fayettefinds.Util.createLocationRequest
import edu.uark.fayettefinds.Util.replaceFragmentInActivity
import org.osmdroid.util.GeoPoint

class AddEditBusinessCardActivity: AppCompatActivity() {
    private lateinit var businessCardName: TextView
    private lateinit var typeOfBusiness: TextView
    private lateinit var businessImage: ImageView
    private lateinit var businessDescription: TextView
    private lateinit var businessTitle: TextView
    private lateinit var businessEmail: String
    private lateinit var businessPhone: String
    private lateinit var businessWebsite: String
    private lateinit var btnHamburgerMenu: Button
    private lateinit var btnContact: Button
    private lateinit var fragmentContainerView: FragmentContainerView
    private lateinit var geoPoint: GeoPoint
    private lateinit var openStreetMapFragment: OpenStreetMapFragment

    private lateinit var businessCard: BusinessCard

    private val addEditBusinessCardViewModel: AddEditBusinessCardViewModel by viewModels {
        AddEditBusinessCardViewModel.AddEditBusinessCardViewModelFactory((application as FayetteFindsApplication).repository)
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
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
        if (id == (-1).toLong()) {
            populateNewBusinessCard()
        } else {
            populateExistingBusinessCard(id)
        }

        btnContact.setOnClickListener {
            PopupMenu(this, it).apply {
                inflate(R.menu.contact_menu)
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.action_email -> sendEmail(businessEmail)
                        R.id.action_phone -> makePhoneCall(businessPhone)
                        R.id.action_website -> openWebsite(businessWebsite)
                    }
                    true
                }
                show()
            }
        }
    }

    fun populateNewBusinessCard() {
        businessCard = BusinessCard(null, "", "", "", "", "", "", "", "")
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun populateExistingBusinessCard(id:Long)
    {
        addEditBusinessCardViewModel.start(id)
        addEditBusinessCardViewModel.businessCard.observe(this) {
            if(it != null) {
                businessCardName.setText(it.businessName)
                typeOfBusiness.setText(it.typeOfBusiness)
                val imageResourceString = getImageResourceGivenId(it.id!!)
                businessImage.setImageResource(resources.getIdentifier(imageResourceString, null, packageName))
                businessDescription.setText(it.description)
                businessTitle.setText(it.title)
                businessEmail = it.email
                businessPhone = it.phone
                businessWebsite = it.website

                getGeoPointFromAddress(this, it.address) { geoPoint ->
                    if (geoPoint != null) {
                        runOnUiThread {
                            openStreetMapFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
                                    as OpenStreetMapFragment? ?:OpenStreetMapFragment.newInstance().also{
                                replaceFragmentInActivity(it,R.id.fragmentContainerView)
                            }
                            openStreetMapFragment.changeCenterLocation(geoPoint)
                            openStreetMapFragment.addMarker(geoPoint, it.id!!, it.address)
                        }

                    }
                }

            }
         }
    }


    fun getImageResourceGivenId(id: Long): String {
        return when (id) {
            1L -> "@drawable/research_company"
            2L -> "@drawable/computer_hardware"
            3L -> "@drawable/government_admin"
            4L -> "@drawable/graphic_design"
            5L -> "@drawable/animation"
            6L -> "@drawable/import_export"
            7L -> "@drawable/libraries"
            8L -> "@drawable/veterinarian"
            9L -> "@drawable/veterinarian"
            10L -> "@drawable/veterinarian"
            11L -> "@drawable/veterinarian"
            12L -> "@drawable/veterinarian"
            13L -> "@drawable/veterinarian"
            14L -> "@drawable/veterinarian"
            15L -> "@drawable/veterinarian"
            16L -> "@drawable/veterinarian"
            else -> "@drawable/placeholder_image"
        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun getGeoPointFromAddress(context: Context, address: String, callback: (GeoPoint?) -> Unit) {
        val geocoder = Geocoder(context)
        val listener = @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        object : Geocoder.GeocodeListener {
            override fun onGeocode(addresses: List<Address>) {
                if (addresses.isNotEmpty()) {
                    val location = addresses[0]
                    callback(GeoPoint(location.latitude, location.longitude))
                } else {
                    callback(null)
                }
            }

            override fun onError(errorMessage: String?) {
                Log.e("GeocodeError", errorMessage ?: "Unknown error")
                callback(null)
            }
        }
        geocoder.getFromLocationName(address, 1, listener)
    }

    fun sendEmail(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
        }
        startActivity(intent)
    }

    fun makePhoneCall(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phone")
        }
        startActivity(intent)
    }

    fun openWebsite(website: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(website))
        startActivity(intent)
    }

    companion object{
        val EXTRA_ID = "FayetteFinds.AddEditBusinessCardActivity.id"
    }
}