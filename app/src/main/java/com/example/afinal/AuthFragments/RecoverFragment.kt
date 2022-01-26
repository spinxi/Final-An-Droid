package com.example.afinal.AuthFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.afinal.R
import com.example.afinal.databinding.FragmentRecoverBinding
import com.google.firebase.auth.FirebaseAuth

class RecoverFragment : Fragment() {
    private lateinit var editMail: EditText
    private lateinit var returnbacktologin: TextView
    private lateinit var sendButton: Button
    private var _binding: FragmentRecoverBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecoverBinding.inflate(inflater, container, false)
        val view = binding.root
        init()
        returnBacktoLogin()
        sendButtonListeners()
        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun init(){
        returnbacktologin = binding.returnbacktologin
        editMail = binding.editMail
        sendButton = binding.sendButton
    }
    private fun returnBacktoLogin() {
        returnbacktologin.setOnClickListener{
            findNavController().navigate(RecoverFragmentDirections.actionFragmentRecoverToFragmentLogin())
        }
    }
    fun sendButtonListeners(){
        sendButton.setOnClickListener{
            val mail = editMail.text.toString()
            if (mail.isNotEmpty()) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(mail)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful && mail.isNotEmpty()) {
                            Toast.makeText(
                                activity,
                                "Recovery link has been sent to your Email",
                                Toast.LENGTH_SHORT
                            ).show()
                            findNavController().navigate(RecoverFragmentDirections.actionFragmentRecoverToFragmentLogin())
                        } else {
                            Toast.makeText(activity, "Please, try again later", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
