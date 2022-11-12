package com.chichelin

import org.springframework.data.jpa.repository.JpaRepository

interface ChickenRepository : JpaRepository<Chicken, Long> {}
interface ReviewRepository : JpaRepository<Review, Long> {}