package com.example.isaquecoelho.mbeventapp.model

data class Event constructor(var title: String? = null,
                        var image: String? = null,
                        var type: List<String>? = null,
                        var schedule: String? = null,
                        var price: String? = null,
                        var address: String? = null,
                        var description: String? = null
)