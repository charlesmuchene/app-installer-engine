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
     * @param properties Argument list
     * @return An array representing argument
     */
    fun getArgument(properties: Properties): Array<String> {
        val value = properties.getProperty(getKey())
        return getArgument(value)
    }

    /**
     * Get parsed app argument given a value
     *
     * @param value Argument value
     */
    fun getArgument(value: String): Array<String> {
        val key = getKey()
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
     * @return Key corresponding to the argument
     */
    private fun getKey() = when (this) {
        NETWORK_SSID -> "network_ssid"
        ACCOUNT_EMAIL -> "account_email"
        NETWORK_PASSWORD -> "network_password"
        ACCOUNT_PASSWORD -> "account_password"
    }

}