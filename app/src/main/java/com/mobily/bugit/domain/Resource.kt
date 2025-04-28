package com.mobily.bugit.domain

sealed class Resource {
    data class Success<T>(val data:T) :Resource()
    data class Error(val error:String) : Resource()
}