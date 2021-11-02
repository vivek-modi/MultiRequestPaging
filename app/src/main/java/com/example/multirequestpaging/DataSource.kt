package com.example.multirequestpaging

class DataSource {

    data class MovieResult(
        val result: ArrayDeque<Genre>?,
        val nextKey: String?
    )

    fun fetchInitialMovie(): MovieResult {
        val response = ApiInterface.create().getMovieResponse(20)

        return MovieResult(
            addInArrayDeque(response),
            response.items?.last()?.date
        )
    }

    fun fetchMovieBefore(key: String): MovieResult {
        val response = ApiInterface.create().getMovieResponseBefore(20, key)

        return MovieResult(
            addInArrayDeque(response),
            response.items?.last()?.date
        )
    }

    private fun addInArrayDeque(response: MovieResponse): ArrayDeque<Genre> {
        val result: ArrayDeque<Genre> = ArrayDeque()
        response.items?.forEach {
            result.add(it)
        }

        return result
    }

}