package com.itau.family.network

import android.content.Context
import com.itau.family.models.CreditCard
import com.itau.family.models.ModelValidate
import com.itau.family.models.enuns.CardFormat
import com.itau.family.models.enuns.CardType

class UserClient(var context: Context) {

    fun login(result: ((response: ModelValidate)->Unit)){

        result(ModelValidate(true))
    }

}