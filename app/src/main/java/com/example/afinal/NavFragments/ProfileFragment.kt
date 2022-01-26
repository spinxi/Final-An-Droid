package com.example.afinal.NavFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.afinal.LoginActivity
import com.example.afinal.R
import com.example.afinal.UserInfo
import com.example.afinal.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileFragment: Fragment(){
    private lateinit var imageView:ImageView
    private lateinit var nameSurname: TextView
    private lateinit var editPersonName: EditText
    private lateinit var editPersonPhoto: EditText
    private lateinit var editPersonPassword: EditText
    private lateinit var buttonSave: Button
    private lateinit var buttonLogout:Button
    private val auth = FirebaseAuth.getInstance()
    private val data = FirebaseDatabase.getInstance().getReference("Users")

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        init()
        insertedListeners()
        callbackListeners()
        return view
    }
    private fun init(){
        imageView = binding.imageView
        nameSurname = binding.nameSurname
        editPersonName = binding.editPersonName
        editPersonPhoto = binding.editPersonPhoto
        editPersonPassword = binding.editPersonPassword
        buttonSave = binding.buttonSave
        buttonLogout = binding.buttonLogout
    }
    private fun insertedListeners(){
        buttonSave.setOnClickListener{
            val name = editPersonName.text.toString()
            val photoUrl = editPersonPhoto.text.toString()
            val newPassword = editPersonPassword.text.toString()

            if(name.isNotEmpty()){
                //data.child(auth.currentUser?.uid!!).setValue(userInfo.name)
                data.child(auth.currentUser?.uid!!).child("name").setValue(name)
                editPersonName.setText("")
            }
            if (photoUrl.isNotEmpty()) {
                data.child(auth.currentUser?.uid!!).child("photoUrl").setValue(photoUrl)
                editPersonPhoto.setText("")
            }
            if(newPassword.isNotEmpty() && newPassword.length >= 6){
                FirebaseAuth.getInstance().currentUser?.updatePassword(newPassword)?.addOnCompleteListener(){ task ->
                    if(task.isSuccessful){
                        Toast.makeText(activity, "Password has been changed", Toast.LENGTH_LONG).show()
                        editPersonPassword.setText("")
                    }
                }
            }
            if(newPassword.length > 0 && newPassword.length <= 6){
                Toast.makeText(activity, "Password must contain 8 symbols", Toast.LENGTH_LONG).show()
                editPersonPassword.setText("")
            }


        }
        buttonLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity,LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun callbackListeners(){
        data.child(auth.currentUser?.uid!!).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild("photoUrl") == false) {
                    data.child(auth.currentUser?.uid!!).child("photoUrl").setValue("https://i.stack.imgur.com/l60Hf.png")
                }
                val userInfo:UserInfo = snapshot.getValue(UserInfo :: class.java) ?: return
                if(activity !=null) {
                    Glide.with(this@ProfileFragment)
                        .load(userInfo.photoUrl)
                        .placeholder(R.drawable.ic_baseline_supervised_user_circle_24)
                        .into(imageView)
                }

                nameSurname.text = userInfo.name
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}