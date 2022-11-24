package com.chichelin.domain

import com.chichelin.config.getSavePath
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.persistence.*

@Entity
class Brand(
    name: String
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    var name: String = name
        protected set
}



@Entity
class Chicken(
    name: String,
    imageServerFilename: String,
    price: Int,
    spicy: Int,
    brand: String,
    likes: Int = 0,
    reviewCount: Int = 0,
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chicken_id")
    var id: Long? = null
        protected set

    var chickenName: String = name
        protected set

    var imageServerFilename: String = imageServerFilename
        protected set

    var price: Int = price
        protected set

    var spicy: Int = spicy
        protected set

    var brand: String = brand
        protected set

    var likes: Int = likes
        protected set

    var reviewCount: Int = reviewCount
        protected set

    @OneToMany(mappedBy = "chicken", cascade = [CascadeType.ALL], orphanRemoval = true)
    var reviews: MutableList<Review> = mutableListOf()
        protected set

    /**
     * methods
     */
    @PostRemove
    fun deleteImage() {
        File(getSavePath()).delete()
    }
    fun addReview(review: Review) {
        this.reviews.add(review)
    }
    fun plusLikes() { this.likes += 1 }

    fun update(
        chickenName: String? = null,
        imageServerFilename: String? = null,
        price: Int? = null,
        spicy: Int? = null,
        brand: String? = null
    ) {
        chickenName?.let { this.chickenName = it }
        imageServerFilename?.let { this.imageServerFilename = it }
        price?.let { this.price = it }
        spicy?.let { this.spicy = it }
        brand?.let { this.brand = it }
    }

}

@Entity
class Review(
    content: String,
    nickname: String,
    password: Int,
    chicken: Chicken,
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    var id: Long? = null
        protected set

    var content: String = content
        protected set

    var nickname: String = nickname
        protected set

    var password: Int = password
        protected set

    var likes: Int = 0
        protected set

    var createdAt: String = ""
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chicken_id")
    var chicken: Chicken = chicken
        protected set

    /**
     * methods
     */
    fun plusLike() { this.likes += 1 }
    fun update(
        content: String,
        nickname: String,
        password: Int
    ) {
        this.content = content
        this.nickname = nickname
        this.password = password
    }
    @PrePersist
    fun prePersist() {
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"))
    }
}