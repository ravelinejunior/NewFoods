package br.com.raveline.newfoods.presentation.ui.fragment.favorites

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.raveline.newfoods.R
import br.com.raveline.newfoods.data.db.favorite.entity.FavoriteEntity
import br.com.raveline.newfoods.databinding.FragmentFavoritesBinding
import br.com.raveline.newfoods.presentation.ui.adapter.favorites.FavoriteRecipesAdapter
import br.com.raveline.newfoods.presentation.viewmodel.MainViewModel
import br.com.raveline.newfoods.presentation.viewmodel.MainViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private val favoriteRecipesAdapter: FavoriteRecipesAdapter by lazy {
        FavoriteRecipesAdapter(
            requireActivity(),
            mainViewModel
        )
    }

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    private lateinit var mainViewModel: MainViewModel
    private var favoritesBinding: FragmentFavoritesBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        favoritesBinding = FragmentFavoritesBinding.inflate(inflater, container, false)
        favoritesBinding!!.lifecycleOwner = this
        favoritesBinding!!.mainViewModel = mainViewModel
        favoritesBinding!!.fAdapter = favoriteRecipesAdapter

        setupRecycler()
        mainViewModel.favoritesLiveData.observe(viewLifecycleOwner, { favorites ->
            favoriteRecipesAdapter.differ.submitList(favorites)
        })

        return favoritesBinding!!.root
    }

    private fun setupRecycler() {
        favoritesBinding!!.recyclerViewFavoriteId.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = favoriteRecipesAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_delete_all_favorites, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteAll_favoriteRecipe_menu_id) {
            displayDeleteSelections(favoriteRecipesAdapter.differ.currentList)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun displayDeleteSelections(favorites: List<FavoriteEntity>) {
        val alert = AlertDialog.Builder(requireContext())
            .setTitle("Delete the current selection of ${favorites.size} items?")
            .setIcon(
                ContextCompat.getDrawable(
                    requireContext().applicationContext,
                    R.drawable.ic_baseline_delete_24
                )
            )
            .setNegativeButton(
                "Cancel"
            ) { dialog, _ ->
                dialog?.dismiss()
            }

            .setPositiveButton("Confirm") { dialog, _ ->
                Toast.makeText(
                    requireContext().applicationContext,
                    "${favorites.size} Items Deleted Successfully",
                    Toast.LENGTH_SHORT
                ).show()
                mainViewModel.deleteAllFavoritesRecipes()
                dialog.dismiss()

            }

        val dialog = alert.create()
        dialog.show()

    }

    override fun onDestroy() {
        super.onDestroy()
        favoritesBinding = null
        favoriteRecipesAdapter.clearContextualActionMode()
    }

}