package com.example.musiqal.downloadManager.util

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.musiqal.dialogs.SimpleYesOrNoDialog
import com.example.musiqal.downloadManager.data.DownloadInfo
import com.example.musiqal.youtubeAudioVideoExtractor.model.LocalExtractedFileData
import com.example.musiqal.youtubeAudioVideoExtractor.model.YouTubeDlExtractorResultData
import com.example.musiqal.youtubeAudioVideoExtractor.model.YouTubeDlExtractorResultDataItem
import com.example.musiqal.youtubeAudioVideoExtractor.mvi.YouTubeExtractorViewModel
import com.example.musiqal.youtubeAudioVideoExtractor.mvi.state.SingleExtractedDataStateView
import com.example.musiqal.youtubeAudioVideoExtractor.util.YoutubeDlResultFormatIDs
import com.example.musiqal.youtubeAudioVideoExtractor.util.YoutubeDlResultSate
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectIndexed
import java.net.URL

class DownloadInfoExtractor(
    private val context: Context,
    private val onFilterationSuccess: OnFilterationSuccess,
    val showProgress: Boolean = true
) {
    private val TAG = "DownloadInfoExtractor"
    fun extractVideosUrlsByVideoLink(urls: DownloadInfo) {
        if (showProgress) {
            showProgressDialog(urls)

        }
        addVideoDownloadUrlFromDataBase(urls)

//        val youTubeExtractorViewModel =
//            ViewModelProvider(context as AppCompatActivity).get(YouTubeExtractorViewModel::class.java)
//        youTubeExtractorViewModel.getAudio(urls.videoId)
//        CoroutineScope(Dispatchers.Default).launch {
//            youTubeExtractorViewModel.audioStateFlow.collectIndexed { index, value ->
//                when (value) {
//                    is YoutubeURLSViewState.Loading -> {
//                        Log.d(TAG, "getVideoLinkByVideoId: loading")
//                    }
//                    is YoutubeURLSViewState.Success -> {
//
//                        Log.d(TAG, "extractVideosUrlsByVideoLink: "+value.youtubeUrl+" "+urls)
//                        if (index == 1) {

//                            dataRetrieved(value.youtubeUrl, urls)
//                            dataRetrieved( urls)
//                        }
//
//                    }
//                    is YoutubeURLSViewState.Failed -> {
//                        Log.d(TAG, "extractVideosUrlsByVideoLink: "+value.errorMessgae)
//                    }
//
//                }
//
            }



    private val simpleYesOrNoDialog: SimpleYesOrNoDialog = SimpleYesOrNoDialog(context)
    private fun showProgressDialog(urls: DownloadInfo) {

        val extractUrlMsg = "Extracting Track Streaming Url"
//        simpleYesOrNoDialog.dismis()
        simpleYesOrNoDialog.intialize(extractUrlMsg, urls.videoTitle, "", "", 0, false)
        simpleYesOrNoDialog.show(true)

    }

//    private fun dataRetrieved(youtubeUrl: YouTubeDlExtractorResultData, urls: DownloadInfo) {
//        Log.d(TAG, "dataRetrieved: ")
//        val filterYoutubeExtractorData = filterYoutubeExtractorData(youtubeUrl)
//        val assignAudioQualityToAudioFile =
//            YoutubeDlResultSate.assignAudioQualityToAudioFile(filterYoutubeExtractorData)
//        assignAudioQualityToAudioFile.forEach { i -> Log.d(TAG, "dataRetrieved: " + i.toString()) }
////01
//
//
//    }

    private fun addVideoDownloadUrlFromDataBase(
        urls: DownloadInfo
    ) {
        val youTubeExtractorViewModel =
            ViewModelProvider((context as AppCompatActivity)).get(YouTubeExtractorViewModel::class.java)
        youTubeExtractorViewModel.getSingleExtractedDataById(urls.videoId)
//        youTubeExtractorViewModel.getSingleExtractedDataById(urls.videoId)
        CoroutineScope(Dispatchers.Main).launch {
            youTubeExtractorViewModel.singleExtractedDataStateFLow.collectIndexed { index, value ->
                when (value) {
                    is SingleExtractedDataStateView.Loading -> Log.d(
                        TAG,
                        "addVideoDownloadUrlFromDataBase: loading"
                    )
                    is SingleExtractedDataStateView.Success -> {
                        Log.d(TAG, "addVideoDownloadUrlFromDataBase: "+index)
                        if (index == 1||index==0) {
                            Log.d(
                                TAG,
                                "addVideoDownloadUrlFromDataBase: " + value.localExtractedFileData
                            )
               try {
                   checkIfUrlIsWorking(
                       value.localExtractedFileData,
                       urls
                   )
               }catch (e:Exception)
               {
                   Log.d(TAG, "addVideoDownloadUrlFromDataBase: "+e.message)
               }
                        }
                    }
                    is SingleExtractedDataStateView.Failed -> {
                        Log.d(TAG, "addVideoDownloadUrlFromDataBase: failed")
                    }

                }
            }
        }

    }

    private fun checkIfUrlIsWorking(
        assignAudioQualityToAudioFile: LocalExtractedFileData,
        urls: DownloadInfo
    ) {



        CoroutineScope(Dispatchers.Default).launch {
            val audioUrl = URL(assignAudioQualityToAudioFile.mp3Url)
            val openConnection = audioUrl.openConnection()!!
//            if (openConnection.contentLength > 0) {
//                Log.d(
//                    TAG,
//                    "checkIfUrlIsWorking: " + openConnection.contentType + " " + openConnection.contentLength
//                )
//                assignAudioQualityToAudioFile.add(
            val youTubeDlExtractorResultDataItem = YouTubeDlExtractorResultDataItem(
                url = assignAudioQualityToAudioFile.mp3Url,
                filesize = openConnection.contentLength.toLong(),
                ext = "mp3",
                videoQualityId = "128"
            )
            val list:MutableList<YouTubeDlExtractorResultDataItem> = mutableListOf()
            list.add(youTubeDlExtractorResultDataItem)
            onFilterationSuccess.onSuccess(list)
            hideProgressDialog()

//                )
//                assignDownLoadInfoDataToUrl(assignAudioQualityToAudioFile, urls)
//
//
//            } else {
//                Log.d(TAG, "checkIfUrlIsWorking: non")
//                assignDownLoadInfoDataToUrl(assignAudioQualityToAudioFile, urls)
//
//            }
        }

    }

    private fun hideProgressDialog() {
//        if (showProgress)
//        {
//
            simpleYesOrNoDialog.dismis()
//        }
    }

    private fun assignDownLoadInfoDataToUrl(
        assignAudioQualityToAudioFile: List<YouTubeDlExtractorResultDataItem>,
        urls: DownloadInfo
    ) {
        assignAudioQualityToAudioFile.forEach { i ->
            i.videoTitle = urls.videoTitle
            i.videoDuration = urls.videoDuration.toString()

        }
//        Log.d(TAG, "assignDownLoadInfoDataToUrl: " + assignAudioQualityToAudioFile.size)
        onFilterationSuccess.onSuccess(assignAudioQualityToAudioFile)
        hideProgressDialog()

    }


    private fun filterYoutubeExtractorData(youTubeDlExtractorResultData: YouTubeDlExtractorResultData):
            List<YouTubeDlExtractorResultDataItem> {
        YoutubeDlResultSate.checkStateForMultiple(youTubeDlExtractorResultData)
        return youTubeDlExtractorResultData.filter { i ->
            i.format?.contains(
                "audio only"
            )!! || i.isItVideo.equals(YoutubeDlResultFormatIDs.IsVideoState.AUDIO.msg)
        }


    }
}

interface OnFilterationSuccess {
    fun onSuccess(
        dataItems: List<YouTubeDlExtractorResultDataItem>
    )
}