package com.example.movie2you.utils

open class Command {
    class Loading(val value: Boolean): Command()
    class Error(val error: String?): Command()
}