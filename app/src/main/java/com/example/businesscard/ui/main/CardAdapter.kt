package com.example.businesscard.ui.main

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.businesscard.data.BusinessCardEntity
import com.example.businesscard.databinding.ItemBusinessCardLayoutBinding
import java.io.File

class CardAdapter(private val cardList: List<BusinessCardEntity>) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>(), Filterable {

    private var cardFiltered: List<BusinessCardEntity> = cardList
    private lateinit var listener: Listener

    inner class CardViewHolder(val binding: ItemBusinessCardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
            ItemBusinessCardLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cardFiltered[position]

        holder.binding.textName.text = card.name
        holder.binding.textPhone.text = card.phone
        holder.binding.textEmail.text = card.email
        holder.binding.companyName.text = card.company

        holder.binding.textName.setTextColor(Color.parseColor(card.textColor))
        holder.binding.textPhone.setTextColor(Color.parseColor(card.textColor))
        holder.binding.textEmail.setTextColor(Color.parseColor(card.textColor))
        holder.binding.companyName.setTextColor(Color.parseColor(card.textColor))
        holder.binding.iconShare.setColorFilter(Color.parseColor(card.textColor))

        holder.binding.card.setCardBackgroundColor(Color.parseColor(card.color))

        holder.binding.card.setOnLongClickListener {
            listener.onLongClick(card.id)
            true
        }
        holder.binding.iconShare.setOnClickListener {
            listener.onClick(holder.binding.card)
        }

    }

    override fun getItemCount() = cardFiltered.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                cardFiltered = if (charString.isEmpty()) {
                    cardList
                } else {
                    val result: MutableList<BusinessCardEntity> = mutableListOf()
                    for (item in cardList) {
                        if (item.name.lowercase()
                                .contains(charString.lowercase()) || item.company.lowercase()
                                .contains(charString.lowercase()) ||
                            item.email.lowercase()
                                .contains(charString.lowercase()) || item.phone.contains(charString)
                        ) {
                            result.add(item)
                        }
                    }
                    result
                }
                val resultFiltered = FilterResults()
                resultFiltered.values = cardFiltered
                return resultFiltered
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                cardFiltered = results!!.values as List<BusinessCardEntity>
                notifyDataSetChanged()
            }

        }
    }

    fun attachListener(listener: Listener) {
        this.listener = listener
    }
}