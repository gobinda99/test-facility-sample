package com.gobinda.facilities.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gobinda.facilities.R
import com.gobinda.facilities.data.model.Facility
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.nav_item.*
import timber.log.Timber

class NavFacilityAdapter(
    facilities: List<Facility>,
    private val itemClick: (Facility) -> Unit = {}
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var mutableList: MutableList<Facility> = facilities.toMutableList()

    fun addItem(news: List<Facility>, clear: Boolean = false) {
        if (clear) {
            mutableList.clear()
        }
        mutableList.addAll(news.toMutableList())
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return inflate.inflate(R.layout.nav_item, parent, false)
            .run {
                ItemViewHolder(this, itemClick)

            }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).run {
            bind(mutableList.get(position))
        }

    }


    override fun getItemCount() = mutableList.size


    inner class ItemViewHolder(override val containerView: View, private val itemClick: (Facility) -> Unit) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(facility: Facility) {
            with(facility) {
                text_nav.text = facility.name
                if(selected) {
                    text_nav.setTextColor(Color.RED)
                }else {
                    text_nav.setTextColor(Color.BLACK)
                }
                itemView.setOnClickListener { itemClick(this)
                refreshTheItem(this)}

            }
        }
    }

    private fun refreshTheItem(facility: Facility) {
        mutableList.map { if(it == facility) {
            if(!it.selected)
            it.selected = !facility.selected
        } else{
            it.selected = false
        }}
        notifyDataSetChanged()
    }


}