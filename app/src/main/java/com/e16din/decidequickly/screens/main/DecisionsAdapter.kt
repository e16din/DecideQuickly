package com.e16din.decidequickly.screens.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.e16din.decidequickly.R
import com.e16din.decidequickly.data.Decision
import kotlinx.android.synthetic.main.item_decision.view.*


class DecisionsAdapter(val items: ArrayList<Decision> = ArrayList())
    : RecyclerView.Adapter<DecisionsAdapter.DecisionVH>() {

    override fun onCreateViewHolder(vParent: ViewGroup, type: Int): DecisionVH {
        val view = LayoutInflater.from(vParent.context)!!
                .inflate(R.layout.item_decision, vParent, false)
        return DecisionVH(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: DecisionVH, position: Int) {
        val decision = items[position]
        holder.vDecisionTextLabel.text = decision.text
        when (decision.state) {
            Decision.State.New ->
                holder.vDecisionResultImage.setImageDrawable(null)
            Decision.State.Accepted ->
                holder.vDecisionResultImage.setImageResource(R.drawable.ic_check_circle_black_24dp)
            Decision.State.Rejected ->
                holder.vDecisionResultImage.setImageResource(R.drawable.ic_cancel_black_24dp)
        }
    }

    class DecisionVH(vItem: View) : RecyclerView.ViewHolder(vItem) {
        val vDecisionTextLabel: TextView = vItem.vDecisionTextLabel
        val vDecisionResultImage: ImageView = vItem.vDecisionResultImage
    }
}