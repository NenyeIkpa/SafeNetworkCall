package com.example.safenetworkcall


import java.lang.NumberFormatException

private val NAMING_PATTERN = Regex("^[A-Z]{1}[a-z]{2,}\$")
private var EMAIL_PATTERN = Regex("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+\$")
private val PHONE_PATTERN = Regex("^(?:234|0)([7-9][0-1])[1-9].......\$")
private val PASSWORD_PATTERN = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\da-zA-Z]).{8,15}\$")

fun validateCompanySelection(companyId: String): Boolean {
    if (companyId.isNullOrEmpty()){
        return false
    }
    return true
}
fun validateFirstNameInput(firstName: String): Boolean {
    if (firstName.length < 2 || !firstName.contains(NAMING_PATTERN)) {
        return false
    }
    return true
}
fun validateLastNameInput(lastName: String): Boolean {
    if (lastName.length < 2 || !lastName.contains(NAMING_PATTERN)) {
        return false
    }
    return true
}
fun validateAgeInput(age: Int): Int {
        if (age == 0)
            return 1
        else if ( age < 17)
            return 2
        if (age > 99) {
            return 3
        }
    return 0
}
fun validateEmailInput(email: String): Boolean {
    if (email.isEmpty() || !email.matches(EMAIL_PATTERN)) {
        return false
    }
    return true
}
fun validatePasswordInput(password: String): Boolean {
    if (!password.matches(PASSWORD_PATTERN)) {
        return false
    }
    return true
}
fun validateConfirmPassword(password: String, confirmPassword: String): Boolean {
    if (password != confirmPassword) {
        return false
    }
    return true
}
fun validatePhoneInput(phone: String): Boolean {
    if (phone.isEmpty() || !phone.matches(PHONE_PATTERN)) {
        return false
    }
    return true
}
fun validateSex(gender: String): Boolean {
    return when (gender) {
        "Male" -> true
        "Female" -> true
        else -> false
    }
}


