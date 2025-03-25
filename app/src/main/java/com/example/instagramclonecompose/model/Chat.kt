package com.example.instagramclonecompose.model

data class Chat(val message:String,val idSender:String,val idReceiver:String,val time:String){
    constructor():this("","","","")
}