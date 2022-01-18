package szymon.swic.plomyk.features.songs.presentation.details

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.TextView
import szymon.swic.plomyk.R
import szymon.swic.plomyk.core.base.BaseViewModel
import szymon.swic.plomyk.features.songs.presentation.chords.ChordGridDialog
import java.util.*


class SongDetailsViewModel : BaseViewModel() {
    private val TAG = "SongView VM"

    private var CHORDS_ARRAY =
        arrayOf("A", "B", "H", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#")
    private lateinit var songChordsSet: Set<String>
    private lateinit var rawLyricsText: String
    private lateinit var chordsPositionIndexes: List<Int>
    private var currKeyShift = 0
    private lateinit var spannedText: SpannableStringBuilder

    fun getFormattedSpannableText(inputText: String, appContext: Context): SpannableStringBuilder {
        rawLyricsText = inputText
        val chordRegex = Regex("\\[(.*?)]")
        val matchResult = chordRegex.findAll(rawLyricsText)
        spannedText = formatChordsStyle(SpannableStringBuilder(rawLyricsText), matchResult, appContext)

        songChordsSet = matchResult.map { it.value }.toSet()
        Log.d(TAG, songChordsSet.toString())

        return spannedText
    }

    private fun formatChordsStyle(
        text: SpannableStringBuilder,
        chordSequences: Sequence<MatchResult>,
        appContext: Context
    ): SpannableStringBuilder {
        val bracketsIndexes = mutableListOf<Int>()
        val tmpChordsPositionIndexes = mutableListOf<Int>()
        for (singleMatch in chordSequences) {
            text.setSpan(
                ForegroundColorSpan(appContext.getColor(R.color.colorAccent)),
                singleMatch.range.first,
                singleMatch.range.last,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            bracketsIndexes.add(singleMatch.range.first)
            tmpChordsPositionIndexes.add(bracketsIndexes.last() + 1)
            bracketsIndexes.add(singleMatch.range.last)
        }

        chordsPositionIndexes = tmpChordsPositionIndexes

        for (i in bracketsIndexes) {
            Log.d(TAG + "Brackets", i.toString())
        }
        for (i in chordsPositionIndexes) {
            Log.d(TAG + "Chords", i.toString())
        }

        //delete brackets around chords
        for ((shiftCounter, index) in bracketsIndexes.withIndex()) {
            text.replace(index - shiftCounter, index - shiftCounter + 1, "")
        }

        return text
    }

    fun getChordsDialog(fragmentContext: Context) =
        ChordGridDialog(getChordImagesIds(fragmentContext))

    fun getAnimationDuration(textView: TextView): Long = (textView.lineCount * 700).toLong()

    fun getSpannedLyrics(): SpannableStringBuilder = spannedText

    private fun getChordImagesIds(fragmentContext: Context): Array<Int> {
        val resultList = mutableListOf<Int>()

        songChordsSet.forEach {
            val soundName = it.removeSurrounding("[", "]")
            val resourceName =
                if (soundName[0].isUpperCase())
                    "chord_${soundName.lowercase(Locale.getDefault())}_major"
                else "chord_${soundName.lowercase(Locale.getDefault())}_minor"

            resultList.add(
                fragmentContext.resources.getIdentifier(
                    resourceName,
                    "drawable",
                    fragmentContext.packageName
                )
            )
        }
        return resultList.toTypedArray()
    }

    fun getTransposedText(interval: Int, appContext: Context): SpannableStringBuilder {
        changeKey(interval)

        return getFormattedSpannableText(rawLyricsText, appContext)
    }

    private fun changeKey(interval: Int) {
        var textIndex = 0

        while (textIndex != rawLyricsText.lastIndex) {

            if (rawLyricsText[textIndex] == '[') {
                val chordIndex = textIndex + 1
                val baseSoundNameLength = if (rawLyricsText[chordIndex + 1] == '#') 2 else 1
                val baseSoundName =
                    rawLyricsText.substring(chordIndex, chordIndex + baseSoundNameLength)

                rawLyricsText = rawLyricsText.replaceRange(
                    chordIndex,
                    chordIndex + baseSoundNameLength,
                    changeChord(baseSoundName, interval)
                )
            }
            textIndex++
        }
        Log.d(TAG + "RAW", rawLyricsText)
    }

    private fun changeChord(currChord: String, interval: Int): String {

        if (currChord[0].isLowerCase()) {
            for (chord in CHORDS_ARRAY.indices) {
                CHORDS_ARRAY[chord] = CHORDS_ARRAY[chord].lowercase(Locale.getDefault())
            }
        }

        if (currChord[0].isUpperCase()) {
            for (chord in CHORDS_ARRAY.indices) {
                CHORDS_ARRAY[chord] = CHORDS_ARRAY[chord].uppercase(Locale.getDefault())
            }
        }
        val currSoundIndex = CHORDS_ARRAY.indexOf(currChord)
        return CHORDS_ARRAY[getArraySoundIndex(currSoundIndex, interval)]
    }

    private fun getArraySoundIndex(curr: Int, interval: Int): Int {
        val shift = curr + interval

        return when {
            //non-boundary case
            shift < 0 -> CHORDS_ARRAY.size + shift
            //boundary cases
            shift < CHORDS_ARRAY.size -> curr + interval
            else -> shift - CHORDS_ARRAY.size
        }
    }
}
