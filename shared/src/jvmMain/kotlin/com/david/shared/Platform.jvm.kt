package com.david.shared

actual fun platform(): String = "Java ${System.getProperty("java.version")}"
