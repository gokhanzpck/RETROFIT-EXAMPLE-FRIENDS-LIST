package com.gokhanzopcuk.retrofitappyt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyAdapter(var con : Context , var list : List<UsersItem>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    fun setFilteredList(kisilerListesi: List<UsersItem>){
        this.list = kisilerListesi
        notifyDataSetChanged()
        //recyler viewi sürekli yenilenmesini saglıyor
    }


    inner class ViewHolder(v : View) : RecyclerView.ViewHolder(v)
    {
        var img = v.findViewById<ImageView>(R.id.RV_Image)
        var tvName = v.findViewById<TextView>(R.id.RV_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(con).inflate(R.layout.list_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(con).load(list[position].avatar_url).into(holder.img)
//Glide=Glide Android işletim sistemi için Bump Technologies tarafından geliştirilmiş resim, gif ve yerel video’ları kolayca uygulamamıza dahil etmek, memory ve disk cache gibi olayları düşünmeden hızlıca uygulama geliştirmek için geliştirilmiş açık kaynak bir kütüphanedir.
        holder.tvName.text = list[position].login

    }

    override fun getItemCount(): Int {
        return list.count()
    }

}