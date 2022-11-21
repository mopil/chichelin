package com.chichelin.domain

import com.chichelin.config.logger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    val log = logger()
    @GetMapping("/test")
    fun sleep() {
        log.info("income")
        Thread.sleep(3_000)
        log.info("end")
    }

}