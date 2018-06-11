@file:JvmName("Utilities")

package com.charlesmuchene.installer.utils

import com.charlesmuchene.installer.models.Commands
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.*

/**
 * Utilities file
 */

/**
 * Properties file name
 */
const val propertiesFile = "installer.properties"

val lineSeparator = System.lineSeparator()

/**
 * Load app configuration
 */
@Throws(FileNotFoundException::class)
fun loadConfiguration(): Properties {
    val stream = FileInputStream(propertiesFile)
    return Properties().apply { load(stream) }
}

// TODO Remove test implementation
fun main(args: Array<String>) {
    println(Commands.connectToWifi.reduce { a, b -> "$a $b" })
    println(Commands.resetDeviceBridge.reduce { a, b -> "$a $b" })
    println(Commands.addGoogleAccount.reduce { a, b -> "$a $b" })
    println(Commands.pushChecker.reduce { a, b -> "$a $b" })
    println(Commands.installChecker.reduce { a, b -> "$a $b" })
    println(Commands.pushAutomator.reduce { a, b -> "$a $b" })
    println(Commands.installAutomator.reduce { a, b -> "$a $b" })
    println(Commands.pushSBDriverApp.reduce { a, b -> "$a $b" })
    println(Commands.installSBDriverApp.reduce { a, b -> "$a $b" })
}