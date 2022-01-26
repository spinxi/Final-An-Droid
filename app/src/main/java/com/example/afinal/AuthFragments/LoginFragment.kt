package com.example.afinal.AuthFragments

import android.content.Intent
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
import com.example.afinal.MainActivity
import com.example.afinal.R
import com.example.afinal.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginFragment: Fragment() {
    private lateinit var editMail: EditText
    private lateinit var editPassword: EditText
    private lateinit var authorisationButton: Button
    private lateinit var registerButton: Button
    private lateinit var recoverPass: TextView
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        init()
        registerButtonListeners()
        recoverTextListener()
        authorisationButtonListeners()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun init() {
        editMail = binding.editMail
        editPassword = binding.editPassword
        authorisationButton = binding.authorisationButton
        registerButton = binding.registerButton
        recoverPass = binding.recoverPass
    }

    private fun registerButtonListeners() {
        registerButton.setOnClickListener{
            findNavController().navigate(LoginFragmentDirections.actionFragmentLoginToFragmentRegistration())
        }
    }
    private fun recoverTextListener(){
        recoverPass.setOnClickListener{
            findNavController().navigate(LoginFragmentDirections.actionFragmentLoginToFragmentRecover())
        }
    }
    private fun authorisationButtonListeners(){
        authorisationButton.setOnClickListener{
            val email = editMail.text.toString()
            val password = editPassword.text.toString()

            if(editMail.text.isEmpty()){
                Toast.makeText(activity, "Email is Empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            else if(editMail.text.contains("@") == false){
                Toast.makeText(activity, "Check you Email", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            else if(editPassword.text.isEmpty()) {
                Toast.makeText(activity, "Password is empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }else{
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener{ task ->
                        if (task.isSuccessful){
                            Toast.makeText(activity, "You entered successfully", Toast.LENGTH_LONG).show()
                            val intent = Intent(activity, MainActivity::class.java)
                            startActivity(intent)
                            activity?.finish()
                        }else{
                            Toast.makeText(activity, "Please, try again later", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }

    }

}