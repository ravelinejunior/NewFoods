package br.com.raveline.newfoods.presentation.ui.fragment.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import br.com.raveline.newfoods.R
import br.com.raveline.newfoods.presentation.viewmodel.RecipesViewModel
import br.com.raveline.newfoods.presentation.viewmodel.RecipesViewModelFactory
import br.com.raveline.newfoods.utils.Constants.Companion.DEFAULT_DIET_TYPE
import br.com.raveline.newfoods.utils.Constants.Companion.DEFAULT_MEAL_TYPE
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_bottom_sheet.view.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class RecipesBottomSheet : BottomSheetDialogFragment() {

    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0

    private lateinit var mView: View
    private lateinit var recipesViewModel: RecipesViewModel

    @Inject
    lateinit var recipesFactory: RecipesViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(this, recipesFactory).get(RecipesViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)

        //OBSERVA OS VALORES SETADOS TODA VEZ QUE O BOTTOMSHEET É ABERTO
        recipesViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner, { value ->
            mealTypeChip = value.selectedMealType
            dietTypeChip = value.selectedDietType
            updateChip(value.selectedMealTypeId, mealType_chipGroup_id)
            updateChip(value.selectedDietTypeId, dietType_chipGroup_id)
        })

        //SELECIONA O TIPO DE MEAL
        mView.mealType_chipGroup_id.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            val selectedMealType = chip.text.toString().toLowerCase(Locale.ROOT)
            mealTypeChip = selectedMealType
            mealTypeChipId = checkedId
        }

        //SELECIONA O TIPO DE DIET
        mView.dietType_chipGroup_id.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            val selectedDietType = chip.text.toString().toLowerCase(Locale.ROOT)
            dietTypeChip = selectedDietType
            dietTypeChipId = checkedId
        }

        //SALVA NO DATASTORE OS VALORES SELECIONADOS NOS BOTTOMSHEETS
        mView.applyButton_sheet_id.setOnClickListener {
            recipesViewModel.saveMealAndDietPreferences(
                mealTypeChip,
                mealTypeChipId,
                dietTypeChip,
                dietTypeChipId
            )

            val action = RecipesBottomSheetDirections.actionRecipesBottomSheetToRecipesFragmentId().setBackFromBottomSheet(true)
            findNavController().navigate(action)
        }

        return mView
    }

    private fun updateChip(chipId: Int, typeChipGroup: ChipGroup) {
        if (chipId != 0) {
            try {
                typeChipGroup.findViewById<Chip>(chipId).isChecked = true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}