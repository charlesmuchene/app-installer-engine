@file:JvmName("Utilities")

package com.charlesmuchene.installer.utils

import java.io.*
import java.util.*

/**
 * Properties file name
 */
const val propertiesFile = "installer.properties"

val lineSeparator: String = System.lineSeparator()

val properties: Properties = loadConfiguration()

val usersList: List<Pair<String, String>> by lazy {
    val result = mutableListOf<Pair<String, String>>()
    val file = File(properties.getProperty(USERS_PATH_KEY))
    val reader = BufferedReader(FileReader(file))
    var line: String? = reader.readLine()
    while (line != null) {
        result.add(parseLine(line))
        line = reader.readLine()
    }
    result
}

/**
 * Load app configuration
 */
@Throws(FileNotFoundException::class)
private fun loadConfiguration(): Properties {
    val stream = FileInputStream(propertiesFile)
    return Properties().apply { load(stream) }
}

/**
 * Parse line
 */
fun parseLine(line: String, separator: Char = ','): Pair<String, String> {
    val (email, password) = line.split(separator)
    return Pair(email, password)
}

/**
 * Array reduction extension function
 *
 * @return Flattened array
 */
fun Array<String>.reduce(): String = this.reduce { a, b -> "$a $b" }