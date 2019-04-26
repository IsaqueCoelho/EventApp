package com.example.isaquecoelho.mbeventapp.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.isaquecoelho.mbeventapp.R
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

        return eventDetailView
    }

    override fun onStart() {
        super.onStart()
        settingToolbarMovie()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onOptionsItemSelected(item)
        if( item!!.itemId == android.R.id.home ){

            Log.e(LOG_TAG, "close fragment")
            (context as MainActivity).onBackPressed()
        }
        return true
    }

    private fun settingToolbarMovie() {
        (context as MainActivity).setSupportActionBar(toolbar_event)
        (context as MainActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (context as MainActivity).supportActionBar!!.setDisplayShowHomeEnabled(true)
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
