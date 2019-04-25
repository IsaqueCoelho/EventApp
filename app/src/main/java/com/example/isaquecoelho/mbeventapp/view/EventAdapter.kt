package com.example.isaquecoelho.mbeventapp.view

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.isaquecoelho.mbeventapp.R
import com.example.isaquecoelho.mbeventapp.data.FirebaseConnection
import com.example.isaquecoelho.mbeventapp.model.Event
import kotlinx.android.synthetic.main.item_event.view.*

class EventAdapter(val eventList: List<Event>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private val LOG_TAG = "EventAdapter"

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): EventViewHolder {
        val evetnView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_event, viewGroup, false)
        return EventViewHolder(evetnView)
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    override fun onBindViewHolder(eventViewHolder: EventViewHolder, position: Int) {
        val event = eventList[position]
        eventViewHolder.setData(event)
    }


    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun setData(event: Event?){
            itemView.textview_event_title.text = event!!.title
            itemView.textview_event_schedule_response.text = event.schedule

            Glide.with(itemView.context)
                .load(FirebaseConnection.getStorage(event.image.toString()))
                .override(Target.SIZE_ORIGINAL)
                .into(itemView.imageview_event_poster)

        }
    }

}