package com.itau.family.utils

class ValidationHelper {

    companion object {

        fun validateCpf(cpf: String): Boolean {

            val cpfFormated = cpf.replace(".", "").replace("-", "").trim { it <= ' ' }
            if (cpfFormated.length != 11) return false
            var lastNumber = Character.MIN_VALUE
            var equalNumbers = true
            for (numero in cpfFormated.toCharArray()) {
                if (lastNumber != Character.MIN_VALUE &&
                    lastNumber != numero
                ) {
                    equalNumbers = false
                    break
                }
                lastNumber = numero
            }
            if (equalNumbers) {
                return false
            }
            try {
                cpfFormated.toLong()
            } catch (e: NumberFormatException) {
                return false
            }
            return calculateVerificationDigit(cpfFormated.substring(0, 9)) == cpfFormated.substring(9, 11)
        }

        private fun calculateVerificationDigit(num: String): String {
            val primDig: Int
            val segDig: Int
            var sum = 0
            var peso = 10
            for (i in 0 until num.length) sum += num.substring(i, i + 1).toInt() * peso--
            primDig = if ((sum % 11 == 0) or (sum % 11 == 1)) 0 else 11 - sum % 11
            sum = 0
            peso = 11
            for (i in 0 until num.length) sum += num.substring(i, i + 1).toInt() * peso--
            sum += primDig * 2
            segDig = if ((sum % 11 == 0) or (sum % 11 == 1)) 0 else 11 - sum % 11
            return primDig.toString() + segDig.toString()
        }
    }




}