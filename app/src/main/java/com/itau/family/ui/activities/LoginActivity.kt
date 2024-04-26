package com.itau.family.ui.activities

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import com.itau.family.R
import com.itau.family.models.User
import com.itau.family.network.UserClient
import com.itau.family.ui.common.MaskEditTextChangedListener
import com.itau.family.ui.common.hideKeyboard
import com.itau.family.ui.common.showToast

class LoginActivity : BaseActivity() {

    private var activity = this
    private lateinit var scrollView: ScrollView
    private lateinit var viewPrincipal: View
    private lateinit var txtCpf: EditText
    private lateinit var txtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var loadingLogin: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        bindControls()
        setNavigationBar()
        configTexts()
        configActions()
    }

    private fun bindControls() {
        scrollView = findViewById(R.id.scrollView)
        viewPrincipal = findViewById(R.id.viewPrincipal)
        txtCpf = findViewById(R.id.txtCpf)
        txtPassword = findViewById(R.id.txtPassword)
        btnLogin = findViewById(R.id.btnLogin)
        loadingLogin = findViewById(R.id.loadingLogin)
    }

    private fun setNavigationBar() {

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setIcon(null)
            actionBar.elevation = 0f
            val mInflater = LayoutInflater.from(this)
            val mCustomView: View? = mInflater.inflate(R.layout.view_navigation_logo, findViewById(R.id.viewRoot))
            actionBar.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(activity, R.color.principal)))
            val params = ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT
            )
            actionBar.setCustomView(mCustomView, params)
            actionBar.setDisplayShowCustomEnabled(true)
        }
    }

    private fun configTexts() {

        val arrayCel1 = arrayOfNulls<InputFilter>(1)
        arrayCel1[0] = LengthFilter(14)
        txtCpf.filters = arrayCel1
        val maskTxtCpf = MaskEditTextChangedListener("###.###.###-##", txtCpf)
        txtCpf.addTextChangedListener(maskTxtCpf)

        txtPassword.transformationMethod = PasswordTransformationMethod()
        val arrayPassword = arrayOfNulls<InputFilter>(1)
        arrayPassword[0] = LengthFilter(4)
        txtPassword.filters = arrayPassword
    }

    private fun configActions() {

        btnLogin.setOnClickListener {

            activity.hideKeyboard()

            val loginUser = User(cpf = txtCpf.text.toString(), password = txtPassword.text.toString())
            val modelValid = loginUser.validate()

            if(!modelValid.success){

                modelValid.resourceMessage?.let {
                    Toast(activity).showToast(
                        activity,
                        getString(modelValid.resourceMessage!!),
                        false
                    )
                }
            }
            else{

                btnLogin.visibility = View.INVISIBLE
                loadingLogin.visibility = View.VISIBLE

                UserClient(activity).login(
                    result = { response ->

                        btnLogin.visibility = View.VISIBLE
                        loadingLogin.visibility = View.INVISIBLE

                        if(!response.success){

                            response.resourceMessage?.let {
                                Toast(activity).showToast(
                                    activity,
                                    getString(response.resourceMessage!!),
                                    false
                                )
                            }
                            return@login
                        }

                        val intent = Intent(activity, WalletActivity::class.java)
                        activity.startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }
}