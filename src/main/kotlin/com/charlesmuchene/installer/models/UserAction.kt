package com.charlesmuchene.installer.models

/**
 * User actions
 */
enum class UserAction {

    ConnectWifi, AddGoogleAccount, ResetDeviceBridge;

    /**
     * Get action command
     *
     * @return Action command
     */
    fun getCommand(): Array<String> = when (this) {
        ConnectWifi -> Commands.connectToWifi
        AddGoogleAccount -> Commands.addGoogleAccount
        ResetDeviceBridge -> Commands.resetDeviceBridge
    }

}