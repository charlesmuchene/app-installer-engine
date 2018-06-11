package com.charlesmuchene.installer.models

/**
 * User actions
 */
enum class UserAction {

    ConnectWifi, AddGoogleAccount, ResetDeviceBridge;

    /**
     * Get action command
     *
     * @param values Value for the commands
     * @return Action command
     */
    fun getCommand(vararg values: String): Array<String> = when (this) {
        ConnectWifi -> Commands.connectToWifi
        ResetDeviceBridge -> Commands.resetDeviceBridge
        AddGoogleAccount -> Commands.getGoogleAccountCommand(values[0], values[1])
    }

    /**
     * To string
     */
    override fun toString(): String = getCommand("", "").reduce { a, b -> "$a $b" }

}