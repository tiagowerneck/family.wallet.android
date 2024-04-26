package com.itau.family.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itau.family.R
import com.itau.family.models.CreditCard
import com.itau.family.models.enuns.CardActions
import com.itau.family.ui.activities.WalletActivity
import com.itau.family.ui.common.margin

class WalletAdapter(
    private val items: List<CreditCard>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var activity: WalletActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wallet_card, parent, false)

        activity = parent.context as WalletActivity

        if (viewType == 1) {
            return AdditionalViewHolder(view)
        }

        return TitularViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (items[position].titular && holder is TitularViewHolder) {
            holder.bind(items[position], position)
        } else if (holder is AdditionalViewHolder) {
            holder.bind(items[position])
        }
    }

    override fun getItemViewType(position: Int): Int {

        return if (!items[position].titular) {
            1
        } else {
            0
        }
    }

    override fun getItemCount(): Int = items.size

    inner class TitularViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: CreditCard, position: Int) {

            itemView.findViewById<TextView>(R.id.lblTitle).visibility = if (position > 0) View.GONE else View.VISIBLE

            itemView.findViewById<ImageView>(R.id.imgCard).setImageResource(R.drawable.card)
            itemView.findViewById<TextView>(R.id.lblName).apply {
                textSize = 14F
                setTextColor(activity.getColor(R.color.black))
                text = item.brand
            }
            itemView.findViewById<TextView>(R.id.lblNumber).text = item.getMaskedNumber()
            itemView.findViewById<ImageButton>(R.id.btnOptions).visibility = View.GONE

            itemView.findViewById<View>(R.id.viewActions).visibility = View.GONE
        }
    }

    inner class AdditionalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: CreditCard) {

            itemView.findViewById<TextView>(R.id.lblTitle).visibility = View.GONE
            itemView.findViewById<View>(R.id.viewActions).visibility = View.GONE

            itemView.findViewById<ImageView>(R.id.imgCard).setImageResource(R.drawable.card_add)
            itemView.findViewById<TextView>(R.id.lblName).apply {
                textSize = 12F
                itemView.findViewById<TextView>(R.id.lblName)
                    .setTextColor(activity.getColor(R.color.text4))
                itemView.findViewById<TextView>(R.id.lblName).text = item.brandTag
            }
            itemView.findViewById<TextView>(R.id.lblNumber).text = item.getMaskedNumber()
            itemView.findViewById<ImageButton>(R.id.btnOptions).apply {
                visibility = View.VISIBLE
                setOnClickListener{
                    showHideActions()
                }
            }

            itemView.findViewById<ImageButton>(R.id.btnEdit).apply {
                setOnClickListener{
                    showHideActions()
                    activity.showActionPopup(CardActions.EDIT, item)
                }
            }

            itemView.findViewById<ImageButton>(R.id.btnDelete).apply {
                setOnClickListener{
                    showHideActions()
                    activity.showActionPopup(CardActions.DELETE, item)
                }
            }
        }

        private fun showHideActions(){

            itemView.findViewById<View>(R.id.viewActions).apply {

                if(visibility == View.VISIBLE){

                    visibility = View.GONE
                    itemView.findViewById<View>(R.id.viewCard).margin(24F, 16F, 24F, 8F)
                }
                else{
                    visibility = View.VISIBLE
                    itemView.findViewById<View>(R.id.viewCard).margin(24F, 16F, 108F, 8F)
                }
            }
        }
    }
}