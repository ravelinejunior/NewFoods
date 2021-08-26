package br.com.raveline.newfoods.presentation.ui.fragment.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.raveline.newfoods.data.model.Recipe
import br.com.raveline.newfoods.databinding.FragmentIngredientsBinding
import br.com.raveline.newfoods.presentation.ui.adapter.detail.ingredients.IngredientsAdapter
import br.com.raveline.newfoods.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class IngredientsFragment : Fragment() {

    private lateinit var binding: FragmentIngredientsBinding

    @Inject
    lateinit var ingredientsAdapter: IngredientsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentIngredientsBinding.inflate(inflater, container, false)

        val args = arguments
        val bundle: Recipe? = args?.getParcelable(Constants.BUNDLE_RECIPE_KEY)
        setupRecyclerView()

        bundle?.extendedIngredients?.let {
            ingredientsAdapter.differ.submitList(it)
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerViewIngredientsFragment.apply {
            adapter = ingredientsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

}