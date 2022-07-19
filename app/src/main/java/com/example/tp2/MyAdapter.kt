package com.example.tp2

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

interface MyAdapterInterface {
    fun onClickItem(id :String)
}

class MyAdapter(private val livreList: ArrayList<Livre>, val myAdapterInterface :MyAdapterInterface) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.livres_item,
            parent, false
        )
        return MyViewHolder(itemView)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = livreList[position]

       Picasso.get().load(currentitem.path).into(holder.image)

        holder.name.text = currentitem.name
        holder.author.text = "Auteur : ".plus(currentitem.author)
        if (currentitem.isRead) {
            holder.relativeLayout.setBackgroundResource(R.drawable.background_border_green)
        } else {
            holder.relativeLayout.setBackgroundResource(R.drawable.background_border_red)
        }

        holder.relativeLayout.setOnClickListener {
            myAdapterInterface.onClickItem(currentitem.id)
        }
    }

    override fun getItemCount(): Int {
        return livreList.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageBooks)
        val author: TextView = itemView.findViewById(R.id.bookAuthor)
        val name: TextView = itemView.findViewById(R.id.bookName)
        val relativeLayout :RelativeLayout = itemView.findViewById(R.id.relativeLayoutItem)
    }


}