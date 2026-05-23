package com.fantasy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler
import java.util.concurrent.Executors

@SpringBootApplication
@EnableScheduling
class FantasyApplication

fun main(args: Array<String>) {
    runApplication<FantasyApplication>(*args)
}
