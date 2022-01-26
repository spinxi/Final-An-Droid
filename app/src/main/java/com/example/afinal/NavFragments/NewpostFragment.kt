package com.example.afinal.NavFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.afinal.R
import com.example.afinal.databinding.FragmentNewpostBinding
import com.example.afinal.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import android.util.Log
import android.widget.TextView
import android.widget.Toast

import com.google.firebase.database.DataSnapshot

import com.google.android.gms.tasks.Task

import androidx.annotation.NonNull
import com.bumptech.glide.Glide
import com.example.afinal.UserInfo
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class NewpostFragment : Fragment() {
    private lateinit var editTextNewPost: EditText
    private lateinit var buttonPost: Button
    private val auth = FirebaseAuth.getInstance()
    private val data = FirebaseDatabase.getInstance().getReference("Posts")
    private val dataa = FirebaseDatabase.getInstance().getReference("Users")
    private var _binding: FragmentNewpostBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewpostBinding.inflate(inflater, container, false)
        val view = binding.root
        init()
        newpost()
        return view
    }
    private fun init(){
        editTextNewPost = binding.editTextNewPost
        buttonPost = binding.buttonPost
    }
    private fun newpost() {
        buttonPost.setOnClickListener() {
            dataa.child(auth.currentUser?.uid!!).get().addOnSuccessListener {
                if (it.exists()) {
                    val imageUrl = it.child("photoUrl").value

                    val postText = editTextNewPost.text.toString()
                    if (postText.isNotEmpty()) {
                        editTextNewPost.setText("")
                        val id = data.push().key
                        data.child(id.toString()).child("title").setValue(postText)
                        data.child(id.toString()).child("imageUrl").setValue(imageUrl.toString())
                        Toast.makeText(activity, "The post was published", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

}