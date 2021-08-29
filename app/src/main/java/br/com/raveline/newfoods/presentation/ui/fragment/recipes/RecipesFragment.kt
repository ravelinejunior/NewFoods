package br.com.raveline.newfoods.presentation.ui.fragment.recipes

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.raveline.newfoods.R
import br.com.raveline.newfoods.databinding.FragmentRecipesBinding
import br.com.raveline.newfoods.presentation.ui.adapter.recipes.RecipesAdapter
import br.com.raveline.newfoods.presentation.viewmodel.MainViewModel
import br.com.raveline.newfoods.presentation.viewmodel.MainViewModelFactory
import br.com.raveline.newfoods.presentation.viewmodel.RecipesViewModel
import br.com.raveline.newfoods.presentation.viewmodel.RecipesViewModelFactory
import br.com.raveline.newfoods.utils.Constants.Companion.showErrorSnackBar
import br.com.raveline.newfoods.utils.Resource
import br.com.raveline.newfoods.utils.listeners.NetworkListeners
import br.com.raveline.newfoods.utils.observeOnce
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipesFragment : Fragment(), SearchView.OnQueryTextListener {

    private var recipesBinding: FragmentRecipesBinding? = null
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel

    @Inject
    lateinit var networkListeners: NetworkListeners

    private val args by navArgs<RecipesFragmentArgs>()

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

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        recipesBinding = FragmentRecipesBinding.inflate(inflater, container, false)
        recipesBinding!!.lifecycleOwner = this
        recipesBinding!!.mainViewModel = mainViewModel
        setupRecyclerView()
        setHasOptionsMenu(true)

        //RECUPERA O VALOR DO BACKONLINE E SETA NO VIEWMODEL
        recipesViewModel.readBackOnline.observe(viewLifecycleOwner, {
            recipesViewModel.backOnline = it
        })

        lifecycleScope.launchWhenStarted {

            networkListeners.checkNetworkAvailability(recipesBinding?.root!!.context)
                .collect { status ->
                    Log.d("TAGNETWORK", "onCreateView: $status")
                    recipesViewModel.isConnected = status
                    view?.let { recipesViewModel.showNetworkStatus(it) }

                    recipesBinding!!.floatingActionButton.animation =
                        AnimationUtils.loadAnimation(
                            requireContext(),
                            android.R.anim.fade_in
                        )
                    //leitura de dados para exibição
                    getData()

                    if (!status) {

                        recipesBinding!!.floatingActionButton.animation =
                            AnimationUtils.loadAnimation(
                                requireContext(),
                                android.R.anim.fade_out
                            )
                        recipesBinding!!.floatingActionButton.alpha = 0.1F

                    } else recipesBinding!!.floatingActionButton.alpha = 1F
                }
        }

        recipesBinding!!.floatingActionButton.setOnClickListener {
            if (recipesViewModel.isConnected) {
                findNavController().navigate(R.id.action_recipesFragment_id_to_recipesBottomSheet)

            } else {
                view?.let { it1 -> recipesViewModel.showNetworkStatus(it1) }
            }
        }

        return recipesBinding!!.root

    }

    private fun setupRecyclerView() {

        recipesBinding!!.shimmerRecyclerView.apply {
            adapter = recipesAdapter
            layoutManager = LinearLayoutManager(requireContext())
            showShimmerEffect()
        }

    }

    private fun getData() {
        //VERIFICA SE EXISTE DADOS NO BANCO PRIMEIRO ANTES DE ENVIAR A REQUISIÇÃO

        lifecycleScope.launch {
            mainViewModel.recipesLocalLiveData.observe(viewLifecycleOwner, { recipesDatabase ->
                try {
                    if (recipesDatabase.isNotEmpty() && !args.backFromBottomSheet) {
                        recipesAdapter.setRecipeData(recipesDatabase[0].recipes)
                        Log.i("TAGFRAGMENT", "getData: from database")
                        hideShimmer()
                    } else {
                        requestApiData()
                        Log.i("TAGFRAGMENT", "getData: from api")
                    }
                } catch (e: Exception) {
                    recipesAdapter.setRecipeData(recipesDatabase[0].recipes)
                    Log.i("TAGFRAGMENT", "getData: from database")
                    hideShimmer()
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
                    showErrorSnackBar(recipesBinding!!.root, response.message.toString())
                }

                is Resource.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }


    private fun searchApiData(searchQuery: String) {
        hideShimmer()
        mainViewModel.searchRecipes(recipesViewModel.applySearchQuery(searchQuery))
        mainViewModel.searchedRecipesLiveData.observe(viewLifecycleOwner, { response ->

            when (response) {
                is Resource.Success -> {
                    hideShimmer()
                    val foodRecipe = response
                    foodRecipe.let {
                        recipesAdapter.differ.submitList(it.data?.recipes)
                    }
                }

                is Resource.Error -> {
                    hideShimmer()
                    loadDataFromCache()
                    showErrorSnackBar(recipesBinding!!.root, response.message.toString())
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

    override fun onQueryTextSubmit(query: String?): Boolean {

        if(query != null){
            searchApiData(query)
        }

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_recipes_search, menu)

        val search = menu.findItem(R.id.recipes_search_menu_id)
        val searchView = search.actionView as? SearchView

        searchView?.isSubmitButtonEnabled = true
        searchView?.queryHint = "Ex: Banana Shake, Waffles"
        searchView?.setOnQueryTextListener(this)
    }


    private fun hideShimmer() {
        recipesBinding?.shimmerRecyclerView?.hideShimmer()
    }

    private fun showShimmerEffect() {
        recipesBinding?.shimmerRecyclerView?.showShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        recipesBinding = null
    }


}