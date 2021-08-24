package br.com.raveline.newfoods.presentation.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import br.com.raveline.newfoods.R
import br.com.raveline.newfoods.databinding.ActivityDetailsBinding
import br.com.raveline.newfoods.presentation.ui.adapter.recipes.PagerAdapter
import br.com.raveline.newfoods.presentation.ui.fragment.detail.IngredientsFragment
import br.com.raveline.newfoods.presentation.ui.fragment.detail.InstructionsFragment
import br.com.raveline.newfoods.presentation.ui.fragment.detail.OverviewFragment
import br.com.raveline.newfoods.utils.Constants.Companion.BUNDLE_RECIPE_KEY

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    private val args by navArgs<DetailsActivityArgs>()

    private lateinit var pagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarDetailsId)
        binding.toolbarDetailsId.apply {
            setTitleTextColor(ContextCompat.getColor(this@DetailsActivity, R.color.white))
        }
        supportActionBar?.title = args.recipe.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Inicializar pager Adapter daqui
        initPagerAdapter()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) finish()

        return super.onOptionsItemSelected(item)
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
}