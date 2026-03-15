package com.iti.azzurra.data.weather.mappers

import androidx.annotation.StringRes
import com.iti.azzurra.R

object WeatherConditionResources {

    data class ConditionRes(
        @param:StringRes val titleResId: Int,
        @param:StringRes val descriptionResId: Int,
    )

    fun getResources(conditionId: Int): ConditionRes = when (conditionId) {
        200 -> ConditionRes(R.string.condition_200_title, R.string.condition_200_desc)
        201 -> ConditionRes(R.string.condition_201_title, R.string.condition_201_desc)
        202 -> ConditionRes(R.string.condition_202_title, R.string.condition_202_desc)
        210 -> ConditionRes(R.string.condition_210_title, R.string.condition_210_desc)
        211 -> ConditionRes(R.string.condition_211_title, R.string.condition_211_desc)
        212 -> ConditionRes(R.string.condition_212_title, R.string.condition_212_desc)
        221 -> ConditionRes(R.string.condition_221_title, R.string.condition_221_desc)
        230 -> ConditionRes(R.string.condition_230_title, R.string.condition_230_desc)
        231 -> ConditionRes(R.string.condition_231_title, R.string.condition_231_desc)
        232 -> ConditionRes(R.string.condition_232_title, R.string.condition_232_desc)
        300 -> ConditionRes(R.string.condition_300_title, R.string.condition_300_desc)
        301 -> ConditionRes(R.string.condition_301_title, R.string.condition_301_desc)
        302 -> ConditionRes(R.string.condition_302_title, R.string.condition_302_desc)
        310 -> ConditionRes(R.string.condition_310_title, R.string.condition_310_desc)
        311 -> ConditionRes(R.string.condition_311_title, R.string.condition_311_desc)
        312 -> ConditionRes(R.string.condition_312_title, R.string.condition_312_desc)
        313 -> ConditionRes(R.string.condition_313_title, R.string.condition_313_desc)
        314 -> ConditionRes(R.string.condition_314_title, R.string.condition_314_desc)
        321 -> ConditionRes(R.string.condition_321_title, R.string.condition_321_desc)
        500 -> ConditionRes(R.string.condition_500_title, R.string.condition_500_desc)
        501 -> ConditionRes(R.string.condition_501_title, R.string.condition_501_desc)
        502 -> ConditionRes(R.string.condition_502_title, R.string.condition_502_desc)
        503 -> ConditionRes(R.string.condition_503_title, R.string.condition_503_desc)
        504 -> ConditionRes(R.string.condition_504_title, R.string.condition_504_desc)
        511 -> ConditionRes(R.string.condition_511_title, R.string.condition_511_desc)
        520 -> ConditionRes(R.string.condition_520_title, R.string.condition_520_desc)
        521 -> ConditionRes(R.string.condition_521_title, R.string.condition_521_desc)
        522 -> ConditionRes(R.string.condition_522_title, R.string.condition_522_desc)
        531 -> ConditionRes(R.string.condition_531_title, R.string.condition_531_desc)
        600 -> ConditionRes(R.string.condition_600_title, R.string.condition_600_desc)
        601 -> ConditionRes(R.string.condition_601_title, R.string.condition_601_desc)
        602 -> ConditionRes(R.string.condition_602_title, R.string.condition_602_desc)
        611 -> ConditionRes(R.string.condition_611_title, R.string.condition_611_desc)
        612 -> ConditionRes(R.string.condition_612_title, R.string.condition_612_desc)
        613 -> ConditionRes(R.string.condition_613_title, R.string.condition_613_desc)
        615 -> ConditionRes(R.string.condition_615_title, R.string.condition_615_desc)
        616 -> ConditionRes(R.string.condition_616_title, R.string.condition_616_desc)
        620 -> ConditionRes(R.string.condition_620_title, R.string.condition_620_desc)
        621 -> ConditionRes(R.string.condition_621_title, R.string.condition_621_desc)
        622 -> ConditionRes(R.string.condition_622_title, R.string.condition_622_desc)
        701 -> ConditionRes(R.string.condition_701_title, R.string.condition_701_desc)
        711 -> ConditionRes(R.string.condition_711_title, R.string.condition_711_desc)
        721 -> ConditionRes(R.string.condition_721_title, R.string.condition_721_desc)
        731 -> ConditionRes(R.string.condition_731_title, R.string.condition_731_desc)
        741 -> ConditionRes(R.string.condition_741_title, R.string.condition_741_desc)
        751 -> ConditionRes(R.string.condition_751_title, R.string.condition_751_desc)
        761 -> ConditionRes(R.string.condition_761_title, R.string.condition_761_desc)
        762 -> ConditionRes(R.string.condition_762_title, R.string.condition_762_desc)
        771 -> ConditionRes(R.string.condition_771_title, R.string.condition_771_desc)
        781 -> ConditionRes(R.string.condition_781_title, R.string.condition_781_desc)
        800 -> ConditionRes(R.string.condition_800_title, R.string.condition_800_desc)
        801 -> ConditionRes(R.string.condition_801_title, R.string.condition_801_desc)
        802 -> ConditionRes(R.string.condition_802_title, R.string.condition_802_desc)
        803 -> ConditionRes(R.string.condition_803_title, R.string.condition_803_desc)
        804 -> ConditionRes(R.string.condition_804_title, R.string.condition_804_desc)
        else -> ConditionRes(R.string.condition_unknown_title, R.string.condition_unknown_desc)
    }
}