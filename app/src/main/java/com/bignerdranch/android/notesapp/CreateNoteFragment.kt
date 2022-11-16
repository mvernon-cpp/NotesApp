package com.bignerdranch.android.notesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.bignerdranch.android.notesapp.database.NotesDatabase
import com.bignerdranch.android.notesapp.entities.Notes
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

import kotlinx.android.synthetic.main.fragment_create_note.*


class CreateNoteFragment : BaseFragment() {
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
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())

        tvDateTime.text = currentDate

        imgDone.setOnClickListener{
            saveNote()
        }

        imgBack.setOnClickListener{
            replaceFragment(HomeFragment.newInstance(), false)
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
                NotesDatabase.getDatabase(it).notesDao().insertNotes(notes)
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
}