package com.bignerdranch.android.notesapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bignerdranch.android.notesapp.database.NotesDatabase
import com.bignerdranch.android.notesapp.entities.Notes
import com.bignerdranch.android.notesapp.util.NoteBottomSheetFragment
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

import kotlinx.android.synthetic.main.fragment_create_note.*


class CreateNoteFragment : BaseFragment() {

    var selectedColor = "#171C26"
    var currentDate:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_note, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CreateNoteFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            BroadcastReceiver, IntentFilter("bottom_sheet_action")
        )

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())
        colorView.setBackgroundColor(Color.parseColor(selectedColor))

        tvDateTime.text = currentDate

        imgDone.setOnClickListener{
            saveNote()
        }

        imgBack.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }

        imgMore.setOnClickListener{
            var noteBottomSheetFragment = NoteBottomSheetFragment.newInstance()
            noteBottomSheetFragment.show(requireActivity().supportFragmentManager,"Note Bottom Sheet Fragment")
        }

    }

    private fun saveNote() {
        if( etNoteTitle.text.isNullOrEmpty()) {
            Toast.makeText(context,"Note Title Required", Toast.LENGTH_SHORT).show()
        }

        if( etNoteSubtitle.text.isNullOrEmpty()) {
            Toast.makeText(context,"Note Subtitle Required", Toast.LENGTH_SHORT).show()
        }

        if( etNoteDesc.text.isNullOrEmpty()) {
            Toast.makeText(context,"Note Description Required", Toast.LENGTH_SHORT).show()
        }

        launch{
            var notes = Notes()
            notes.title = etNoteTitle.text.toString()
            notes.subTitle = etNoteSubtitle.text.toString()
            notes.noteText = etNoteDesc.text.toString()
            notes.dateTime = currentDate

            context?.let {
                NotesDatabase.getDatabase(it).noteDao().insertNotes(notes)
                etNoteTitle.setText("")
                etNoteSubtitle.setText("")
                etNoteDesc.setText("")
            }

        }

    }

    private fun replaceFragment(fragment: Fragment, istransition: Boolean) {
        val fragmentTransition = requireActivity().supportFragmentManager.beginTransaction()

        if(istransition){
            fragmentTransition.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_in_left)
        }
        fragmentTransition.replace(R.id.frame_layout,fragment).addToBackStack(fragment.javaClass.simpleName)
    }

    private val BroadcastReceiver : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {

            val actionColor = p1!!.getStringExtra("actionColor")

            when(actionColor!!){

                "Blue" -> {
                    selectedColor = p1.getStringExtra("actionColor")!!
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "Yellow" -> {
                    selectedColor = p1.getStringExtra("actionColor")!!
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }
                "Purple" -> {
                    selectedColor = p1.getStringExtra("actionColor")!!
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }
                "Green" -> {
                    selectedColor = p1.getStringExtra("actionColor")!!
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }
                "Orange" -> {
                    selectedColor = p1.getStringExtra("actionColor")!!
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }
                "Black" -> {
                    selectedColor = p1.getStringExtra("actionColor")!!
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }
                else -> {
                    selectedColor = p1.getStringExtra("actionColor")!!
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }
            }
        }

    }

    override fun onDestroy() {

        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(BroadcastReceiver)
        super.onDestroy()
    }
}