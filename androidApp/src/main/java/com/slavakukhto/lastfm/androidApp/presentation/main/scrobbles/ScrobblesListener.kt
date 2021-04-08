package com.slavakukhto.lastfm.androidApp.presentation.main.scrobbles

import com.slavakukhto.lastfm.shared.domain.models.ScrobblesTrack

interface ScrobblesListener {

    fun onScrobblesTrackClicked(scrobblesTrack: ScrobblesTrack)

    fun onScrobblesMoreClicked()
}
