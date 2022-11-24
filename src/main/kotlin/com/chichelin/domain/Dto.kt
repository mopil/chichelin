package com.chichelin.domain

import org.springframework.data.domain.Page
import org.springframework.web.multipart.MultipartFile

data class BoolResponse(val result: Boolean = true)

data class ErrorResponse(val cause: String = "", val message: String = "")

data class ErrorListResponse(val errors: List<ErrorResponse>)

data class CreateChickenRequest(
    var chickenName: String,
    var image: MultipartFile,
    var price: Int = 0,
    var spicy: Int = 0,
    var brand: String = ""
)

data class UpdateChickenRequest(
    var chickenName: String?,
    var image: MultipartFile,
    var price: Int?,
    var spicy: Int?,
    var brand: String?
)

data class ChickenResponse(
    var id: Long,
    var name: String,
    var imageServerFilename: String,
    var price: Int,
    var spicy: Int,
    var brand: String,
    var likes: Int,
    var reviews: List<ReviewResponse> = mutableListOf()
) {
    constructor(c: Chicken): this(
        id = c.id!!,
        name = c.chickenName,
        imageServerFilename = c.imageServerFilename,
        price = c.price,
        spicy = c.spicy,
        brand = c.brand,
        likes = c.likes,
        reviews = c.reviews.map { ReviewResponse(it) }.toList()
    )
}

data class ChickenRankingResponse(
    var ranking: MutableList<ChickenResponse>
)

data class ChickenListResponse(
    var totalCount: Long,
    var curCount: Int,
    var totalPage: Int,
    var curPage: Int,
    var chickens: List<ChickenResponse> = mutableListOf()
) {
    constructor(chickenPage: Page<Chicken>): this(
        totalCount = chickenPage.totalElements,
        curCount = chickenPage.numberOfElements,
        totalPage = chickenPage.totalPages,
        curPage = chickenPage.number,
        chickens = chickenPage.map { ChickenResponse(it) }.toList()
    )
}

data class BrandListResponse(
    var brands: List<String> = mutableListOf()
)

data class ReviewPasswordDto(
    var password: Int
)

data class ReviewRequest(
    var nickname: String,
    var password: Int,
    var content: String
)

data class ReviewResponse(
    var id: Long,
    var nickname: String,
    var password: Int,
    var content: String,
    var likes: Int = 0,
    var createdAt: String,
) {
    constructor(r: Review): this(
        id = r.id!!,
        nickname = r.nickname,
        password = r.password,
        content = r.content,
        likes = r.likes,
        createdAt = r.createdAt
    )
}