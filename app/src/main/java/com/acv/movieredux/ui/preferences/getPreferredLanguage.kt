package com.acv.movieredux.ui.preferences

private val javaLocale get() = java.util.Locale.getDefault()

fun getPreferredLanguage(): String = javaLocale.language.substringBefore('-')