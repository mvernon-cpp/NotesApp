package com.bignerdranch.android.notesapp.adapter

import android.annotation.SuppressLint
import android.app.PendingIntent.getActivity
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.notesapp.R
import com.bignerdranch.android.notesapp.entities.Notes
import kotlinx.android.synthetic.main.fragment_create_note.view.*
import kotlinx.android.synthetic.main.item_rv_notes.view.*
import kotlinx.android.synthetic.main.item_rv_notes.view.imgNote
import kotlinx.android.synthetic.main.item_rv_notes.view.tvDateTime
import kotlinx.android.synthetic.main.item_rv_notes.view.tvWebLink

class NotesAdapter () :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>(){
    var listener:OnItemClickListener? = null

    var arrList = ArrayList<Notes>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_notes,parent, false)
        )
    }

    override fun getItemCount(): Int {
        return arrList.size
    }

    fun setData(arrNotesList: List<Notes>) {
        arrList = arrNotesList as ArrayList<Notes>
    }

    fun setOnClickListener(listener1: OnItemClickListener){
        listener = listener1
    }
//    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: NotesAdapter.NotesViewHolder, position: Int) {
        holder.itemView.tvTitle.text = arrList[position].title
        holder.itemView.tvDesc.text = arrList[position].noteText
        holder.itemView.tvDateTime.text = arrList[position].dateTime

        if(arrList[position].color != null){
            holder.itemView.cardView.setCardBackgroundColor(Color.parseColor(arrList[position].color))
        }
        else{
            //from the tutorial
//            holder.itemView.cardView.setCardBackgroundColor(Color.parseColor(R.color.colorLightBlack.toString()))

            //other solutions i found online
//            holder.itemView.cardView.setCardBackgroundColor(R.color.colorLightBlack)
            //holder.itemView.cardView.setCardBackgroundColor(ContextCompat.getColor(getActivity(),R.color.colorLightBlack))
        }
        if(arrList[position].imgPath != null){
            holder.itemView.imgNote.setImageBitmap(BitmapFactory.decodeFile(arrList[position].imgPath))
            holder.itemView.imgNote.visibility = View.VISIBLE
        }
        else{
            holder.itemView.imgNote.visibility = View.GONE
        }

        if(arrList[position].webLink != null){
            holder.itemView.tvWebLink.text = arrList[position].webLink
            holder.itemView.tvWebLink.visibility = View.VISIBLE
        }
        else{
            holder.itemView.tvWebLink.visibility = View.GONE
        }

        holder.itemView.cardView.setOnClickListener {
            listener!!.onClicked(arrList[position].id!!)
        }
    }

    class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    interface OnItemClickListener{
        fun onClicked(noteId: Int)
    }
}