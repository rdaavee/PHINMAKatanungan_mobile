package com.example.phinmakatanungan_mobile.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.phinmakatanungan_mobile.R
import com.example.phinmakatanungan_mobile.models.Announcement

class AnnouncementsAdapter(private var announcementsMap: Map<String, List<Announcement>> = emptyMap()) :
    RecyclerView.Adapter<AnnouncementsAdapter.AnnouncementsViewHolder>() {

    private var currentDepartment: String? = ""

    @SuppressLint("NotifyDataSetChanged")
    fun setAnnouncementsMap(announcementsMap: Map<String, List<Announcement>>) {
        this.announcementsMap = announcementsMap
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCurrentDepartment(departmentID: String?) {
        currentDepartment = departmentID
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncementsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_announcements, parent, false)
        return AnnouncementsViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnnouncementsViewHolder, position: Int) {
        if (currentDepartment.isNullOrEmpty()) {
            val allAnnouncements = announcementsMap.values.flatten()
            val announcements = allAnnouncements[position]
            holder.bind(announcements)
        } else {
            val Announcementss = announcementsMap[currentDepartment ?: ""] ?: return
            val announcements = Announcementss[position]
            holder.bind(announcements)
        }
    }

    override fun getItemCount(): Int {
        return if (currentDepartment.isNullOrEmpty()) {
            val allAnnouncements = announcementsMap.values.flatten()
            allAnnouncements.size
        } else {
            announcementsMap[currentDepartment ?: ""]?.size ?: 0
        }
    }

    inner class AnnouncementsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_header)
        private val contentTextView: TextView = itemView.findViewById(R.id.tv_content)

        fun bind(announcements: Announcement) {

            titleTextView.text = announcements.title
            contentTextView.text = announcements.content
        }
    }
}