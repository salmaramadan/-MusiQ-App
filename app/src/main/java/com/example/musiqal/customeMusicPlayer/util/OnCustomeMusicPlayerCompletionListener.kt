package com.example.musiqal.customeMusicPlayer.util

import android.media.MediaPlayer
//handle music player completion events.
interface OnCustomeMusicPlayerCompletionListener {
fun onComplete(mediaPlayer: MediaPlayer)
fun onSeeking(seekedDurationInMilles:Int)
}
