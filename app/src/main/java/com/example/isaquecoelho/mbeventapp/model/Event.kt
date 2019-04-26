package com.example.isaquecoelho.mbeventapp.model

data class Event constructor(var id: String? = null,
                        var title: String? = null,
                        var image: String? = null,
                        var category: List<String>? = null,
                        var schedule: String? = null,
                        var price: String? = null,
                        var address: String? = null,
                        var overview: String? = null
)