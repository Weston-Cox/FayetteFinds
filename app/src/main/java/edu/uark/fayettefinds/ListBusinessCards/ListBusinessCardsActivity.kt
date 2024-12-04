package edu.uark.fayettefinds.ListBusinessCards

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.uark.fayettefinds.AddEditBusinessCard.AddEditBusinessCardActivity
import edu.uark.fayettefinds.FayetteFindsApplication
import edu.uark.fayettefinds.LoginPage.LoginPageActivity
import edu.uark.fayettefinds.R

class ListBusinessCardsActivity : AppCompatActivity() {
    val startAddEditBusinessCardsActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if(result.resultCode== Activity.RESULT_OK){
            Log.d("ListBusinessCardsActivity","Completed")
        }
    }

    private lateinit var searchView: androidx.appcompat.widget.SearchView

    private val listBusinessCardsViewModel: ListBusinessCardsViewModel by viewModels {
        ListBusinessCardsViewModel.ListBusinessCardsViewModelFactory((application as FayetteFindsApplication).repository)
    }

    //******************************************************************************************************
    // launchNewTaskActivity
    // Description: Launches the TaskDetailScreenActivity. Used as a callback to the TaskListAdapter
    // Parameters: Long
    // Returns: Unit
    //******************************************************************************************************
    fun launchNewTaskActivity(id: Long) {
        val secondActivityIntent = Intent (this, AddEditBusinessCardActivity::class.java)
        secondActivityIntent.putExtra(EXTRA_ID, id)
        this.startActivity(secondActivityIntent)
    }




    //TODO:: setup adapter for recycler view
    fun recyclerAdapterItemClicked(itemId:Long){
        startAddEditBusinessCardsActivity.launch(
            Intent(this, AddEditBusinessCardActivity::class.java).putExtra(
            AddEditBusinessCardActivity.EXTRA_ID,itemId))
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_list_screen)

        // Get a reference to the recycler view object
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        // Create an adapter object, passing the launchNewTaskActivity callback
        val adapter = ListBusinessCardAdapter(this::launchNewTaskActivity)
        // Set the adapter for the recycler view to the adapter object
        recyclerView.adapter = adapter
        // Set the recycler view layout to be a linear layout manager with activity context
        recyclerView.layoutManager = LinearLayoutManager(this)

        //Searchview definition
        searchView = findViewById(R.id.searchView)

        listBusinessCardsViewModel.allBusinessCards.observe(this) {
            businesscards ->
            businesscards?.let {
                adapter.submitList(it.values.toList())
            }
        }

        searchView?.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { adapter.filter(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { adapter.filter(it) }
                return true
            }
        })

        findViewById<FloatingActionButton>(R.id.fabLogout).setOnClickListener {
            val intent = Intent(this@ListBusinessCardsActivity, LoginPageActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    companion object{
        val EXTRA_ID = "FayetteFinds.AddEditBusinessCardActivity.id"
    }

}
