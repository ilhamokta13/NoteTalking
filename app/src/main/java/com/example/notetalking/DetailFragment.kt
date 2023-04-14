package com.example.notetalking

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.notetalking.databinding.FragmentDetailBinding
import com.example.notetalking.dbroom.DatabaseNote
import com.example.notetalking.dbroom.EntityNote


class DetailFragment : Fragment() {
    lateinit var binding : FragmentDetailBinding
    lateinit var prefs : SharedPreferences
    lateinit var Vm :HomeViewModel
    var dbasenote : DatabaseNote? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = requireActivity().getSharedPreferences("Berhasil", Context.MODE_PRIVATE)!!
        val fullname = prefs.getString("username","username")
        binding.welcome.text = "Welcome, $fullname!"
        Log.d("Detailscreen", "username : $fullname")

        binding.btnLogout.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_detailFragment_to_loginFragment)
        }

        //Get Data from Adapter
        dbasenote = DatabaseNote.getInstance(requireContext())
        Vm = ViewModelProvider(this).get(HomeViewModel::class.java)
        var getNote = arguments?.getSerializable("datanotes") as EntityNote
        binding.Detail.setText(getNote.title)
        binding.Content.setText(getNote.content)

    }

}