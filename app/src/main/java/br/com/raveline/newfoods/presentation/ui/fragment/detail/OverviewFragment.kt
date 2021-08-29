package br.com.raveline.newfoods.presentation.ui.fragment.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import br.com.raveline.newfoods.R
import br.com.raveline.newfoods.data.model.recipe.Recipe
import br.com.raveline.newfoods.databinding.FragmentOverviewBinding
import br.com.raveline.newfoods.utils.Constants.Companion.BUNDLE_RECIPE_KEY
import coil.load
import org.jsoup.Jsoup

class OverviewFragment : Fragment() {

    private lateinit var binding: FragmentOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentOverviewBinding.inflate(inflater, container, false)

        val args = arguments
        val bundle: Recipe? = args?.getParcelable(BUNDLE_RECIPE_KEY)

        try {
            if (bundle != null) {
                binding.apply {
                    imageRecipeOverviewId.load(bundle.image)
                    titleTextViewOverviewId.text = bundle.title
                    qtdLikeOverviewId.text = bundle.aggregateLikes.toString()
                    qtdTimeOverviewId.text = bundle.readyInMinutes.toString()

                    //description parseada
                    parseHTML(summaryOverviewId, bundle.summary)

                    //preencher de verde os campos caso verdadeiros
                    if (bundle.vegetarian == true) {
                        vegeterianImageViewOverviewId.setColorFilter(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.green
                            )
                        )
                        vegeterianTextViewOverviewId.setTextColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.green
                            )
                        )
                    }

                    if (bundle.vegan == true) {
                        veganImageViewOverviewId.setColorFilter(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.green
                            )
                        )
                        veganTextViewOverviewId.setTextColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.green
                            )
                        )
                    }

                    if (bundle.glutenFree == true) {
                        gluttenImageViewOverviewId.setColorFilter(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.green
                            )
                        )
                        gluttenTextViewOverviewId.setTextColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.green
                            )
                        )
                    }

                    if (bundle.dairyFree == true) {
                        dairyImageViewOverviewId.setColorFilter(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.green
                            )
                        )
                        dairyTextViewOverviewId.setTextColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.green
                            )
                        )
                    }

                    if (bundle.veryHealthy == true) {
                        healthyImageViewOverviewId.setColorFilter(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.green
                            )
                        )
                        healthyTextViewOverviewId.setTextColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.green
                            )
                        )
                    }

                    if (bundle.cheap == true) {
                        cheapImageViewOverviewId.setColorFilter(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.green
                            )
                        )
                        cheapTextViewOverviewId.setTextColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.green
                            )
                        )
                    }
                }


            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return binding.root
    }

    //parsear dados da web para remover as tags de html
    private fun parseHTML(textView: TextView, description: String?) {
        if (description != null) {
            val desc = Jsoup.parse(description).text()
            textView.text = desc
        }
    }

}