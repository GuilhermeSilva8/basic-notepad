package com.example.basicnotepad

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.basicnotepad.databinding.ResItemTextBinding

class TextListAdapter(private val onItemCLicked : (Text) -> Unit) : RecyclerView.Adapter<TextListAdapter.TextViewHolder>() {

    private var listItem = ArrayList<Text>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        return TextViewHolder(ResItemTextBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {

        holder.bind(listItem[position], onItemCLicked)

    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    class TextViewHolder(itemView: ResItemTextBinding): RecyclerView.ViewHolder(itemView.root) {

        private var text = itemView.tvText
        private val date = itemView.tvDate

        fun bind(item: Text, onItemCLicked: (Text) -> Unit) {

            text.text = item.text
            date.text = item.date

            itemView.setOnClickListener {

                onItemCLicked(item)

            }

        }

    }

    fun setDataSet(listItem: ArrayList<Text>) {
        this.listItem = listItem
    }

}