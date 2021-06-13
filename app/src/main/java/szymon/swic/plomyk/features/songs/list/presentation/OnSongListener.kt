package szymon.swic.plomyk.features.songs.list.presentation

import szymon.swic.plomyk.features.songs.domain.model.Song

interface OnSongClickListener {

    fun onSongClick(target_song: Song)

}