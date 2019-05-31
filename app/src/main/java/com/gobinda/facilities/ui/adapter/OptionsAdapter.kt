package com.gobinda.facilities.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gobinda.facilities.R
import com.gobinda.facilities.data.api.Option
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.nav_item.*
import timber.log.Timber

class OptionsAdapter(
    options: List<Option>,
    private val itemClick: (Option) -> Unit = {}
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var mutableList: MutableList<Option> = options.toMutableList()

    fun addItem(news: List<Option>, clear: Boolean = false) {
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


    inner class ItemViewHolder(override val containerView: View, private val itemClick: (Option) -> Unit) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
//            rightIcon.toIcon(R.string.fa_angle_right)
        }

        fun bind(option: Option) {
            Timber.d(" FF ")
            with(option) {
                text_nav.text = option.name
                if(selected) {
                    text_nav.setTextColor(Color.RED)
                }else if(disabled) {
                    text_nav.setTextColor(Color.LTGRAY)
                }else {
                    text_nav.setTextColor(Color.BLACK)
                }
                itemView.setOnClickListener {
                    itemClick(this)
                    refreshTheItem(option)
                }
            }
        }
    }

    private fun refreshTheItem(option: Option) {
        mutableList.map { if(it == option) {
            it.selected = !option.selected
        } else{
            it.selected = false
        }}
        notifyDataSetChanged()
    }



}