package com.gerhardrvv.litit.viewmodel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gerhardrvv.litit.data.InputTypes.AVAILABLE_COLORS
import com.gerhardrvv.litit.data.InputTypes.AVAILABLE_LIGHT_BULBS
import com.gerhardrvv.litit.data.InputTypes.NUMBER_OF_RANDOM_LIGHT_BULBS
import com.gerhardrvv.litit.data.InputTypes.QTY_OF_EACH_LIGHT_BULB_COLOR
import com.gerhardrvv.litit.data.InputTypes.REPEATS
import com.gerhardrvv.litit.data.models.LightBulbColorModel
import com.gerhardrvv.litit.data.models.LightBulbModel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.random.Random

class MainViewModel : AndroidViewModel(Application()) {

    val averageResult: MutableState<Double> = mutableStateOf(0.0)

    private var listOfUniqueNum = mutableMapOf<Int, Boolean>()
    private var uniqueColorCounter: Double = 0.0

    fun generateLightBulbs(parameters: Map<String, Int>) {

        listOfUniqueNum = mutableMapOf()
        averageResult.value = 0.0

        val numberOfRepeats = parameters.getValue(REPEATS.value)

        viewModelScope.launch {
            for (idx in 0 until numberOfRepeats) {
                val colors = createListOfColors(
                    parameters.getValue(AVAILABLE_COLORS.value) + 1,
                    parameters.getValue(QTY_OF_EACH_LIGHT_BULB_COLOR.value)
                )
                val lightBulbsAvailable = createListOfAvailableLightBulbs(
                    parameters.getValue(AVAILABLE_LIGHT_BULBS.value) + 1,
                    colors
                )
                uniqueColorCounter = + getUniqueNumberOfColors(
                    parameters.getValue(NUMBER_OF_RANDOM_LIGHT_BULBS.value) +1,
                    lightBulbsAvailable
                )
            }
        }
        averageResult.value = (uniqueColorCounter / numberOfRepeats * 100.00).roundToInt() / 100.00
    }

    private fun getUniqueNumberOfColors(
        numberOfRandomLightBulbs: Int,
        lightBulbsAvailable: MutableList<LightBulbModel>
    ): Double {

        for (idx in 0 until numberOfRandomLightBulbs) {
            val randomIdx = Random.nextInt(0, lightBulbsAvailable.size - 1)
            val lb = lightBulbsAvailable[randomIdx]
            if (!listOfUniqueNum.containsKey(lb.color.colorId)) {
                listOfUniqueNum[lb.color.colorId] = true
            }
        }
        return listOfUniqueNum.size.toDouble()
    }

    private fun createListOfAvailableLightBulbs(
        numberOfAvailableLightBulbs: Int,
        listOfAvailableColors: MutableList<LightBulbColorModel>
    ): MutableList<LightBulbModel> {

        val availableBulbsList = mutableListOf<LightBulbModel>()

        for (idx in 0 until numberOfAvailableLightBulbs) {
            LightBulbModel(
                id = idx.toLong(),
                color = getColor(listOfAvailableColors)
            ).let {
                availableBulbsList.add(
                    it
                )
            }
        }
        return availableBulbsList
    }

    private fun createListOfColors(
        numberOfAvailableColors: Int,
        qtyAvailableOfEachColor: Int
    ): MutableList<LightBulbColorModel> {
        val availableColors = mutableListOf<LightBulbColorModel>()
        for (idx in 0 until numberOfAvailableColors) {
            availableColors.add(
                LightBulbColorModel(
                    colorId = idx,
                    qty = qtyAvailableOfEachColor
                )
            )
        }
        return availableColors
    }

    private fun getColor(availableColors: MutableList<LightBulbColorModel>): LightBulbColorModel {
        var randomIdx: Int
        var lightBulbColor: LightBulbColorModel? = null
        while (lightBulbColor == null) {
            randomIdx = Random.nextInt(0, availableColors.size - 1)
            if (availableColors[randomIdx].qty > 0) {
                lightBulbColor = availableColors[randomIdx].apply {
                    qty = (qty - 1)
                }
            }
        }
        return lightBulbColor
    }
}
