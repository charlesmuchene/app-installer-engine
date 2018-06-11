@file:JvmName("Utilities")

package com.charlesmuchene.installer.utils

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

val lineSeparator: String = System.lineSeparator()

/**
 * Load app configuration
 */
@Throws(FileNotFoundException::class)
fun loadConfiguration(): Properties {
    val stream = FileInputStream(propertiesFile)
    return Properties().apply { load(stream) }
}