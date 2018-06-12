package com.charlesmuchene.installer.models

import com.charlesmuchene.installer.utils.reduce

/**
 * User actions
 */
enum class UserAction {

    ConnectWifi, AddGoogleAccount, ResetDeviceBridge, LaunchApp, OptimizeBattery;

    /**
     * Get action command
     *
     * @param values Value for the commands
     * @return Action command
     */
    fun getCommand(vararg values: String): Array<String> = when (this) {
        LaunchApp -> Commands.launchApp
        ConnectWifi -> Commands.connectToWifi
        OptimizeBattery -> Commands.optimizeBattery
        ResetDeviceBridge -> Commands.resetDeviceBridge
        AddGoogleAccount -> Commands.getGoogleAccountCommand(values[0], values[1])
    }

    override fun toString(): String = getCommand("email", "password").reduce()

}