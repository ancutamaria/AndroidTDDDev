package com.am.groovy.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.am.groovy.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistDetailsFragment : Fragment() {

    lateinit var viewModel: PlaylistDetailsViewModel

    @Inject
    lateinit var viewModelFactory: PlaylistDetailsViewModelFactory

    val args: PlaylistDetailsFragmentArgs by navArgs()

    lateinit var playlistdetailsName: TextView
    lateinit var playlistdetailsDetails: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playlist_detail, container, false)
        playlistdetailsName = view.findViewById(R.id.playlistdetails_name)
        playlistdetailsDetails = view.findViewById(R.id.playlistdetails_details)

        val id = args.playlistId

        setupViewModel()

        viewModel.getPlaylistDetails(observePlaylists(id))

        observePlaylist()

        return view
    }

    private fun observePlaylist() {
        viewModel.playlistDetails.observe(this as LifecycleOwner) { playlistDetails ->
            if (playlistDetails.getOrNull() != null) {
                setupUI(playlistDetails)
            } else {
                // TODO
            }
        }
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(PlaylistDetailsViewModel::class.java)
    }

    private fun setupUI(playlistDetails: Result<PlaylistDetails>) {
        playlistdetailsName.text = playlistDetails.getOrNull()!!.name
        playlistdetailsDetails.text = playlistDetails.getOrNull()!!.details
    }

    private fun observePlaylists(id: String) = id

    companion object {

        @JvmStatic
        fun newInstance() =
            PlaylistDetailsFragment()
    }
}