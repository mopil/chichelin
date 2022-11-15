package com.chichelin.domain

import org.springframework.data.jpa.repository.JpaRepository

interface ChickenRepository : JpaRepository<Chicken, Long> {}

interface BrandRepository : JpaRepository<Brand, Long> {
    fun existsByName(name: String): Boolean
}

interface ReviewRepository : JpaRepository<Review, Long> {}