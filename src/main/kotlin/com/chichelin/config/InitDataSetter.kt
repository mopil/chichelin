package com.chichelin.config

import com.chichelin.domain.Brand
import com.chichelin.domain.BrandRepository
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class InitDataSetter(
    private val brandRepository: BrandRepository
) {

    @PostConstruct
    fun setBrands() {
        val brandNames = arrayOf("굽네", "BBQ", "BHC", "교촌", "푸라닭", "네네", "60계", "호식이", "노랑통닭", "처갓집", "기타")
        brandNames.forEach {
            if (!brandRepository.existsByName(it)) {
                brandRepository.save(Brand(it))
            }
        }
    }

}