package com.charlesmuchene.installer

class Application {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Runner().runUserAction(UserActions.ConnectWifi)
        }
    }

}