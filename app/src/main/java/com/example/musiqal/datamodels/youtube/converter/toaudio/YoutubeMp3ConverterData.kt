package com.example.musiqal.datamodels.youtube.converter.toaudio

data class YoutubeMp3ConverterData(
    val duration: Double,
    val link: String,
    val msg: String,
    val progress: Int,
    val status: String,
    val title: String,
    val filesize : Int,

)
/**
 *link:"https://malpha.123tokyo.xyz/get.php/4/3d/huGd4efgdPA.mp3?cid=MmEwMTo0Zjg6YzAxMDo5ZmE2OjoxfE5BfERF&h=IVFf8CKXd86tcwMoXGFHBw&s=1723389066&n=Charli%20xcx%20-%20Guess%20featuring%20Billie%20Eilish%20%28official%20video%29"
 * title:"Charli xcx - Guess featuring Billie Eilish (official video)"
 * filesize:2892843
 * progress:0
 * duration:160.96653228527
 * status:"ok"
 * msg:"success"
 */
