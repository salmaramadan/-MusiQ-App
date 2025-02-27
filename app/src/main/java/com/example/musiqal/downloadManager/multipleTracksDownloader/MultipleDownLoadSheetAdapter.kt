package com.example.musiqal.downloadManager.multipleTracksDownloader

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musiqal.R
import com.example.musiqal.downloadManager.util.FileSizeFormat
import com.example.musiqal.downloadManager.util.OnMultipleExtractedItemClickListener
import com.example.musiqal.youtubeAudioVideoExtractor.model.YouTubeDlExtractorResultDataItem

class MultipleDownLoadSheetAdapter(
    val context: Context,
    val onExtractedItemClickListener: OnMultipleExtractedItemClickListener
) :
    RecyclerView.Adapter<MultipleDownLoadSheetAdapter.SpecialUserItemsViewHolder>() {
    lateinit var _listOfYouTubeExtractorItems: MutableList<List<YouTubeDlExtractorResultDataItem>>

    fun setItemsList(items: MutableList<List<YouTubeDlExtractorResultDataItem>>) {
        _listOfYouTubeExtractorItems = items
        notifyItemInserted(items.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialUserItemsViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.recycler_view_item_track_info, null)
        return SpecialUserItemsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpecialUserItemsViewHolder, position: Int) {
        val extractedItem = _listOfYouTubeExtractorItems.get(position)
        var allFileSize = 0L
        extractedItem.forEach { i -> allFileSize += i.filesize!! }

        holder.bind(
            extractedItem.get(0),
            position,
            allFileSize,
            context,
            onExtractedItemClickListener, extractedItem
        )

    }


    override fun getItemCount(): Int {
        return _listOfYouTubeExtractorItems.size
    }

    class SpecialUserItemsViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var radioButton: RadioButton
        var txtViewAudioQuality: TextView
        var txtViewAudioExtension: TextView
        var txtViewAudioSize: TextView
        var txtHighSpeedDownload: TextView

        init {
            radioButton = itemView.findViewById(R.id.recyclerViewItemDownloadTrackInfo_radioButton)
            txtViewAudioQuality =
                itemView.findViewById(R.id.recyclerViewItemDownloadTrackInfo_txtAudioQuality)
            txtViewAudioExtension =
                itemView.findViewById(R.id.recyclerViewItemDownloadTrackInfo_txtAudioExtension)
            txtViewAudioSize =
                itemView.findViewById(R.id.recyclerViewItemDownloadTrackInfo_txtAudioSize)
            txtHighSpeedDownload =
                itemView.findViewById(R.id.recyclerViewItemDownloadTrackInfo_txtHighSpeedDownload)
        }

        fun bind(
            extractedItem: YouTubeDlExtractorResultDataItem,
            position: Int,
            allFileSizes: Long,
            context: Context,
            onExtractedItemClickListener: OnMultipleExtractedItemClickListener,
            allExtractedItems: List<YouTubeDlExtractorResultDataItem>
        ) {
            //file size in bytes
            Log.d("TAG", "bind: " + extractedItem.ext!!)
//            if (extractedItem.ext?.equals("mp3")!! || extractedItem.ext?.equals("m4a")!!) {
//                txtHighSpeedDownload.visibility = View.VISIBLE
//            } else {
//                txtHighSpeedDownload.visibility = View.INVISIBLE
//            }
            val extension: String = getExtenstion(extractedItem.ext)
            val size: String = getFileSize(allFileSizes)
            txtViewAudioQuality.setText("${extractedItem.videoQualityId}K")
            txtViewAudioExtension.setText("${extension}")
            txtViewAudioSize.setText("${size}")
            radioButton.isChecked = extractedItem.isSelected
            itemView.setOnClickListener {
                clickOnExtractedAudioTrack(
                    adapterPosition,
                    allExtractedItems,
                    onExtractedItemClickListener
                )

            }
            radioButton.setOnClickListener {
                clickOnExtractedAudioTrack(
                    adapterPosition,
                    allExtractedItems,
                    onExtractedItemClickListener
                )
            }


        }

        private fun getFileSize(filesize: Long?): String {
            return FileSizeFormat.formatFileSize(filesize!!)

        }

        private fun getExtenstion(ext: String?): String {
            if (ext.equals("webm")) {
                return "mp3"
            }
            return ext!!

        }

        fun clickOnExtractedAudioTrack(
            position: Int,
            _listOfYouTubeExtractorItems: List<YouTubeDlExtractorResultDataItem>,
            onExtractedItemClickListener: OnMultipleExtractedItemClickListener
        ) {
            _listOfYouTubeExtractorItems.forEach { i -> i.isSelected = false }
            _listOfYouTubeExtractorItems[position].isSelected = true
            if (position != -1) {
                onExtractedItemClickListener.onClick(
                    _listOfYouTubeExtractorItems, position
                )
            }

        }

    }


}
