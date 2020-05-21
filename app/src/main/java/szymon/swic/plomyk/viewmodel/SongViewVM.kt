package szymon.swic.plomyk.viewmodel

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModel
import szymon.swic.plomyk.view.ChordGridDialog


class SongViewVM : ViewModel() {
    private val TAG = "SongView VM"
    private lateinit var songChords: Set<String>

    fun getFormattedSpannableText(inputText: String, textView: TextView): SpannableStringBuilder {
        val chordRegex = Regex("\\[(.*?)]")
        val matchResult = chordRegex.findAll(inputText)
        val styledText = formatChordsStyle(SpannableStringBuilder(inputText), matchResult)


        songChords = matchResult.map { it.value }.toSet()
        Log.d(TAG, songChords.toString())

        return styledText
    }

    private fun formatChordsStyle(
        text: SpannableStringBuilder,
        chordSequences: Sequence<MatchResult>
    ): SpannableStringBuilder {
        val bracketsIndexes = mutableListOf<Int>()
        for (singleMatch in chordSequences) {
            text.setSpan(
                ForegroundColorSpan(Color.RED),
                singleMatch.range.first,
                singleMatch.range.last,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            bracketsIndexes.add(singleMatch.range.first)
            bracketsIndexes.add(singleMatch.range.last)
        }

        for ((shiftCounter, index) in bracketsIndexes.withIndex()) {
            text.replace(index - shiftCounter, index - shiftCounter + 1, "")
        }
        return text
    }

    fun getChordsDialog(fragmentContext: Context): ChordGridDialog {
        return ChordGridDialog(getChordImagesIds(fragmentContext))
    }

    fun getAnimationDuration(textView: TextView): Long {
        return (textView.lineCount * 700).toLong()
    }

    private fun getChordImagesIds(fragmentContext: Context): Array<Int> {
        val resultList = mutableListOf<Int>()

        songChords.forEach {
            val soundName = it.removeSurrounding("[", "]")
            val resourceName =
                if (soundName[0].isUpperCase())
                     "chord_${soundName.toLowerCase()}_major"
                else "chord_${soundName.toLowerCase()}_minor"
//            Log.d(TAG, "Resource: $resourceName")
            resultList.add(
                fragmentContext.resources.getIdentifier(
                    resourceName,
                    "drawable",
                    fragmentContext.packageName
                )
            )
//            Log.d(TAG, "ResourceID: ${resultList.last()}")
        }

        return resultList.toTypedArray()
    }
}
