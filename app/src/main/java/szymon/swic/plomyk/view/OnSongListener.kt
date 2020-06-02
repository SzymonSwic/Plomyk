package szymon.swic.plomyk.view

import szymon.swic.plomyk.model.Song

interface OnSongClickListener {

    fun onSongClick(target_song: Song)

}