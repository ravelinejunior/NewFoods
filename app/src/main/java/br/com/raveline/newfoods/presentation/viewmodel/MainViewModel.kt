package br.com.raveline.newfoods.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.*
import br.com.raveline.newfoods.R
import br.com.raveline.newfoods.data.db.favorite.entity.FavoriteEntity
import br.com.raveline.newfoods.data.db.joke.entity.FoodJokeEntity
import br.com.raveline.newfoods.data.db.recipe.entity.RecipesEntity
import br.com.raveline.newfoods.data.model.joke.FoodJoke
import br.com.raveline.newfoods.data.model.recipe.Recipes
import br.com.raveline.newfoods.domain.usecases.*
import br.com.raveline.newfoods.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(
    private val getRecipesUseCase: GetRecipesUseCase,
    private val saveRecipesDatabaseUseCase: SaveRecipesDatabaseUseCase,
    getFoodRecipesFromDatabaseUseCase: GetFoodRecipesFromDatabaseUseCase,
    private val getSearchedUseCase: GetSearchedUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val getFoodJokeUseCase: GetFoodJokeUseCase,
    private val app: Application
) : AndroidViewModel(app) {

    var recipesLiveData: MutableLiveData<Resource<Recipes>> = MutableLiveData()
    var searchedRecipesLiveData: MutableLiveData<Resource<Recipes>> = MutableLiveData()
    var foodJokeLiveData: MutableLiveData<Resource<FoodJoke>> = MutableLiveData()

    //Room Database
    val recipesLocalLiveData: LiveData<List<RecipesEntity>> =
        getFoodRecipesFromDatabaseUseCase.execute().asLiveData()

    val favoritesLiveData: LiveData<List<FavoriteEntity>> =
        getFavoritesUseCase.executeRead().asLiveData()

    val foodLocalJokeLiveData: LiveData<FoodJokeEntity> =
        getFoodJokeUseCase.executeReadFoodEntity().asLiveData()

    private fun insertRecipes(recipesEntity: RecipesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            saveRecipesDatabaseUseCase.execute(recipesEntity)
        }

    /*FAVORITES*/
    fun insertFavoriteRecipe(favoriteEntity: FavoriteEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            getFavoritesUseCase.executeSave(favoriteEntity)
        }

    fun deleteFavoriteRecipe(favoriteEntity: FavoriteEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            getFavoritesUseCase.executeDeleteSingle(favoriteEntity)
        }

    fun deleteAllFavoritesRecipes() = viewModelScope.launch(Dispatchers.IO) {
        getFavoritesUseCase.executeDeleteAll()
    }

    /*FOOD JOKE*/
    fun getFoodJoke(apiKey: String) = viewModelScope.launch(Dispatchers.Main) {
        getFoodJokeSafeCall(apiKey)
    }

    fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) = viewModelScope.launch {
        getFoodJokeUseCase.executeInsertFoodEntity(foodJokeEntity)
    }

    private suspend fun getFoodJokeSafeCall(apiKey: String) {
        foodJokeLiveData.postValue(Resource.Loading())

        if (isNetworkAvailable(app)) {
            try {
                val response = getFoodJokeUseCase.execute(apiKey)

                foodJokeLiveData.value = handleFoodJokeResponse(response)

                val foodJoke = foodJokeLiveData.value?.data
                if (foodJoke != null) {
                    offlineCacheFoodJoke(foodJoke)
                }
            } catch (e: Exception) {
                foodJokeLiveData.postValue(Resource.Error(e.localizedMessage))
            }
        } else {
            foodJokeLiveData.postValue(Resource.Error("No Internet Connection"))
        }
    }

    // FROM REMOTE NETWORKS
    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesCall(queries)
    }

    // FROM REMOTE SEARCHES
    fun searchRecipes(searchQuery: Map<String, String>) = viewModelScope.launch {
        searchRecipesSafeCall(searchQuery)
    }

    private suspend fun searchRecipesSafeCall(searchQuery: Map<String, String>) {
        searchedRecipesLiveData.postValue(Resource.Loading())
        if (isNetworkAvailable(app)) {
            try {
                val response = getSearchedUseCase.execute(searchQuery)
                searchedRecipesLiveData.value = (handleFoodRecipesResponse(response))

            } catch (e: Exception) {
                searchedRecipesLiveData.postValue(Resource.Error(e.message))
            }
        } else {
            searchedRecipesLiveData.postValue(Resource.Error(app.getString(R.string.no_connection)))
        }
    }

    private suspend fun getRecipesCall(queries: Map<String, String>) {
        if (isNetworkAvailable(app)) {
            try {
                val response = getRecipesUseCase.execute(queries)
                recipesLiveData.value = (handleFoodRecipesResponse(response))

                //saving into database
                val foodRecipe = recipesLiveData.value!!.data
                if (foodRecipe != null) {
                    offlineCacheRecipe(foodRecipe)
                }

            } catch (e: Exception) {
                recipesLiveData.postValue(Resource.Error(e.message))
            }
        } else {
            recipesLiveData.postValue(Resource.Error(app.getString(R.string.no_connection)))
        }
    }

    private fun offlineCacheFoodJoke(foodJoke: FoodJoke) {
        val foodJokeEntity = FoodJokeEntity(foodJoke)
        insertFoodJoke(foodJokeEntity)
    }

    private fun offlineCacheRecipe(foodRecipe: Recipes) {
        //create a entity
        val recipesEntity = RecipesEntity(foodRecipe)
        insertRecipes(recipesEntity)
    }

    //N??o passa de uma fun????o de convers??o com um nome mais bonitinho
    private fun handleFoodRecipesResponse(response: Response<Recipes>): Resource<Recipes> {
        when {
            response.message().toString().contains("timeout") -> {
                return Resource.Error("Timeout")
            }

            response.code() == 402 -> {
                return Resource.Error("API Key Limited!")
            }

            response.body()!!.recipes.isNullOrEmpty() -> {
                return Resource.Error("Recipes not found!")
            }

            response.isSuccessful -> {
                val foodRecipes = response.body()
                return Resource.Success(foodRecipes!!)
            }

            else -> {
                return Resource.Error("General Error.")
            }
        }
    }

    private fun handleFoodJokeResponse(response: Response<FoodJoke>): Resource<FoodJoke> {
        when {
            response.message().toString().contains("timeout") -> {
                return Resource.Error("Timeout")
            }

            response.code() == 402 -> {
                return Resource.Error("API Key Limited!")
            }

            response.isSuccessful -> {
                val foodRecipes = response.body()
                return Resource.Success(foodRecipes!!)
            }

            else -> {
                return Resource.Error("General Error.")
            }
        }
    }


    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) return true
        }

        return false
    }
}