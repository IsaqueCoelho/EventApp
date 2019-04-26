package com.example.isaquecoelho.mbeventapp.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.isaquecoelho.mbeventapp.R
import com.example.isaquecoelho.mbeventapp.data.FirebaseConnection
import com.example.isaquecoelho.mbeventapp.model.Event
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.catalog_fragment.*

class CatalogFragment : Fragment() {

    private val LOG_TAG = "CatalogFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        val catalogView: View = inflater.inflate(R.layout.catalog_fragment, container, false)

        //settingToolbarMovie()
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

    private fun settingToolbarMovie() {
        (context as MainActivity).setSupportActionBar(null)
        (context as MainActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        (context as MainActivity).supportActionBar!!.setDisplayShowHomeEnabled(false)
        (context as MainActivity).supportActionBar!!.title = null
    }

    private fun getDataList() {
        val eventNode = "catalog"

        val databaseReference = FirebaseConnection.getDatabase(eventNode)
        databaseReference.addListenerForSingleValueEvent( settingListerData() )
    }

    private fun settingListerData(): ValueEventListener {
        return object: ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, getString(R.string.error_firebase_getdata), Toast.LENGTH_LONG).show()
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

        recyclerview_eventList.adapter = EventAdapter(eventList, object: EventAdapter.OnItemClickListener{
            override fun onItemClick(eventId: String) {
                (context as MainActivity).settingFragment(EventDetailsFragment.newInstance(eventId))
            }
        } )
    }

}
