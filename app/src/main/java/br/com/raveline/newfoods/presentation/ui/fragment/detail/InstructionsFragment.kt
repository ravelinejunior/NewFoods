package br.com.raveline.newfoods.presentation.ui.fragment.detail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import br.com.raveline.newfoods.data.model.recipe.Recipe
import br.com.raveline.newfoods.databinding.FragmentInstructionsBinding
import br.com.raveline.newfoods.presentation.viewmodel.RecipesViewModel
import br.com.raveline.newfoods.presentation.viewmodel.RecipesViewModelFactory
import br.com.raveline.newfoods.utils.Constants.Companion.BUNDLE_RECIPE_KEY
import br.com.raveline.newfoods.utils.listeners.NetworkListeners
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class InstructionsFragment : Fragment() {

    private lateinit var binding: FragmentInstructionsBinding

    @Inject
    lateinit var recipesViewModelFactory: RecipesViewModelFactory
    private lateinit var recipesViewModel: RecipesViewModel

    @ExperimentalCoroutinesApi
    private lateinit var networkListener: NetworkListeners

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(
            requireActivity(),
            recipesViewModelFactory
        ).get(RecipesViewModel::class.java)
    }

    @ExperimentalCoroutinesApi
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentInstructionsBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Recipe = args?.getParcelable(BUNDLE_RECIPE_KEY)!!

        binding.instructionsWebViewId.apply {
            webViewClient = object : WebViewClient() {}
            val webSiteUrl: String? = myBundle.sourceUrl
            webSiteUrl?.let { loadUrl(it) }
        }

        lifecycleScope.launchWhenResumed {
            networkListener = NetworkListeners()
            networkListener.checkNetworkAvailability(requireContext())
                .collect { status ->
                    recipesViewModel.isConnected = status
                    if (!status) {
                        binding.instructionsWebViewId.visibility = View.GONE
                        binding.imageViewNoConnectionIngredientsId.visibility = View.VISIBLE
                        binding.textViewNoConnectionIngredientsId.visibility = View.VISIBLE
                    } else {
                        binding.instructionsWebViewId.visibility = View.VISIBLE
                        binding.imageViewNoConnectionIngredientsId.visibility = View.GONE
                        binding.textViewNoConnectionIngredientsId.visibility = View.GONE
                    }
                    recipesViewModel.showNetworkStatus(binding.root.rootView)
                }
        }


        return binding.root
    }

}