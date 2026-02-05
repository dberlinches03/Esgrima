package org.example.esgrima

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform