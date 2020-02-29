package com.yazan98.data.database

interface DatabaseMapper<From, To> {

    fun get(from: From): To

}