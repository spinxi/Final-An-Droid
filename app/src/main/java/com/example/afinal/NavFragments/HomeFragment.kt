package com.example.afinal.NavFragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.Posts
import com.example.afinal.R
import com.example.myapplication.RecyclerViewPersonAdapter
import com.google.firebase.database.*

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var database: FirebaseDatabase
    private lateinit var referance: DatabaseReference

    private lateinit var recyclerView: RecyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        database = FirebaseDatabase.getInstance()
        referance = database.getReference("Posts")
        getData()
    }

    private fun getData(){
        referance.addValueEventListener(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var list= ArrayList<Posts>()
                for(data in snapshot.children){
                    var model = data.getValue(Posts::class.java)
                    list.add(model as Posts)
                }
                    val adapter= RecyclerViewPersonAdapter(list)
                    recyclerView.adapter = adapter
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}