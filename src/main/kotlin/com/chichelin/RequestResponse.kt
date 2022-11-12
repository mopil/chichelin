package com.chichelin

import org.springframework.web.multipart.MultipartFile

data class ChickenRequest(
    var chickenName: String,
    var image: MultipartFile,
    var price: Int,
    var spicy: Int,
    var brand: String,
    var writer: String,
    var password: Int,
)

data class ChickenResponse(
    var name: String,
    var price: Int,
    var spicy: Int,
    var brand: String,
    var likes: Int,
    var createdAt: String,
    var reviews: MutableList<ReviewResponse> = mutableListOf()
)

data class ChickenListResponse(
    var totalNum: Int,
    var curNum: Int,
    var totalPage: Int,
    var curPage: Int,
    var chickens: MutableList<ChickenResponse> = mutableListOf()
)

data class ReviewRequest(
    var nickname: String,
    var password: Int,
    var content: String,
)

data class ReviewResponse(
    var nickname: String,
    var password: Int,
    var content: String,
    var likes: Int = 0,
    var createdAt: String,
)