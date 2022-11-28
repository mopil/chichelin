package com.chichelin.domain

import com.chichelin.config.PAGE_PER_UNIT
import com.chichelin.config.logger
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/chicken")
class ChickenController(private val chickenService: ChickenService) {
    val log = logger()
    /**
     * Chicken
     */
    @PostMapping
    fun createChicken(@ModelAttribute form: CreateChickenRequest): ChickenResponse {
        return chickenService.createChicken(form)
    }

    @GetMapping("/brand")
    fun getBrands(): BrandListResponse = chickenService.getBrands()

    @GetMapping("/{chickenId}")
    fun getChicken(@PathVariable chickenId: Long): ChickenResponse =
        chickenService.getChicken(chickenId)

    @GetMapping("/list")
    fun getChickenList(
        @PageableDefault(sort = ["id"], direction = Sort.Direction.DESC, size = PAGE_PER_UNIT) pageable: Pageable
    ): ChickenListResponse = chickenService.getChickenList(pageable)

    @PutMapping("/{chickenId}/like")
    fun plusChickenLike(@PathVariable chickenId: Long): ChickenResponse =
        chickenService.plusChickenLike(chickenId)

    @PostMapping("/{chickenId}")
    fun updateChicken(
        @PathVariable chickenId: Long,
        @ModelAttribute form: UpdateChickenRequest
    ): ChickenResponse = chickenService.updateChicken(chickenId, form)

    @DeleteMapping("/{chickenId}")
    fun deleteChicken(@PathVariable chickenId: Long): BoolResponse =
        chickenService.deleteChicken(chickenId)

    @GetMapping("/recommend")
    fun getRecommend(): ChickenResponse = chickenService.getRecommend()

    @GetMapping("/ranking")
    fun getRanking(): ChickenRankingResponse = chickenService.getRanking()

    @GetMapping("/search")
    fun search(
        keyword: String,
        @PageableDefault(sort = ["id"], direction = Sort.Direction.DESC, size = PAGE_PER_UNIT) pageable: Pageable
    ): ChickenListResponse = chickenService.search(keyword, pageable)



    /**
     * Review
     */
    @PostMapping("/{chickenId}/review")
    fun createReview(
        @PathVariable("chickenId") chickenId: Long,
        @RequestBody form: ReviewRequest
    ): ChickenResponse = chickenService.createReview(chickenId, form)

    @PutMapping("/{chickenId}/review/{reviewId}")
    fun updateReview(
        @PathVariable("chickenId") chickenId: Long,
        @PathVariable("reviewId") reviewId: Long,
        @RequestBody form: ReviewRequest
    ): ChickenResponse = chickenService.updateReview(chickenId, reviewId, form)

    @PostMapping("/{chickenId}/review/{reviewId}/like")
    fun plusReviewLike(
        @PathVariable("chickenId") chickenId: Long,
        @PathVariable("reviewId") reviewId: Long,
    ): ChickenResponse = chickenService.plusReviewLike(chickenId, reviewId)

    @DeleteMapping("/{chickenId}/review/{reviewId}")
    fun deleteReview(
        @PathVariable("chickenId") chickenId: Long,
        @PathVariable("reviewId") reviewId: Long,
        @RequestBody passwordDto: ReviewPasswordDto,
    ): ChickenResponse = chickenService.deleteReview(chickenId, reviewId, passwordDto.password)
}

@RestController
class HelloController {
    @GetMapping
    fun hello() = "Hello! Chichelin is running on service."
}