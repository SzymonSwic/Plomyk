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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.random.Random

class TunerTestVM : ViewModel() {

//      parameters for recorder configuration:

    private val TAG = "TunerVM";

    val audioSource = MediaRecorder.AudioSource.MIC
    val samplingFrequency = 8000
    val channelConfig = AudioFormat.CHANNEL_IN_STEREO
    val audioEncoding = AudioFormat.ENCODING_PCM_8BIT
    val bufferSize = AudioRecord.getMinBufferSize(samplingFrequency, channelConfig, audioEncoding)
    val blockSize = bufferSize
    val updateOffset = 0
    val updateDelay: Long = 100
    val volumeThreshold: Int = 32000
    var buffer = ShortArray(blockSize)

//      for recording process:

    private var recorder: AudioRecord? = null
    private var RECORDING_FLAG = false

    val frequency: MutableLiveData<Double> by lazy { MutableLiveData<Double>() }

    private fun setRecorder() {
        if(recorder == null) {
            recorder =
                AudioRecord(audioSource, samplingFrequency, channelConfig, audioEncoding, bufferSize)
        }

        Log.d(TAG, "Recorder init")
        Log.d(TAG, recorder?.state.toString())
        Log.d(TAG, recorder?.sampleRate.toString())
    }

    fun startAnalysing() {
        Log.d(TAG, "Start Analysing")
        if(recorder == null) setRecorder()
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
        Log.d(TAG, "Recording Loop")
        while (RECORDING_FLAG) {


            var lastUpdatedFreq = 0.0
            recorder?.read(buffer, 0, blockSize)
            withContext(Main) {
                var currFreq = (calculate(buffer) * 100).roundToInt() / 100.0

                val volume = getBufferVolume(buffer)
                Log.d("Volume", volume.toString())
                if (volume < volumeThreshold) {
                    frequency.value = currFreq
                    lastUpdatedFreq = currFreq
                    delay(updateDelay)
                }
            }
        }
    }

    private fun getBufferVolume(audioData: ShortArray): Double {

        val currBufferSize = audioData.size

        for (p in 0 until currBufferSize - 1) {
            if (audioData[p] < 0) audioData[p] = (audioData[p] * (-1)).toShort()
        }
        return audioData.average()
    }

    private fun calculate(audioData: ShortArray): Double {

        val currBufferSize = audioData.size
        var numCrossing = 0
        for (p in 0 until currBufferSize - 1) {
            if (audioData[p] * audioData[p + 1] <= 0) {
                numCrossing++
            }
        }

        val numSecondsRecorded = bufferSize.toFloat() / samplingFrequency.toFloat()
        val numCycles = (numCrossing / 2).toFloat()
        val frequency = numCycles / numSecondsRecorded

        return frequency.toDouble()
    }
}
