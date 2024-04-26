package com.itau.family.ui.common

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class MaskEditTextChangedListener(maskFormat: String, text: EditText) : TextWatcher {

    private val instance: MaskEditTextChangedListener? = null
    private var mask = maskFormat
    private var mEditText = text
    private val symbolMask: MutableSet<String> = HashSet()
    private var isUpdating = false
    private var old = ""

    init {
        initSymbolMask()
    }

    private fun initSymbolMask() {
        for (i in 0 until mask!!.length) {
            val ch = mask!![i]
            if (ch != '#') symbolMask.add(ch.toString())
        }
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        val str: String = unmask(s.toString(), symbolMask)
        var mascara = ""
        if (isUpdating) {
            old = str
            isUpdating = false
            return
        }
        mascara = if (str.length > old.length) mask(mask, str) else s.toString()
        isUpdating = true
        mEditText!!.setText(mascara)
        mEditText!!.setSelection(mascara.length)
    }

    fun unmask(s: String, replaceSymbols: Set<String>): String {
        var s = s
        for (symbol in replaceSymbols) s = s.replace("[$symbol]".toRegex(), "")
        return s
    }

    fun mask(format: String, text: String): String {
        var maskedText: String = ""
        var i = 0
        for (m in format.toCharArray()) {
            if (m != '#') {
                maskedText += m
                continue
            }
            maskedText += try {
                text[i]
            } catch (e: Exception) {
                break
            }
            i++
        }
        return maskedText
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun afterTextChanged(s: Editable?) {
    }
}