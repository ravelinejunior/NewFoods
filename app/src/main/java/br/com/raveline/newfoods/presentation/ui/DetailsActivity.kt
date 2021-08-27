package br.com.raveline.newfoods.presentation.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import br.com.raveline.newfoods.R
import br.com.raveline.newfoods.data.db.favorite.entity.FavoriteEntity
import br.com.raveline.newfoods.databinding.ActivityDetailsBinding
import br.com.raveline.newfoods.presentation.ui.adapter.recipes.PagerAdapter
import br.com.raveline.newfoods.presentation.ui.fragment.detail.IngredientsFragment
import br.com.raveline.newfoods.presentation.ui.fragment.detail.InstructionsFragment
import br.com.raveline.newfoods.presentation.ui.fragment.detail.OverviewFragment
import br.com.raveline.newfoods.presentation.viewmodel.MainViewModel
import br.com.raveline.newfoods.presentation.viewmodel.MainViewModelFactory
import br.com.raveline.newfoods.utils.Constants.Companion.BUNDLE_RECIPE_KEY
import br.com.raveline.newfoods.utils.observeOnce
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    private val args by navArgs<DetailsActivityArgs>()

    private lateinit var pagerAdapter: PagerAdapter

    private lateinit var menuItem: MenuItem

    private lateinit var mainViewModel: MainViewModel

    private var recipeSaved = false
    private var savedRecipeId = 0

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)

        setSupportActionBar(binding.toolbarDetailsId)
        binding.toolbarDetailsId.apply {
            setTitleTextColor(ContextCompat.getColor(this@DetailsActivity, R.color.white))
        }
        supportActionBar?.title = args.recipe.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Inicializar pager Adapter daqui
        initPagerAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite_recipe, menu)
        menuItem = menu!!.findItem(R.id.save_favorite_menu_id)
        checkSavedRecipes(menuItem)
        return super.onCreateOptionsMenu(menu)
    }

    private fun checkSavedRecipes(menuItem: MenuItem) {
        /*Verificar pelo viewModel*/
        mainViewModel.favoritesLiveData.observeOnce(this, { favorites ->
            try {
                for (recipeSaved in favorites)
                    if (recipeSaved.recipe.id == args.recipe.id) {
                        changeMenuItemColor(
                            menuItem,
                            ContextCompat.getDrawable(this, R.drawable.ic_full_saved)!!
                        )

                        savedRecipeId = recipeSaved.id
                        this.recipeSaved = true
                    }
            } catch (e: Exception) {
                showSnackbar(e.message.toString())
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) finish()
        else if (item.itemId == R.id.save_favorite_menu_id && !recipeSaved) {
            saveToFavorites(item)
        }else if(item.itemId == R.id.save_favorite_menu_id && recipeSaved){
            removeFromFavorites(item)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun saveToFavorites(item: MenuItem) {
        val favorite = FavoriteEntity(0, args.recipe)
        mainViewModel.insertFavoriteRecipe(favorite)
        changeMenuItemColor(item, ContextCompat.getDrawable(this, R.drawable.ic_full_saved)!!)
        showSnackbar("Recipe Saved Successfully")
        recipeSaved = true
    }

    private fun removeFromFavorites(item: MenuItem) {
        val favoriteEntity = FavoriteEntity(savedRecipeId, args.recipe)
        mainViewModel.deleteFavoriteRecipe(favoriteEntity)
        changeMenuItemColor(item, ContextCompat.getDrawable(this, R.drawable.ic_save_favorite)!!)
        showSnackbar("Recipe Delete Successfully")
        recipeSaved = false
    }


    private fun changeMenuItemColor(item: MenuItem, icFullSaved: Drawable) {
        item.icon = icFullSaved
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.detailsLayoutId, message, Snackbar.LENGTH_SHORT)
            .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
            .setAction("Okay") {}
            .show()
    }

    private fun initPagerAdapter() {
        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titleArray = ArrayList<String>()
        titleArray.add("Overview")
        titleArray.add("Ingredients")
        titleArray.add("Instructions")

        val bundleResult = Bundle()
        bundleResult.putParcelable(BUNDLE_RECIPE_KEY, args.recipe)

        pagerAdapter = PagerAdapter(bundleResult, fragments, titleArray, supportFragmentManager)

        binding.apply {
            viewPagerDetailsId.adapter = pagerAdapter
            tabLayoutDetailsId.setupWithViewPager(viewPagerDetailsId)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        changeMenuItemColor(
            menuItem,
            ContextCompat.getDrawable(this, R.drawable.ic_save_favorite)!!
        )
    }
}