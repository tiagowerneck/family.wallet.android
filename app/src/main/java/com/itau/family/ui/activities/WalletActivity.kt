package com.itau.family.ui.activities

import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputFilter
import android.text.Spannable
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itau.family.R
import com.itau.family.models.CreditCard
import com.itau.family.models.enuns.CardActions
import com.itau.family.network.CreditCardClient
import com.itau.family.ui.adapters.WalletAdapter
import com.itau.family.ui.common.CustomTypefaceSpan
import com.itau.family.ui.common.hideKeyboard

class WalletActivity : BaseActivity() {

    private var activity = this
    private lateinit var listCards: RecyclerView
    private lateinit var adapter: WalletAdapter
    private lateinit var btnAddCard: Button
    private lateinit var viewPopUp: View
    private lateinit var lblTitle: TextView
    private lateinit var lblDescription: TextView
    private lateinit var txtDescription: EditText
    private lateinit var btnActionPrimary: Button
    private lateinit var btnActionSecond: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)
        bindControls()
        setNavigationBar()
        configTexts()
        configActions()
        listCards()
    }

    private fun bindControls() {

        listCards = findViewById(R.id.listCards)
        btnAddCard = findViewById(R.id.btnAddCard)
        viewPopUp = findViewById(R.id.viewPopUp)
        lblTitle = findViewById(R.id.lblTitle)
        lblDescription = findViewById(R.id.lblDescription)
        txtDescription = findViewById(R.id.txtDescription)
        btnActionPrimary = findViewById(R.id.btnActionPrimary)
        btnActionSecond = findViewById(R.id.btnActionSecond)
    }

    private fun setNavigationBar() {

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setIcon(null)
            actionBar.elevation = 0f
            val mInflater = LayoutInflater.from(this)
            val mCustomView = mInflater.inflate(R.layout.view_navigation_logout, findViewById(R.id.viewRoot))
            actionBar.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(activity, R.color.white)))
            val params = ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT
            )
            mCustomView.findViewById<Button>(R.id.btnLogout).apply {
                setOnClickListener{
                    finish()
                }
            }
            mCustomView.findViewById<TextView>(R.id.lblTitle).text = this.title

            actionBar.setCustomView(mCustomView, params)
            actionBar.setDisplayShowCustomEnabled(true)
        }
    }

    private fun configTexts() {

        val arrayCel1 = arrayOfNulls<InputFilter>(1)
        arrayCel1[0] = InputFilter.LengthFilter(20)
        txtDescription.filters = arrayCel1
    }

    private fun configActions() {

        btnAddCard.setOnClickListener {
            showActionPopup(CardActions.ADD)
        }

        btnActionSecond.setOnClickListener {
            activity.hideKeyboard()
            viewPopUp.visibility = View.GONE
        }
    }

    private fun listCards(){
        adapter = WalletAdapter(CreditCardClient(activity).list())
        listCards.layoutManager = LinearLayoutManager(activity)
        listCards.adapter = adapter
    }

    fun showActionPopup(action: CardActions, item: CreditCard? = null){

        viewPopUp.visibility = View.VISIBLE
        txtDescription.setText("")

        when (action) {
            CardActions.ADD -> {
                configActionAdd()
            }
            CardActions.EDIT -> {
                item?.let{
                    configActionEdit(item)
                }
            }
            CardActions.DELETE -> {
                item?.let{
                    configActionDelete(item)
                }
            }
        }
    }

    private fun configActionAdd(){

        lblDescription.visibility = View.GONE
        lblTitle.text = getString(R.string.wallet_lbl_add_title)
        txtDescription.visibility = View.VISIBLE
        btnActionPrimary.apply {
            text = getString(R.string.wallet_btn_add)
            setOnClickListener {
                activity.hideKeyboard()
                CreditCardClient(activity).add(txtDescription.text.toString())
                viewPopUp.visibility = View.GONE
            }
        }
    }

    private fun configActionEdit(item: CreditCard){

        lblDescription.visibility = View.GONE
        txtDescription.visibility = View.VISIBLE
        lblTitle.text = getString(R.string.wallet_lbl_edit_title)
        item.tag?.let{
            txtDescription.setText(item.tag)
        }

        btnActionPrimary.apply {
            text = getString(R.string.wallet_btn_save)
            setOnClickListener {
                activity.hideKeyboard()
                CreditCardClient(activity).edit(item.id, txtDescription.text.toString())
                viewPopUp.visibility = View.GONE
            }
        }
    }

    private fun configActionDelete(item: CreditCard){

        lblDescription.visibility = View.VISIBLE
        txtDescription.visibility = View.GONE
        lblTitle.text = getString(R.string.wallet_lbl_delete_title)
        item.tag?.let{

            val spannableDelete = SpannableString(String.format(getString(R.string.wallet_lbl_delete_description), item.brandTag))
            spannableDelete.setSpan(
                CustomTypefaceSpan(
                    "", Typeface.createFromAsset(
                        assets, getString(R.string.font_p4)
                    )
                ), 14, 14 + item.brandTag.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            lblDescription.text = spannableDelete
        }

        btnActionPrimary.apply {
            text = getString(R.string.wallet_btn_delete)
            setOnClickListener {
                activity.hideKeyboard()
                CreditCardClient(activity).delete(item.id)
                viewPopUp.visibility = View.GONE
            }
        }
    }
}