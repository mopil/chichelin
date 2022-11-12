package com.chichelin

import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.persistence.*

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeEntity {

    var createdAt: String = ""
    var updatedAt: String = ""

    @PrePersist
    fun prePersist() {
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"))
        this.updatedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"))
    }

    @PreUpdate
    fun preUpdate() {
        this.updatedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"))
    }
}

@Entity
class Chicken(
    id: Long,
    name: String,
    image: String,
    price: Int,
    spicy: Int,
    brand: String,
    likes: Int,
    reviews: MutableList<Review>
): BaseTimeEntity() {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    var chickenName: String = ""
        protected set

    var image: String = ""
        protected set

    var price: Int = 0
        protected set

    var spicy: Int = 0
        protected set

    var brand: String = ""
        protected set

    var likes: Int = 0
        protected set

    var writer: String = "익명"
        protected set

    var password: Int = 0
        protected set

    @OneToMany(mappedBy = "chicken", cascade = [CascadeType.ALL], orphanRemoval = true)
    var reviews: MutableList<Review> = mutableListOf()
        protected set

}

@Entity
class Review(
    id: Long,
    content: String,
    password: Int,
    likes: Int,
    chicken: Chicken
): BaseTimeEntity() {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    var content: String = ""
        protected set

    var nickname: String = ""
        protected set

    var password: Int = 0
        protected set

    var likes: Int = 0
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    var chicken: Chicken? = null
        protected set
}