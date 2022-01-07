package com.gerhardrvv.litit.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.gerhardrvv.litit.viewmodel.InputTypes.AVAILABLE_COLORS
import com.gerhardrvv.litit.viewmodel.InputTypes.AVAILABLE_LIGHT_BULBS
import com.gerhardrvv.litit.viewmodel.InputTypes.QTY_OF_EACH_LIGHT_BULB_COLOR
import kotlin.random.Random

class MainViewModel() : AndroidViewModel(Application()) {

    fun generateLightBulbs(parameters: Map<String, Int>) {

        val availableColors = mutableListOf<LbColor>()
        for (idx in 0 until parameters.getValue(AVAILABLE_COLORS.value) + 1) {
            availableColors.add(
                LbColor(
                    colorId = idx,
                    qty = parameters.getValue(QTY_OF_EACH_LIGHT_BULB_COLOR.value)
                )
            )
        }

        val availableBulbsList = mutableListOf<LightBulb>()
        for (idx in 0 until parameters.getValue(AVAILABLE_LIGHT_BULBS.value) + 1) {
            getColor(availableColors)?.let {
                LightBulb(
                    id = idx.toLong(),
                    color = it
                )
            }?.let {
                availableBulbsList.add(
                    it
                )
            }
        }

        for (i in availableBulbsList) {
            Log.d("GERHARD", "${i.id} ${i.color.colorId}  ${i.color.qty}")
        }
    }

    private fun getColor(availableColors: MutableList<LbColor>): LbColor? {
        var randomIdx : Int
        var lbColor : LbColor? = null
        while (lbColor == null) {
            randomIdx = Random.nextInt(0, availableColors.size -1)
            if (availableColors[randomIdx].qty > 0){
                lbColor = availableColors[randomIdx]
                availableColors[randomIdx].qty -1
            }
        }
        return lbColor
    }
}

data class LightBulb(
    val id: Long,
    val color: LbColor
)

data class LbColor(
    val colorId: Int,
    val qty: Int
)

enum class InputTypes(val value: String) {
    AVAILABLE_LIGHT_BULBS("lbAvailable"),
    AVAILABLE_COLORS("lbColors"),
    QTY_OF_EACH_LIGHT_BULB_COLOR("LbQtyEachColor"),
    NUMBER_OF_RANDOM_LIGHT_BULBS("lbRandomNumber"),
    REPEATS("lbRepeats")
}
