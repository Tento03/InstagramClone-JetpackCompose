package com.example.instagramclonecompose.model

data class Post(val image:String,val description:String,val time:String){
    constructor():this("","","")
}
