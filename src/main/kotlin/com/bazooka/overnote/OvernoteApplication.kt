package com.bazooka.overnote

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OvernoteApplication

fun main(args: Array<String>) {
	runApplication<OvernoteApplication>(*args)
}
