package com.itau.family.models

import android.content.res.Resources
import com.itau.family.R
import com.itau.family.utils.ValidationHelper

class User(
    var id: Int = 0,
    var cpf: String,
    var password: String
) {

    fun validate(): ModelValidate {

        if (cpf.isEmpty()) {
            return ModelValidate(false, R.string.login_msg_cpf_required);
        }
        else if (!ValidationHelper.validateCpf(cpf)) {
            return ModelValidate(false, R.string.login_msg_cpf_invalid)
        }
        else if (password.isEmpty()) {
            return ModelValidate(false, R.string.login_msg_password_required)
        }
        else if (password.length < 4) {
            return ModelValidate(false, R.string.login_msg_password_invalid)
        }

        return ModelValidate(true)
    }


}
