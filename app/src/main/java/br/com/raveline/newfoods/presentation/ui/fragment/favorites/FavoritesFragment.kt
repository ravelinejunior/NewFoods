package br.com.raveline.newfoods.presentation.ui.fragment.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.raveline.newfoods.R
import br.com.raveline.newfoods.databinding.FragmentFavoritesBinding
import br.com.raveline.newfoods.presentation.ui.adapter.favorites.FavoriteRecipesAdapter
import br.com.raveline.newfoods.presentation.viewmodel.MainViewModel
import br.com.raveline.newfoods.presentation.viewmodel.MainViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    @Inject
    lateinit var favoriteRecipesAdapter: FavoriteRecipesAdapter

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    private lateinit var mainViewModel: MainViewModel
    private lateinit var favoritesBinding: FragmentFavoritesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        favoritesBinding = FragmentFavoritesBinding.inflate(inflater,container,false)
        favoritesBinding.lifecycleOwner = this
        favoritesBinding.mainViewModel = mainViewModel
        favoritesBinding.fAdapter = favoriteRecipesAdapter

        setupRecycler()
        mainViewModel.favoritesLiveData.observe(viewLifecycleOwner,{ favorites ->
            favoriteRecipesAdapter.differ.submitList(favorites)
        })

        return favoritesBinding.root
    }

    private fun setupRecycler(){
        favoritesBinding.recyclerViewFavoriteId.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = favoriteRecipesAdapter
        }
    }

}