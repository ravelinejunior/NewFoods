package br.com.raveline.newfoods.presentation.ui.adapter.binding

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import br.com.raveline.newfoods.data.db.joke.entity.FoodJokeEntity
import br.com.raveline.newfoods.data.model.joke.FoodJoke
import br.com.raveline.newfoods.utils.Resource
import com.google.android.material.card.MaterialCardView

class FoodJokeBindingAdapter {

    companion object {

        @BindingAdapter("readApiResponseJoke", "readDatabaseJoke", requireAll = false)
        @JvmStatic
        fun setCardAndProgressVisibility(
            view: View,
            readApiResponse: Resource<FoodJoke>?,
            readDatabase: FoodJokeEntity?
        ) {
            when (readApiResponse) {
                is Resource.Loading -> {
                    when (view) {
                        is ProgressBar -> {
                            view.visibility = VISIBLE
                        }
                        is MaterialCardView -> {
                            view.visibility = GONE
                        }
                    }
                }
                is Resource.Error -> {
                    when (view) {
                        is ProgressBar -> {
                            view.visibility = GONE
                        }

                        is MaterialCardView -> {
                            view.visibility = VISIBLE
                            if (readDatabase == null) {
                                view.visibility = GONE
                            }
                        }
                    }
                }

                is Resource.Success -> {
                    when (view) {
                        is ProgressBar -> {
                            view.visibility = GONE
                        }
                        is MaterialCardView -> {
                            view.visibility = VISIBLE
                        }
                    }
                }
            }
        }

        @BindingAdapter("responseJokeAdapter", "databaseJokeAdapter", requireAll = false)
        @JvmStatic
        fun setErrorViewVisibility(
            view: View,
            apiResponse: Resource<FoodJoke>?,
            database: FoodJokeEntity?
        ) {
            if (database == null) {
                view.visibility = VISIBLE
                if (view is TextView) {
                    if (apiResponse != null) {
                        view.text = apiResponse.message
                    }
                }
            }

            if (apiResponse is Resource.Success) {
                view.visibility = GONE
            }
        }

    }
}