package com.example.isaquecoelho.mbeventapp.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.isaquecoelho.mbeventapp.R
import com.example.isaquecoelho.mbeventapp.data.FirebaseConnection
import com.example.isaquecoelho.mbeventapp.model.Event
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_event_details.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_EVENT_ID = "eventId"

/**
 * A simple [Fragment] subclass.
 * Use the [EventDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EventDetailsFragment : Fragment() {

    private val LOG_TAG = "EventDetailsFragment"

    // TODO: Rename and change types of parameters
    private var mEventId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mEventId = it.getString(ARG_EVENT_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val eventDetailView = inflater.inflate(R.layout.fragment_event_details, container, false)
        getDataEvent()
        return eventDetailView
    }

    override fun onStart() {
        super.onStart()
        settingToolbarMovie()
    }

    private fun settingToolbarMovie() {
        (context as MainActivity).setSupportActionBar(toolbar_event)
        (context as MainActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (context as MainActivity).supportActionBar!!.setDisplayShowHomeEnabled(true)
        (context as MainActivity).supportActionBar!!.title = ""
    }

    private fun getDataEvent(){
        val eventNode = "event"

        val databaseReference = FirebaseConnection.getDatabase(eventNode)
        databaseReference.addListenerForSingleValueEvent( settingValueListener() )
    }

    private fun settingValueListener(): ValueEventListener {

        return object : ValueEventListener{
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, getString(R.string.error_firebase_getdata), Toast.LENGTH_LONG).show()
                Log.e(LOG_TAG, "dabaseError: ${databaseError.message}, details: ${databaseError.details}")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()){

                    dataSnapshot.children.forEach{
                        if( it.key.equals(mEventId) ){
                            settingViews( it.getValue(Event::class.java) )
                        }
                    }
                }
            }
        }
    }

    private fun settingViews(event: Event?) {

        Glide.with(this.context!!)
            .using(FirebaseImageLoader())
            .load(FirebaseConnection.getStorage(event!!.image.toString()))
            .into(imageview_event_banner)

        textview_event_title.text = event.title
        textview_event_category_response.text = event.category!!.joinToString()
        textview_event_schedule_response.text = event.schedule
        textview_event_price_response.text = event.price
        textview_event_address_response.text = event.address
        textview_event_overview_response.text = event.overview

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param eventId Parameter 1.
         * @return A new instance of fragment EventDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(eventId: String) =
            EventDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_EVENT_ID, eventId)
                }
            }
    }
}
