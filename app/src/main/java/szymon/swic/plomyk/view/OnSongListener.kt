package szymon.swic.plomyk.view

import szymon.swic.plomyk.model.Song

interface OnSongListener {

    fun onSongClick(target_song: Song)

}