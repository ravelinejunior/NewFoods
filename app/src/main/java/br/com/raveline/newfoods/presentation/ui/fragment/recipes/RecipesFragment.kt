package br.com.raveline.newfoods.presentation.ui.fragment.recipes

import android.os.Bundle
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
import br.com.raveline.newfoods.utils.Constants.Companion.API_KEY
import br.com.raveline.newfoods.utils.Constants.Companion.API_KEYA
import br.com.raveline.newfoods.utils.Constants.Companion.DEFAULT_DIET_TYPE
import br.com.raveline.newfoods.utils.Constants.Companion.DEFAULT_MEAL_TYPE
import br.com.raveline.newfoods.utils.Constants.Companion.DEFAULT_RECIPES_NUMBER
import br.com.raveline.newfoods.utils.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import br.com.raveline.newfoods.utils.Constants.Companion.QUERY_API_KEY
import br.com.raveline.newfoods.utils.Constants.Companion.QUERY_DIET
import br.com.raveline.newfoods.utils.Constants.Companion.QUERY_FILL_INGREDIENTS
import br.com.raveline.newfoods.utils.Constants.Companion.QUERY_NUMBER
import br.com.raveline.newfoods.utils.Constants.Companion.QUERY_TYPE
import br.com.raveline.newfoods.utils.Constants.Companion.showErrorSnackBar
import br.com.raveline.newfoods.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private lateinit var recipesBinding: FragmentRecipesBinding
    private lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var factory: MainViewModelFactory

    private val recipesAdapter by lazy { RecipesAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        recipesBinding = FragmentRecipesBinding.inflate(inflater, container, false)
        setupRecyclerView()
        lifecycleScope.launchWhenCreated {
            requestApiData()
        }

        return recipesBinding.root

    }


    private fun requestApiData() {
        mainViewModel.getRecipes(applyQueries())

        mainViewModel.recipesLiveData.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    hideShimmer()
                    response.data.let { recipe ->
                        recipesAdapter.differ.submitList(recipe?.recipes)
                      //  recipesAdapter.setRecipeData(recipe!!)
                    }
                }

                is Resource.Error -> {
                    hideShimmer()
                    showErrorSnackBar(recipesBinding.root, response.message.toString())
                }

                is Resource.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }

    private fun setupRecyclerView() {

        recipesBinding.shimmerRecyclerView.apply {
            adapter = recipesAdapter
            layoutManager = LinearLayoutManager(requireContext())
            showShimmerEffect()
        }

    }

    private fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEYA
        queries[QUERY_TYPE] = DEFAULT_MEAL_TYPE
        queries[QUERY_DIET] = DEFAULT_DIET_TYPE
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries

    }

    private fun hideShimmer() {
        recipesBinding.shimmerRecyclerView.hideShimmer()
    }

    private fun showShimmerEffect() {
        recipesBinding.shimmerRecyclerView.showShimmer()
    }


}