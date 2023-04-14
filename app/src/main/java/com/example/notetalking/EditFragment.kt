package com.example.notetalking

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.notetalking.databinding.FragmentEditBinding
import com.example.notetalking.dbroom.DatabaseNote
import com.example.notetalking.dbroom.EntityNote
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class EditFragment : Fragment() {
    lateinit var binding : FragmentEditBinding
    lateinit var prefs : SharedPreferences
    lateinit var Vm : HomeViewModel
    var dbasenote : DatabaseNote? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbasenote = DatabaseNote.getInstance(requireContext())
        Vm = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.btnUpdate.setOnClickListener {
            GlobalScope.async {
                var judul = binding.editTitle.text.toString()
                var isi = binding.editNote.text.toString()


                val dataInsert = EntityNote(id,judul,isi)
                Vm.insert(dataInsert)

                activity?.runOnUiThread {
                    Toast.makeText(context,"Berhasil menambahkan note", Toast.LENGTH_LONG)
                }
            }
            Navigation.findNavController(binding.root).navigate(R.id.action_editFragment_to_homeFragment)

        }

        }
    }

