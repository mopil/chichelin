package com.chichelin

import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller(private val chickenService: ChickenService) {
    val log = logger()

    @PostMapping
    fun createChicken(@ModelAttribute form: ChickenRequest) = chickenService.createChicken(form)

}