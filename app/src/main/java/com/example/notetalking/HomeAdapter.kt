package com.example.notetalking

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.notetalking.databinding.ItemListBinding
import com.example.notetalking.dbroom.DatabaseNote
import com.example.notetalking.dbroom.EntityNote
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.newSingleThreadContext



class HomeAdapter(var context: Context, var note: List<EntityNote>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    var Note: DatabaseNote? = null

    class ViewHolder(var binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EntityNote) {
            binding.datbasenote = item
        }

    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(note[position])
        // delete note
        holder.binding.btnDelete.setOnClickListener {
            Note = DatabaseNote.getInstance(it.context)
            GlobalScope.async {
                HomeViewModel(Application()).delete(note[position])
                Note?.noteDao()?.deleteNote(note[position])
                kotlin.run {
                    Navigation.findNavController(it).navigate(R.id.homeFragment)
                }


            }
        }
        holder.binding.btnEdit.setOnClickListener {
            var edit = Bundle()
            edit.putSerializable("edit",note[position])
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_editFragment,edit)
//            val fragmentManager = (it.context as AppCompatActivity).supportFragmentManager
//            val dialogFragment = EditFragment()
//            dialogFragment.show(fragmentManager, "MyDialogFragment")

        }

        holder.binding.btnDetail.setOnClickListener {
            var detail = Bundle()
            detail.putSerializable("datanotes", note[position])
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_detailFragment, detail)

        }
    }


    override fun getItemCount(): Int {
        return note.size

    }


    fun setNotes(itemNote: ArrayList<EntityNote>) {
        this.note = itemNote
    }


}