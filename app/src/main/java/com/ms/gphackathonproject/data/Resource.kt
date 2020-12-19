package com.gm.lollipop.data

/**
* Created by Mehedi Hasan on 12/19/20.
 * */
class Resource<T> {
    val status: Status
    val data: T?
    var message: String? = ""
    var throwable: Throwable? = null

    constructor(status: Status, data: T?, message: String?) {
        this.status = status
        this.data = data
        this.message = message
    }

    constructor(status: Status, data: T?, throwable: Throwable?) {
        this.status = status
        this.data = data
        this.throwable = throwable
    }

    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, "")
        }

        fun <T> error(msg: String?, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> error(throwable: Throwable?, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, throwable)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, "")
        }
    }
}