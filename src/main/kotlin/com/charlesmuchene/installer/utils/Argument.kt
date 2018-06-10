package com.charlesmuchene.installer.utils

import java.util.*

/**
 * Application arguments
 */
enum class Arguments {

    NETWORK_SSID, NETWORK_PASSWORD, ACCOUNT_EMAIL, ACCOUNT_PASSWORD;

    /**
     * Get fully parsed app argument
     *
     * @param properties Arguments
     * @return An array representing argument
     */
    @Throws()
    fun getArguments(properties: Properties): Array<String> {
        return when (this) {
            NETWORK_SSID -> arrayOf("-e", "network_ssid", properties)
            ACCOUNT_EMAIL -> arrayOf("-e", "account_email", properties)
            NETWORK_PASSWORD -> arrayOf("-e", "network_password", properties)
            ACCOUNT_PASSWORD -> arrayOf("-e", "account_password", properties)
        }
    }

    /**
     * Get the key for the given [Arguments]
     *
     * @param argument [Arguments] instance
     * @return Key corresponding to the argument
     */
    private fun getKey(argument: Arguments) = when (argument) {
        NETWORK_SSID -> "network_ssid"
        ACCOUNT_EMAIL -> "account_email"
        NETWORK_PASSWORD -> "network_password"
        ACCOUNT_PASSWORD -> "account_password"
    }
}