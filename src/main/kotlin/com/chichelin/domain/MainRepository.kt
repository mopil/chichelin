package com.chichelin.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ChickenRepository : JpaRepository<Chicken, Long> {

    @Query("SELECT c FROM Chicken c ORDER BY c.likes DESC, c.reviewCount DESC")
    fun getRecommendChicken(): List<Chicken>
}

interface BrandRepository : JpaRepository<Brand, Long> {
    fun existsByName(name: String): Boolean
}
