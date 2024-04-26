package com.itau.family.models

import com.itau.family.models.enuns.CardFormat
import com.itau.family.models.enuns.CardType

open class Card(
    var id: Int = 0,
    var name: String = "",
    var number: String = "",
    var type: CardType,
    var format: CardFormat = CardFormat.PHYSICAL,
    var titular: Boolean = true
) {
    fun getMaskedNumber() : String {

        if(number.length > 4){
            return "**** " + number.substring(number.length-4,number.length);
        }

        return ""
    }
}