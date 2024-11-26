package com.example.musiqal.downloadManager.util

import com.example.musiqal.youtubeAudioVideoExtractor.model.YouTubeDlExtractorResultDataItem

public interface OnMultipleExtractedItemClickListener {
    fun onClick(
        youTubeDlExtractorResultDataItem: List<YouTubeDlExtractorResultDataItem>,
        adapterPosition: Int
    )
}