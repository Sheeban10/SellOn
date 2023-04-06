package com.example.sellon.model

data class CategoriesModel(
    val key : String,
    val image : String,
    val image_bw : String
){
    constructor() : this("",
    "",
    "")
}