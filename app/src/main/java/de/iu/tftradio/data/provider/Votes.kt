package de.iu.tftradio.data.provider

interface Votes {

    fun getVotes(): List<String>

    fun setVote(songIdentifier: String)

    fun removeVote(songIdentifier: String)
}