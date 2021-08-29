package br.com.raveline.newfoods.presentation.ui.fragment.jokes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import br.com.raveline.newfoods.databinding.FragmentJokeBinding
import br.com.raveline.newfoods.presentation.viewmodel.MainViewModel
import br.com.raveline.newfoods.presentation.viewmodel.MainViewModelFactory
import br.com.raveline.newfoods.utils.Constants.Companion.API_KEYA
import br.com.raveline.newfoods.utils.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class JokeFragment : Fragment() {

    private var binding: FragmentJokeBinding? = null

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private lateinit var mainViewModel:MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this,mainViewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentJokeBinding.inflate(inflater, container, false)
        binding!!.mainViewModel = mainViewModel
        binding!!.lifecycleOwner = this

        requestApiData()

        return binding!!.root
    }

    private fun requestApiData() {

        mainViewModel.getFoodJoke(API_KEYA)

        mainViewModel.foodJokeLiveData.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    binding?.progressBarFoodJokeFragmentId?.visibility = GONE
                    binding?.foodJokeTextViewFragmentId?.text = response.data?.text
                }
                is Resource.Error -> {
                    loadDataFromCache()
                    binding?.progressBarFoodJokeFragmentId?.visibility = GONE
                    showSnackBar(response.message.toString())
                }
                is Resource.Loading -> {
                    binding?.progressBarFoodJokeFragmentId?.visibility = VISIBLE
                }
            }
        })
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding!!.root, message, Snackbar.LENGTH_SHORT)
            .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
            .setAction("Okay") {}
            .show()
    }

    private fun loadDataFromCache() {
        lifecycleScope.launchWhenCreated {
            mainViewModel.foodLocalJokeLiveData.observe(viewLifecycleOwner, { database ->
                if (database != null) {
                    binding?.progressBarFoodJokeFragmentId?.visibility = GONE
                    binding!!.foodJokeTextViewFragmentId.text = database.foodJoke.text
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}