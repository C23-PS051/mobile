package com.dicoding.c23ps051.caferecommenderapp.data

import com.dicoding.c23ps051.caferecommenderapp.model.Cafe
import com.dicoding.c23ps051.caferecommenderapp.model.CafeDummy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class CafeRepository {

    private val cafeList = mutableListOf<Cafe>()

    init {
        if (cafeList.isEmpty()) {
            CafeDummy.cafeList.forEach {
                val cafe = Cafe(
                    id = it.id,
                    thumbnail = it.thumbnail,
                    name = it.name,
                    rating = it.rating,
                    ratingCount = it.ratingCount,
                    distance = it.distance,
                    address = it.address,
                    isOpen = it.isOpen,
                    minPrice = it.minPrice,
                    maxPrice = it.maxPrice,
                    region = it.region
                )
                cafeList.add(cafe)
            }
        }
    }

    fun getAllCafes(): Flow<List<Cafe>> {
        return flowOf(cafeList)
    }

    fun getCafeById(id: Long): Cafe {
        return cafeList.first { it.id == id }
    }

    fun getNearbyCafes(): Flow<List<Cafe>> {
        // TODO: TO BE UPDATED
        return getAllCafes()
            .map { cafes ->
                cafes.filter { cafe ->
                    cafe.distance <= 1000
                }
            }
    }

    fun getOpen24HoursCafes(): Flow<List<Cafe>> {
        // TODO: TO BE UPDATED
        return getAllCafes()
            .map { cafes ->
                cafes.filter { cafe ->
                    cafe.isOpen
                }
            }
    }

    fun getOnBudgetCafes(): Flow<List<Cafe>> {
        // TODO: TO BE UPDATED
        return getAllCafes()
            .map { cafes ->
                cafes.filter { cafe ->
                    cafe.minPrice < 20000
                }
            }
    }

    fun searchCafes(query: String): List<Cafe> {
        return cafeList.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    companion object {
        @Volatile
        private var instance: CafeRepository? = null

        fun getInstance(): CafeRepository =
            instance ?: synchronized(this) {
                CafeRepository().apply {
                    instance = this
                }
            }
    }
}