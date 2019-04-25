package com.example.isaquecoelho.mbeventapp.utils

import java.security.MessageDigest

class StringUtils {

    fun passwordHash(text: String): String{
        val textBytes = text.toByteArray()
        val textmessageDigest = MessageDigest.getInstance("SHA-256")
        val textDigest = textmessageDigest.digest(textBytes)
        return textDigest.fold("", { str, it -> str + "%02x".format(it) }).toUpperCase()
    }
}