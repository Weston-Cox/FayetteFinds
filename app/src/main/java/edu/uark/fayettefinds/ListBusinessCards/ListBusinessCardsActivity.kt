package edu.uark.fayettefinds.ListBusinessCards

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.uark.fayettefinds.AddEditBusinessCard.AddEditBusinessCardActivity
import edu.uark.fayettefinds.FayetteFindsApplication
import edu.uark.fayettefinds.R

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


    //TODO:: setup adapter for recycler view
    fun recyclerAdapterItemClicked(itemId:Int){
        startAddEditBusinessCardsActivity.launch(
            Intent(this, AddEditBusinessCardActivity::class.java).putExtra(
            AddEditBusinessCardActivity.EXTRA_ID,itemId))
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_list_screen)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ListBusinessCardAdapter(this::recyclerAdapterItemClicked)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        listBusinessCardsViewModel.allBusinessCards.observe(this) {
            businesscards ->
            businesscards.let {
                adapter.submitList(it.values.toList())
            }
        }
        val fab = findViewById<FloatingActionButton>(R.id.fabAddTask)
        fab.setOnClickListener {
            startAddEditBusinessCardsActivity.launch(Intent(this, AddEditBusinessCardActivity::class.java))
        }
    }




}