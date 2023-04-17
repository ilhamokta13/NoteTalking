package com.example.notetalking

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notetalking.databinding.FragmentHomeBinding
import com.example.notetalking.dbroom.DatabaseNote
import com.example.notetalking.dbroom.EntityNote
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Locale.filter


class HomeFragment : Fragment() {
    lateinit var binding : FragmentHomeBinding
    lateinit var Vm : HomeViewModel
    lateinit var pref : SharedPreferences
    lateinit var adapter: HomeAdapter
    lateinit var note : ArrayList<EntityNote>
    var datbasenote :DatabaseNote? =null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = requireActivity().getSharedPreferences("Berhasil", Context.MODE_PRIVATE)!!
        val fullname = pref.getString("username", "username")
        binding.welcome.text = "Welcome, $fullname!"
        Log.d("Homescreen", "Username : $fullname")

        Vm = ViewModelProvider(this).get(HomeViewModel::class.java)
        //ViewModel
        adapter = HomeAdapter(requireActivity(),ArrayList())

        binding.rvMain.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvMain.adapter = adapter

        //LiveData
        Vm = ViewModelProvider(this).get(HomeViewModel::class.java)
        Vm.getNoteObservers().observe(viewLifecycleOwner, Observer {
            adapter.setNotes(it as ArrayList<EntityNote>)
        })
        //Room
        datbasenote = DatabaseNote.getInstance(requireContext())
        //Add Note
        binding.floatingActionButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_homeFragment_to_tambahNoteFragment)

        }

        binding.btnLogout.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_homeFragment_to_loginFragment)
        }

    }




            override fun onStart() {
                super.onStart()
                GlobalScope.launch {
                    var data = datbasenote?.noteDao()?.getDataNote()
                    activity?.runOnUiThread {
                        adapter = HomeAdapter(requireActivity(), data!!)
                        binding.rvMain.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        binding.rvMain.adapter = adapter
                    }
                }
            }


        }


