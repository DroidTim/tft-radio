package de.iu.tftradio.data.provider

import android.content.SharedPreferences

internal const val VOTED_SONG_REQUEST_IDENTIFIER = "VOTED_SONG_REQUEST_IDENTIFIER"

class SongRequestVotes(
    val sharedPreferences: SharedPreferences
) : Votes {

    override fun getVotes(): List<String> {
        return sharedPreferences.getStringSet(VOTED_SONG_REQUEST_IDENTIFIER, mutableSetOf())?.toList() ?: emptyList()
    }

    override fun setVote(songIdentifier: String) {
        val newVotedSongIdentifiers = getVotes().toMutableSet()
        newVotedSongIdentifiers.add(songIdentifier)
        sharedPreferences.edit().putStringSet(VOTED_SONG_REQUEST_IDENTIFIER, newVotedSongIdentifiers).apply()
    }

    override fun removeVote(songIdentifier: String) {
        val newVotedSongIdentifiers = getVotes().toMutableSet()
        newVotedSongIdentifiers.removeIf { songIdentifier == it }
        sharedPreferences.edit().putStringSet(VOTED_SONG_REQUEST_IDENTIFIER, newVotedSongIdentifiers).apply()
    }
}