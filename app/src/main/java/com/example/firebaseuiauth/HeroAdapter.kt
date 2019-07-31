package com.example.firebaseuiauth

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.heros.view.*

class HeroAdapter(val mCtx: Context, val layoutResId: Int, val heroList:List<Hero>)
    :ArrayAdapter<Hero>(mCtx,layoutResId,heroList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layouInflater : LayoutInflater = LayoutInflater.from(mCtx)
        val view : View  = layouInflater.inflate(layoutResId, null)
        val hero = heroList[position]
        view.tvName.text = hero.name
        return view

    }
}