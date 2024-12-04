package edu.uark.fayettefinds.ListBusinessCards

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import edu.uark.fayettefinds.R
import edu.uark.fayettefinds.Repository.BusinessCard

class ListBusinessCardAdapter(val onItemClicked:(itemId:Long)->Unit)
    : ListAdapter<BusinessCard, ListBusinessCardAdapter.BusinessCardViewHolder>(BusinessCardComparator()){

        private var originalList: List<BusinessCard> = emptyList()
        private var filteredList: List<BusinessCard> = emptyList()

    //TODO:: Setup listbusinesscard adapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessCardViewHolder {
            return BusinessCardViewHolder.create(parent)
        }


    override fun onBindViewHolder(holder: BusinessCardViewHolder, position: Int) {
//        val current = getItem(position)
//        current.id?.let {
//            holder.bind(it, current.title)
//        }
//        holder.itemView.tag = current.id
//        holder.itemView.setOnClickListener {
//            val itemId = it.tag
//            Log.d("ListBusinessCardAdapter", "Item Clicked: $itemId")
//        }

        val current = getItem(position)
        holder.itemView.setOnClickListener {
            current.id?.let { it1 -> onItemClicked(it1) }
        }
        holder.bind(current)
    }

    override fun getItemCount(): Int = filteredList.size

    override fun submitList(list: List<BusinessCard>?) {
        originalList = list ?: emptyList()
        filteredList = originalList
        super.submitList(filteredList)
    }

    fun filter(query: String) {
        filteredList = if (query.isEmpty()) {
            originalList
        } else
        {
            originalList.filter {
                it.businessName.contains(query, ignoreCase = true)
            }
        }
        super.submitList(filteredList)
    }


    class BusinessCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        private val businessCardName: TextView = itemView.findViewById(R.id.textView)

        fun bind(businessCard: BusinessCard)
        {
            businessCardName.text = businessCard.businessName
            itemView.setOnLongClickListener {
                shareTask(businessCard)
                true
            }

        }

        //******************************************************************************************************
        // shareTask
        // Description: Shares the task via an intent
        // Parameters: Task
        // Returns: Unit
        //******************************************************************************************************
        private fun shareTask(businessCard: BusinessCard) {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Business: ${businessCard.title}\nDescription: ${businessCard.description}\n")
                type = "text/plain"
            }
            itemView.context.startActivity(Intent.createChooser(shareIntent, "Share business via"))
        }


        companion object {
            fun create(parent:ViewGroup): BusinessCardViewHolder {
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
                return BusinessCardViewHolder(view)
            }
        }
    }

    class BusinessCardComparator : DiffUtil.ItemCallback<BusinessCard>() {
        override fun areItemsTheSame(oldCard: BusinessCard, newCard: BusinessCard): Boolean {
            return oldCard === newCard
        }

        override fun areContentsTheSame(oldCard: BusinessCard, newCard: BusinessCard): Boolean {
            return oldCard.id === newCard.id
        }
    }
}