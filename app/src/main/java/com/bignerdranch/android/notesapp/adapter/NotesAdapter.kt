package com.bignerdranch.android.notesapp.adapter

import android.annotation.SuppressLint
import android.app.PendingIntent.getActivity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.notesapp.R
import com.bignerdranch.android.notesapp.entities.Notes
import kotlinx.android.synthetic.main.item_rv_notes.view.*

class NotesAdapter (val arrList: List<Notes>) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_notes,parent, false)
        )
    }

    override fun getItemCount(): Int {
        return arrList.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: NotesAdapter.NotesViewHolder, position: Int) {
        holder.itemView.tvTitle.text = arrList[position].title
        holder.itemView.tvDesc.text = arrList[position].noteText
        holder.itemView.tvDateTime.text = arrList[position].dateTime

        if(arrList[position].color != null){
            holder.itemView.cardView.setCardBackgroundColor(Color.parseColor(arrList[position].color))
        }
        else{
            //from the tutorial
            //holder.itemView.cardView.setCardBackgroundColor(Color.parseColor(R.color.colorLightBlack.toString()))

            //other solutions i found online
            holder.itemView.cardView.setCardBackgroundColor(R.color.colorLightBlack)
            //holder.itemView.cardView.setCardBackgroundColor(ContextCompat.getColor(getActivity(),R.color.colorLightBlack))
        }
    }

    class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

}