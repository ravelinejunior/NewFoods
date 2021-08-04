package br.com.raveline.newfoods.presentation.ui.fragment.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.raveline.newfoods.R
import br.com.raveline.newfoods.databinding.FragmentRecipesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RecipesFragment : Fragment() {

    private lateinit var recipesBinding: FragmentRecipesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipesBinding = FragmentRecipesBinding.bind(view)
        
        recipesBinding.shimmerRecyclerView.showShimmer()

        recipesBinding.floatingActionButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                hideShowShimmer()
            }
        }

    }

    private fun hideShowShimmer() {
        if (recipesBinding.ivNoInternetConnectionId.visibility != VISIBLE) {
            recipesBinding.shimmerRecyclerView.showShimmer()
            recipesBinding.shimmerRecyclerView.hideShimmer()
            recipesBinding.ivNoInternetConnectionId.visibility = VISIBLE
        } else {
            recipesBinding.shimmerRecyclerView.hideShimmer()
            recipesBinding.shimmerRecyclerView.showShimmer()
            recipesBinding.ivNoInternetConnectionId.visibility = GONE
        }

    }


}