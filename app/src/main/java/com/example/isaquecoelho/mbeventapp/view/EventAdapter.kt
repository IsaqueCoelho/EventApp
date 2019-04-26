package com.example.isaquecoelho.mbeventapp.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.isaquecoelho.mbeventapp.R
import com.example.isaquecoelho.mbeventapp.data.FirebaseConnection
import com.example.isaquecoelho.mbeventapp.model.Event
import com.firebase.ui.storage.images.FirebaseImageLoader
import kotlinx.android.synthetic.main.item_event.view.*


class EventAdapter(
    private val eventList: List<Event>,
    private val mOnItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

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

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var eventId: String? = null

        override fun onClick(v: View?) {
            mOnItemClickListener.onItemClick(eventId.toString())
        }

        fun setData(event: Event?){

            eventId = event!!.id

            itemView.textview_event_title.text = event!!.title
            itemView.textview_event_schedule_response.text = event.schedule

            Glide.with(itemView.context)
                .using(FirebaseImageLoader())
                .load(FirebaseConnection.getStorage(event.image.toString()))
                .into(itemView.imageview_event_poster)

            itemView.setOnClickListener(this)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(eventId: String)
    }

}