package com.example.isaquecoelho.mbeventapp.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.isaquecoelho.mbeventapp.R
import com.example.isaquecoelho.mbeventapp.data.FirebaseConnection
import com.example.isaquecoelho.mbeventapp.model.Event
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.catalog_fragment.*

class CatalogFragment : Fragment() {

    private val LOG_TAG = "CatalogFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        val catalogView: View = inflater.inflate(R.layout.catalog_fragment, container, false)

        getDataList()

        return catalogView
    }

    override fun onStart() {
        super.onStart()
        MainActivity.validateConnection(context)
    }

    companion object {
        fun newInstance() = CatalogFragment()
    }

    private fun getDataList() {
        val EVENT_NODE = "catalog"

        val databaseReference = FirebaseConnection.getDatabase(EVENT_NODE)
        databaseReference.addListenerForSingleValueEvent( settingListerData() )
    }

    private fun settingListerData(): ValueEventListener {
        return object: ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(LOG_TAG, "dabaseError: ${databaseError.message}, details: ${databaseError.details}")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()){

                    val eventList: MutableList<Event> = ArrayList()

                    dataSnapshot.children.forEach{
                        eventList.add(it.getValue(Event::class.java)!!)
                    }

                    populateRecyclerView(eventList)

                }
            }
        }
    }

    fun populateRecyclerView(eventList: MutableList<Event>) {

        recyclerview_eventList.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        recyclerview_eventList.adapter = EventAdapter(eventList)

    }

}
