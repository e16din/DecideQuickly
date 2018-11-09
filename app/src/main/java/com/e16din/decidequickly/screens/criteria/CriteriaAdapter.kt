package com.e16din.decidequickly.screens.criteria

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import com.e16din.decidequickly.R
import com.e16din.decidequickly.data.Criterion
import kotlinx.android.synthetic.main.item_criterion.view.*


class CriteriaAdapter(val items: ArrayList<Criterion> = ArrayList())
    : RecyclerView.Adapter<CriteriaAdapter.CriterionVH>() {

    override fun onCreateViewHolder(vParent: ViewGroup, type: Int): CriterionVH {
        val view = LayoutInflater.from(vParent.context)!!
                .inflate(R.layout.item_criterion, vParent, false)
        return CriterionVH(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CriterionVH, position: Int) {
        val criterion = items[position]
        holder.vCriterionField.setText(criterion.text)
        holder.vCriterionSelector.isChecked = criterion.checked
    }

    class CriterionVH(vItem: View) : RecyclerView.ViewHolder(vItem) {
        val vCriterionField: EditText = vItem.vCriterionField
        val vCriterionSelector: CheckBox = vItem.vCriterionSelector
    }
}