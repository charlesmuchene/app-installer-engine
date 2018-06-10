package com.charlesmuchene.installer.utils

import java.util.*

/**
 * Application arguments
 */
enum class Argument {

    NETWORK_SSID, NETWORK_PASSWORD, ACCOUNT_EMAIL, ACCOUNT_PASSWORD;

    /**
     * Get fully parsed app argument
     *
     * @param properties Argument
     * @return An array representing argument
     */
    fun getArguments(properties: Properties): Array<String> {
        val key = getKey(this)
        val value = properties.getProperty(key)
        return when (this) {
            NETWORK_SSID -> arrayOf("-e", key, value)
            ACCOUNT_EMAIL -> arrayOf("-e", key, value)
            NETWORK_PASSWORD -> arrayOf("-e", key, value)
            ACCOUNT_PASSWORD -> arrayOf("-e", key, value)
        }
    }

    /**
     * Get the key for the given [Argument]
     *
     * @param argument [Argument] instance
     * @return Key corresponding to the argument
     */
    private fun getKey(argument: Argument) = when (argument) {
        NETWORK_SSID -> "network_ssid"
        ACCOUNT_EMAIL -> "account_email"
        NETWORK_PASSWORD -> "network_password"
        ACCOUNT_PASSWORD -> "account_password"
    }
}