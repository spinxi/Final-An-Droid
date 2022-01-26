package com.example.afinal.AuthFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.afinal.R
import com.example.afinal.databinding.FragmentRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistrationFragment: Fragment() {
    private lateinit var regButton: Button
    private lateinit var editPassword: EditText
    private lateinit var returnbacktologin: TextView
    private lateinit var editMail:EditText
    private lateinit var editName: EditText

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private val auth = FirebaseAuth.getInstance()
    private val data = FirebaseDatabase.getInstance().getReference("Users")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        val view = binding.root
        init()
        returnBacktoLogin()
        regButtonListeners()
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun init(){
        returnbacktologin = binding.returnbacktologin
        regButton = binding.regButton
        editMail = binding.editMail
        editPassword = binding.editPassword
        editName = binding.editName
    }
    private fun returnBacktoLogin() {
        returnbacktologin.setOnClickListener{
            findNavController().navigate(RegistrationFragmentDirections.actionFragmentRegistrationToFragmentLogin())
        }
    }
    fun regButtonListeners(){
        regButton.setOnClickListener {
            if(editName.text.isEmpty()){
                Toast.makeText(activity, "Name is empty", Toast.LENGTH_LONG).show()
            }
            else if(editPassword.text.isEmpty()){
                Toast.makeText(activity, "Password is empty", Toast.LENGTH_LONG).show()
            }
            else if(editMail.text.isEmpty()){
                Toast.makeText(activity, "Email is empty", Toast.LENGTH_LONG).show()
            }
            else if(editMail.text.contains("@") == false){
                Toast.makeText(activity, "Check you Email", Toast.LENGTH_LONG).show()
            } else{
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(editMail.text.toString(), editPassword.text.toString())
                    .addOnCompleteListener{ task ->
                        if(task.isSuccessful){
                            data.child(auth.currentUser?.uid!!).child("name").setValue(editName.text.toString())

                            findNavController().navigate(RegistrationFragmentDirections.actionFragmentRegistrationToFragmentLogin())
                            Toast.makeText(activity, "You have been registered", Toast.LENGTH_SHORT).show()

                        }else{
                            Toast.makeText(activity, "Please, Try again later", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
    }
}
}