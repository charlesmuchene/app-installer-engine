package com.charlesmuchene.installer

/**
 * User actions
 */
enum class UserActions {

    ConnectWifi, AddAccount, InstallApp;

    /**
     * Get action command
     *
     * @return Action command
     */
    fun getCommand(): Array<String> = when (this) {
        ConnectWifi -> Commands.connectToWifi
        AddAccount -> Commands.addAccount
        InstallApp -> Commands.installApp
    }

}