package szymon.swic.plomyk.viewmodel

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModel
import java.lang.Math.ceil
import kotlin.math.ceil

class SongViewVM : ViewModel() {
    private val TAG = "SongView VM"
    private lateinit var songChords: Set<String>

    fun getFormattedSpannableText(inputText: String, textView: TextView): SpannableStringBuilder {
        val formattedLinesText = splitSongLines(inputText, textView)
        val chordRegex = Regex("\\[(.*?)]")
        val matchResult = chordRegex.findAll(formattedLinesText)
        val styledText = formatChordsStyle(SpannableStringBuilder(formattedLinesText), matchResult)


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

    private fun splitSongLines(songText: String, textView: TextView): String {
        val songLines = songText.split("\n").toMutableList()

        var linesLimit = 0
        for (line in songLines) {
            linesLimit += getTooLongSplitLinesList(line, textView).size
        }

        for (i in 0..linesLimit) {
            val splitLines = getTooLongSplitLinesList(songLines[i], textView)

            for(iter in 1..splitLines.size) {
                songLines.add(i+iter*2, splitLines[iter])
            }
        }

        val result = songLines.joinToString("\n")
        Log.d(TAG, result)
        return result
    }

    private fun getFinalLinesAmount(songLines: MutableList<String>, textView: TextView): Int {
        var linesNeeded = 0
        for (line in songLines) {
            linesNeeded += getSplitsNeededForLine(line, textView)
        }
        return linesNeeded
    }

    private fun getSplitsNeededForLine(line: String, textView: TextView): Int {
        val width = textView.paint.measureText(line)
        val textWidth = textView.width
        val result = ceil(width / textView.width).toInt()
        return result
    }


    private fun getTooLongSplitLinesList(line: String, textView: TextView): List<String> {
        var splitLines = mutableListOf<String>()
        var currentLine = ""
        for (sign in line) {
            currentLine += sign
            if (textView.paint.measureText(currentLine) > textView.measuredWidth) {
                splitLines.add(currentLine)
                currentLine = ""
            }
        }

        if (splitLines.isEmpty()) {
            splitLines.add(currentLine)
        }

        return splitLines
    }


}
