package szymon.swic.plomyk.viewmodel

import android.Manifest
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs
import kotlin.random.Random

class TunerTestVM : ViewModel() {

//      parameters for recorder configuration:

    private val TAG = "TunerVM";

    val audioSource = MediaRecorder.AudioSource.MIC
    val samplingFrequency = 8000
    val channelConfig = AudioFormat.CHANNEL_IN_STEREO
    val audioEncoding = AudioFormat.ENCODING_PCM_8BIT
    val bufferSize = AudioRecord.getMinBufferSize(samplingFrequency, channelConfig, audioEncoding)
    val blockSize = 256
    val updateOffset = 0
    var buffer = ShortArray(blockSize)

//      for recording process:

    private var recorder: AudioRecord? = null
    private var RECORDING_FLAG = false

    val frequency: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }

    fun setRecorder() {
        recorder =
            AudioRecord(audioSource, samplingFrequency, channelConfig, audioEncoding, bufferSize)
        Log.d(TAG, recorder?.state.toString())
        Log.d(TAG, recorder?.sampleRate.toString())
    }

    fun startAnalysing() {
        Log.d(TAG, "Start Analysing")
        CoroutineScope(Default).launch {
            recorder?.startRecording()
            RECORDING_FLAG = true
            getFrequencyStream()
        }
    }

    fun stopAnalysing() {
        Log.d(TAG, "Stop Analysing")
        recorder?.stop()
        RECORDING_FLAG = false
    }

    private suspend fun getFrequencyStream() {
        while (RECORDING_FLAG) {
            Log.d(TAG, "Recording Loop")
            var lastUpdatedFreq = 0
            recorder?.read(buffer, 0, blockSize)
            withContext(Main) {
                var currFreq = calculate(buffer)
                if (abs(currFreq - lastUpdatedFreq) >= updateOffset) {
                    frequency.value = currFreq
                    lastUpdatedFreq = currFreq
                }
                Log.d("FreqLog", frequency.value.toString())
            }
        }
    }

    private fun calculate(audioData: ShortArray): Int {

        val numSamples = audioData.size
        var numCrossing = 0
        for (p in 0 until numSamples - 1) {
            if (audioData[p] * audioData[p + 1] <= 0) {
                numCrossing++
            }
        }

        val numSecondsRecorded = numSamples.toFloat() / samplingFrequency.toFloat()
        val numCycles = (numCrossing / 2).toFloat()
        val frequency = numCycles / numSecondsRecorded

        return frequency.toInt()
    }
}
