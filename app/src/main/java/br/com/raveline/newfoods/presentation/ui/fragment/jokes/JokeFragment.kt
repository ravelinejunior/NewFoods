package br.com.raveline.newfoods.presentation.ui.fragment.jokes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.GONE
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import br.com.raveline.newfoods.R
import br.com.raveline.newfoods.databinding.FragmentJokeBinding
import br.com.raveline.newfoods.presentation.viewmodel.MainViewModel
import br.com.raveline.newfoods.presentation.viewmodel.MainViewModelFactory
import br.com.raveline.newfoods.utils.Constants.Companion.API_KEYA
import br.com.raveline.newfoods.utils.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class JokeFragment : Fragment() {

    private var binding: FragmentJokeBinding? = null

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private lateinit var mainViewModel: MainViewModel

    private var foodJoke = "No Food Joke"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
        setHasOptionsMenu(true)
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
                    binding?.foodJokeTextViewFragmentId?.text = response.data?.text
                    foodJoke = response.data!!.text
                }
                is Resource.Error -> {
                    loadDataFromCache()
                    //showSnackBar(response.message.toString())
                }

                else -> Log.d(JokeFragment::class.java.canonicalName, "Loading: ")
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
        lifecycleScope.launch {
            mainViewModel.foodLocalJokeLiveData.observe(viewLifecycleOwner, { database ->
                if (database != null) {
                    binding!!.foodJokeTextViewFragmentId.text = database.foodJoke.text
                    foodJoke = database.foodJoke.text
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.share_foodjoke_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.share_foodJoke_menu_id){
            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT,foodJoke)
                this.type = "text/plain"
            }
            startActivity(shareIntent)
        }
        return super.onOptionsItemSelected(item)
    }

}