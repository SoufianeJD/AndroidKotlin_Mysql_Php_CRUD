package com.example.thotmb3_soufianejd

class ConnectionToScripts {
companion object {
    private val SERVER = "http://192.168.0.26/thotscriptsmb3/"
    val CREATE = SERVER+"create.php"
    val READ = SERVER+"read.php"
    val DELETE = SERVER+"delete.php"
    val UPDATE = SERVER+"update.php"
}
}