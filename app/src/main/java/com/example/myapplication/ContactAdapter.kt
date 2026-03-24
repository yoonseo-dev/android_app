package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.myapplication.databinding.ItemContactBinding

data class ContactData(
    val name: String,
    val phoneNumber: String
)

class ContactAdapter(private val contactList: List<ContactData>) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>(){
    inner class ContactViewHolder(private val binding: ItemContactBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: ContactData) {
            binding.tvName.text = contact.name
            binding.tvPhone.text = contact.phoneNumber
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val targetData = contactList[position]
        holder.bind(targetData)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }
}