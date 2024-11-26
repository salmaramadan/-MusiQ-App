package com.example.musiqal.downloadManager.util

import com.example.musiqal.youtubeAudioVideoExtractor.model.YouTubeDlExtractorResultDataItem

interface OnMultipleSelectedTrackClickedListener {
    fun onTrackSeelcted(currentSelectedItem: List<YouTubeDlExtractorResultDataItem>)
}