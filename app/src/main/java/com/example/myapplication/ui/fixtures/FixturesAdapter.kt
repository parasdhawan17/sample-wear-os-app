package com.example.myapplication.ui.fixtures

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.datamodels.Fixtures
import com.example.myapplication.setCountryFlag
import com.example.myapplication.twoDigit
import com.jwang123.flagkit.FlagKit
import java.lang.Exception
import java.nio.channels.CompletionHandler

class FixturesAdapter(val list: List<Fixtures.Result>,private val onItemClickListener: (result: Fixtures.Result) -> Unit) :
    RecyclerView.Adapter<FixturesAdapter.ViewHolder>() {
    class ViewHolder(itemView: View,private val onItemClickListener: (result: Fixtures.Result) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.tvTitle)
        private val ivLeft = itemView.findViewById<ImageView>(R.id.ivLeft)
        private val ivRight = itemView.findViewById<ImageView>(R.id.ivRight)
        fun bind(result: Fixtures.Result) {
            title.text = result.home.name + " vs " + result.away.name
            itemView.setOnClickListener {
                onItemClickListener(result)
            }
            setCountryFlag(itemView.context,ivLeft,result.home.code)
            setCountryFlag(itemView.context,ivRight,result.away.code)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.fixtures_list_item, parent, false)
        return ViewHolder(itemView,onItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}