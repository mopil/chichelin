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
                brand = form.brand,
                writer = form.writer,
                password = form.password
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
            brand = form.brand,
            writer = form.writer,
            password = form.password
        )

        chickenRepository.flush()

        return ChickenResponse(target)
    }

    fun deleteChicken(chickenId: Long): BoolResponse {
        chickenRepository.deleteById(chickenId)
        return BoolResponse(true)
    }



}