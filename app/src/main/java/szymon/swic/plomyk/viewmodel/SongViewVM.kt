package szymon.swic.plomyk.viewmodel

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.lifecycle.ViewModel
import java.util.regex.Pattern

class SongViewVM : ViewModel() {
    private val TAG = "SongView VM"
    private lateinit var songChords: Set<String>

    fun getFormattedSpannableText(inputText: String): SpannableStringBuilder {
        val chordRegex = Regex("\\[(.*?)]")
        val matchResult = chordRegex.findAll(inputText)
        val spannableText = SpannableStringBuilder(inputText)
        var bracketsIndexes = mutableListOf<Int>()
        for (singleMatch in matchResult) {
            spannableText.setSpan(
                ForegroundColorSpan(Color.RED),
                singleMatch.range.first,
                singleMatch.range.last,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            bracketsIndexes.add(singleMatch.range.first)
            bracketsIndexes.add(singleMatch.range.last)
        }

        var shiftCounter = 0
        for (index in bracketsIndexes) {
            spannableText.replace(index-shiftCounter, index-shiftCounter+1, "")
            shiftCounter++
        }


        songChords = matchResult.map { it.value }.toSet()
        Log.d(TAG, songChords.toString())

        return spannableText
    }

}
