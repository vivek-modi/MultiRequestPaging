package com.example.multirequestpaging

data class MovieResponse(
    var id: Int?,
    var name: String?,
    var items: List<Genre>?
)