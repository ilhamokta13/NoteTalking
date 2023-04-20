package com.example.notetalking
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.notetalking.databinding.FragmentTambahNoteBinding
import com.example.notetalking.dbroom.DatabaseNote
import com.example.notetalking.dbroom.EntityNote
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class TambahNoteFragment : Fragment() {
    lateinit var binding : FragmentTambahNoteBinding
    lateinit var Vm : HomeViewModel
    var dbasenote : DatabaseNote? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTambahNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        dbasenote = DatabaseNote.getInstance(requireContext())
        Vm = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.btnInput.setOnClickListener {
                GlobalScope.async {
                    var judul = binding.inputTitle.text.toString()
                    var isi = binding.inputNote.text.toString()

                    val dataInsert = EntityNote(0,judul,isi)
                    Vm.insert(dataInsert)

                    activity?.runOnUiThread {
                        Toast.makeText(context,"Berhasil menambahkan note", Toast.LENGTH_LONG)
                    }
                }
            Navigation.findNavController(binding.root).navigate(R.id.action_tambahNoteFragment_to_homeFragment)
        }
    }

}


