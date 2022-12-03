package com.chichelin.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ChickenRepository : JpaRepository<Chicken, Long> {

    @Query("SELECT c FROM Chicken c ORDER BY c.likes DESC, c.reviewCount DESC")
    fun getRecommendChicken(): List<Chicken>

    @Query("SELECT c FROM Chicken c WHERE c.brand LIKE %:keyword% OR c.chickenName LIKE %:keyword%")
    fun searchByKeyword(keyword: String): List<Chicken>
}

interface BrandRepository : JpaRepository<Brand, Long> {
    fun existsByName(name: String): Boolean
}
