package com.alish.geekbank.presentation.customviews

interface KeyPadListerner {
    fun onKeyPadPressed(value: String?)
    fun onKeyBackPressed()
    fun onClear()
}