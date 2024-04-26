package com.itau.family.models

import com.itau.family.AppApplication
import com.itau.family.R
import com.itau.family.models.enuns.CardFormat
import com.itau.family.models.enuns.CardType

class CreditCard(
    var brand: String,
    var tag: String?): Card(
    type = CardType.CREDIT) {

    val brandTag: String get() {
        return String.format(AppApplication.applicationContext().getString(R.string.wallet_lbl_card_title),
            this.brand,
            this.tag)
    }
}