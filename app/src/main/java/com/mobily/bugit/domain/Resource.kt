package com.mobily.bugit.domain

sealed class Resource {
    data class Success<T>(val data:T?=null) :Resource()
    data class Error(val error:String) : Resource()
}