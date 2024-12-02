package edu.uark.fayettefinds.ListBusinessCards

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import edu.uark.fayettefinds.R
import edu.uark.fayettefinds.Repository.BusinessCard

class ListBusinessCardAdapter(val itemClicked:(itemId:Int)->Unit)
    : androidx.recyclerview.widget.ListAdapter<BusinessCard, ListBusinessCardAdapter.BusinessCardViewHolder>(BusinessCardComparator()){
    //TODO:: Setup listbusinesscard adapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessCardViewHolder {
            return BusinessCardViewHolder.create(parent)
        }

    override fun onBindViewHolder(holder: BusinessCardViewHolder, position: Int) {
        val current = getItem(position)
        current.id?.let {
            holder.bind(it, current.title)
        }
        holder.itemView.tag = current.id
        holder.itemView.setOnClickListener {
            val itemId = it.tag
            Log.d("ListBusinessCardAdapter", "Item Clicked: $itemId")
        }
    }


    class BusinessCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        private val businessCardTitleView: TextView = itemView.findViewById(R.id.textView)

        fun bind(id: Int, title: String?)
        {
            businessCardTitleView.text = title

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