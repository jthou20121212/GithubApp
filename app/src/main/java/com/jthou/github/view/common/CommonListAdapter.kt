package com.jthou.github.view.common

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.jthou.github.R
import com.jthou.github.utils.AdapterList
import kotlinx.android.synthetic.main.item_card.view.*
import splitties.dimensions.dip
import splitties.views.onClick

abstract class CommonListAdapter<T>(@LayoutRes val itemLayoutRes: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val CARD_TAP_DURATION = 100L
    }

    init {
        setHasStableIds(true)
    }

    private var oldPosition = RecyclerView.NO_POSITION
    val data = AdapterList<T>(this)

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemCount() : Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_card, parent, false)
        layoutInflater.inflate(itemLayoutRes, itemView.contentContainer, true)
        return CommonViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindData(holder, data[position])

        holder.itemView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> ViewCompat.animate(holder.itemView).scaleX(1.03f).scaleY(1.03f).translationZ(holder.itemView.dip(10).toFloat()).duration = CARD_TAP_DURATION
                MotionEvent.ACTION_UP,
                MotionEvent.ACTION_CANCEL -> {
                    ViewCompat.animate(holder.itemView).scaleX(1f).scaleY(1f).translationZ(holder.itemView.dip(0).toFloat()).duration = CARD_TAP_DURATION
                }
            }
            false
        }

        holder.itemView.onClick {
            onItemClicked(holder.itemView, data[position])
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        if(holder is CommonViewHolder && holder.layoutPosition > oldPosition){
            addItemAnimation(holder.itemView)
            oldPosition = holder.layoutPosition
        }
    }

    private fun addItemAnimation(itemView: View) {
        ObjectAnimator.ofFloat(itemView, "translationY", 500f, 0f).setDuration(500).start()
    }

    abstract fun onBindData(viewHolder: RecyclerView.ViewHolder, item: T)

    abstract fun onItemClicked(itemView: View, item: T)

    class CommonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
