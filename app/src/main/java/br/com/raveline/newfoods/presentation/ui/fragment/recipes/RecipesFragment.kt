package br.com.raveline.newfoods.presentation.ui.fragment.recipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.raveline.newfoods.databinding.FragmentRecipesBinding
import br.com.raveline.newfoods.presentation.ui.adapter.recipes.RecipesAdapter
import br.com.raveline.newfoods.presentation.viewmodel.MainViewModel
import br.com.raveline.newfoods.presentation.viewmodel.MainViewModelFactory
import br.com.raveline.newfoods.presentation.viewmodel.RecipesViewModel
import br.com.raveline.newfoods.presentation.viewmodel.RecipesViewModelFactory
import br.com.raveline.newfoods.utils.Constants.Companion.showErrorSnackBar
import br.com.raveline.newfoods.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private lateinit var recipesBinding: FragmentRecipesBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel

    @Inject
    lateinit var factory: MainViewModelFactory

    @Inject
    lateinit var recipesFactory: RecipesViewModelFactory

    private val recipesAdapter by lazy { RecipesAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        recipesViewModel = ViewModelProvider(this, recipesFactory).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        recipesBinding = FragmentRecipesBinding.inflate(inflater, container, false)
        setupRecyclerView()
        lifecycleScope.launchWhenCreated {
            getData()
        }

        return recipesBinding.root

    }

    private fun setupRecyclerView() {

        recipesBinding.shimmerRecyclerView.apply {
            adapter = recipesAdapter
            layoutManager = LinearLayoutManager(requireContext())
            showShimmerEffect()
        }

    }

    private fun getData() {
        //VERIFICA SE EXISTE DADOS NO BANCO PRIMEIRO ANTES DE ENVIAR A REQUISIÇÃO

        lifecycleScope.launch {
            mainViewModel.recipesLocalLiveData.observe(viewLifecycleOwner, { recipesDatabase ->
                if (recipesDatabase.isNotEmpty()) {
                    recipesAdapter.setRecipeData(recipesDatabase[0].recipes)
                    Log.i("TAGFRAGMENT", "getData: from database")
                    hideShimmer()
                } else {
                    requestApiData()
                    Log.i("TAGFRAGMENT", "getData: from api")
                }
            })
        }
    }


    private fun requestApiData() {
        mainViewModel.getRecipes(recipesViewModel.applyQueries())

        mainViewModel.recipesLiveData.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    hideShimmer()
                    response.data.let { recipe ->
                        recipesAdapter.differ.submitList(recipe!!.recipes)
                    }
                }

                is Resource.Error -> {
                    hideShimmer()
                    loadDataFromCache()
                    showErrorSnackBar(recipesBinding.root, response.message.toString())
                }

                is Resource.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.recipesLocalLiveData.observe(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    recipesAdapter.setRecipeData(database[0].recipes)
                }
            })
        }
    }



    private fun hideShimmer() {
        recipesBinding.shimmerRecyclerView.hideShimmer()
    }

    private fun showShimmerEffect() {
        recipesBinding.shimmerRecyclerView.showShimmer()
    }


}