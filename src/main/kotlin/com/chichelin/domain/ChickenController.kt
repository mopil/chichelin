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
    fun getChickenList(@PageableDefault(sort = ["id"],
            direction = Sort.Direction.DESC,
            size = PAGE_PER_UNIT) pageable: Pageable): ChickenListResponse =
        chickenService.getChickenList(pageable)

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


}