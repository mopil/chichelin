package com.chichelin

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ChickenService(private val chickenRepository: ChickenRepository) {

    fun createChicken(form: ChickenRequest) {

    }
}