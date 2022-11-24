package com.chichelin.config

import com.chichelin.domain.Brand
import com.chichelin.domain.BrandRepository
import com.chichelin.domain.Chicken
import com.chichelin.domain.ChickenRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct
import kotlin.random.Random

@Component
@Transactional
class InitDataSetter(
    private val brandRepository: BrandRepository,
    private val chickenRepository: ChickenRepository
) {
    val brandNames = arrayOf("굽네", "BBQ", "BHC", "교촌", "푸라닭", "네네", "60계", "호식이", "노랑통닭", "처갓집", "기타")

    @PostConstruct
    fun setBrands() {
        brandNames.forEach {
            if (!brandRepository.existsByName(it)) {
                brandRepository.save(Brand(it))
            }
        }
    }

    @PostConstruct
    fun setDummyChickens() {
        if (chickenRepository.count() <= 30L) {
            repeat(30) {
                chickenRepository.save(Chicken(
                    name = "테스트치킨$it",
                    imageServerFilename = "이미지",
                    price = Random.nextInt(10) * 1000,
                    spicy = Random.nextInt(5),
                    brand = brandNames.random(),
                    likes = Random.nextInt(1_000),
                    reviewCount = Random.nextInt(20)
                ))
            }
        }

    }
}