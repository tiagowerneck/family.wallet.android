package com.itau.family.network

import android.content.Context
import com.itau.family.models.CreditCard
import com.itau.family.models.enuns.CardFormat
import com.itau.family.models.enuns.CardType

class CreditCardClient(var context: Context) {

    fun list(): List<CreditCard>{
        return listOf(

            CreditCard("Latam Pass", null).apply {
                id = 1
                number = "1234123412341111"
                format = CardFormat.PHYSICAL
            },
            CreditCard("Latam Pass", "Enzo").apply {
                titular = false
                id = 2
                number = "1234123412341112"
                format = CardFormat.VIRTUAL
            },
            CreditCard("Latam Pass", "Valentina").apply {
                titular = false
                id = 3
                number = "1234123412341113"
                format = CardFormat.VIRTUAL
            },
            CreditCard("Latam Pass", "Lucas").apply {
                titular = false
                id = 4
                number = "1234123412341114"
                format = CardFormat.VIRTUAL
            }
        )
    }

    fun add(tagNewCard: String){}

    fun edit(id: Int, newTag: String){}

    fun delete(id: Int){}
}