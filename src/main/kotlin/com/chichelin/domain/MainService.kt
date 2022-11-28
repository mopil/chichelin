package com.chichelin.domain

import com.chichelin.config.getSavePath
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Service
@Transactional(readOnly = true)
class ChickenService(
    private val chickenRepository: ChickenRepository,
    private val brandRepository: BrandRepository
    ) {

    private fun findById(chickenId: Long) = chickenRepository.findById(chickenId)
        .orElseThrow { NoSuchElementException() }

    private fun saveChickenImage(file: MultipartFile): String {
        val now = LocalDateTime.now()
        val prefix = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_hh-mm-ss"))
        val serverFilename = "${prefix}_${Random().nextInt(1000)}_${file.originalFilename}"
        val savePath = System.getProperty("user.dir") + "/image/${serverFilename}"
        file.transferTo(File(savePath))
        return serverFilename
    }

    @Transactional
    fun createChicken(form: CreateChickenRequest): ChickenResponse {
        val savedChicken = chickenRepository.save(
            Chicken(
                name = form.chickenName,
                imageServerFilename = saveChickenImage(form.image),
                price = form.price,
                spicy = form.spicy,
                brand = form.brand
            )
        )
        return ChickenResponse(savedChicken)
    }

    fun getBrands() = BrandListResponse(brandRepository.findAll().map { it.name })

    fun getChicken(chickenId: Long) = ChickenResponse(
        chickenRepository.findById(chickenId)
            .orElseThrow { NoSuchElementException() }
    )

    fun getChickenList(pageable: Pageable) = ChickenListResponse(
        chickenRepository.findAll(pageable)
    )

    @Transactional
    fun plusChickenLike(chickenId: Long): ChickenResponse {
        val target = findById(chickenId)
        target.plusLikes()
        return ChickenResponse(target)
    }

    @Transactional
    fun updateChicken(chickenId: Long, form: UpdateChickenRequest): ChickenResponse {
        val target = findById(chickenId)

        if (!form.image.isEmpty) {
            File(getSavePath(target.imageServerFilename)).delete()
            target.update(imageServerFilename = saveChickenImage(form.image))
        }

        target.update(
            chickenName = form.chickenName,
            price = form.price,
            spicy = form.spicy,
            brand = form.brand
        )

        chickenRepository.flush()

        return ChickenResponse(target)
    }

    fun deleteChicken(chickenId: Long): BoolResponse {
        chickenRepository.deleteById(chickenId)
        return BoolResponse(true)
    }

    fun getRecommend() = ChickenResponse(chickenRepository.getRecommendChicken()[0])

    fun getRanking(): ChickenRankingResponse {
        val result = mutableListOf<ChickenResponse>()
        var i = 0
        val chickens = chickenRepository.getRecommendChicken()
        while (i < 3) {
            result.add(ChickenResponse(chickens[i]))
            i++
        }
        return ChickenRankingResponse(result)
    }

    fun search(keyword: String, pageable: Pageable)  = ChickenListResponse(
        chickenRepository.searchByKeyword(keyword, pageable)
    )



    /**
     * Review
     */
    @Transactional
    fun createReview(chickenId: Long, form: ReviewRequest): ChickenResponse {
        val chicken = findById(chickenId)
        chicken.addReview(
            Review(
                content = form.content,
                nickname = form.nickname,
                password = form.password,
                chicken = chicken
            )
        )
        chickenRepository.flush()
        return ChickenResponse(chicken)
    }

    @Transactional
    fun updateReview(chickenId: Long, reviewId: Long, form: ReviewRequest): ChickenResponse {
        val chicken = findById(chickenId)
        chicken.reviews
            .find { it.id == reviewId }
            ?.let {
                if (it.password != form.password) throw RuntimeException("비밀번호가 다릅니다.")
                it.update(
                    content = form.content,
                    nickname = form.nickname,
                    password = form.password
                )
            }
        return ChickenResponse(chicken)
    }

    @Transactional
    fun plusReviewLike(chickenId: Long, reviewId: Long): ChickenResponse {
        val chicken = findById(chickenId)
        chicken.reviews
            .find { it.id == reviewId }
            ?.plusLike()
            ?: throw NoSuchElementException("리뷰가 존재하지 않습니다.")
        return ChickenResponse(chicken)
    }

    @Transactional
    fun deleteReview(chickenId: Long, reviewId: Long, password: Int): ChickenResponse {
        val chicken = findById(chickenId)

        chicken.reviews
            .find { it.id == reviewId }
            ?.let {
                if (it.password != password) throw RuntimeException("비밀번호가 다릅니다.")
                chicken.reviews.remove(it)
            }

        return ChickenResponse(chicken)
    }



}