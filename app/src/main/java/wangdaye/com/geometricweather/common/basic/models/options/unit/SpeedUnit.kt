package wangdaye.com.geometricweather.common.basic.models.options.unit

import android.content.Context
import wangdaye.com.geometricweather.R
import wangdaye.com.geometricweather.common.basic.models.options._basic.UnitEnum
import wangdaye.com.geometricweather.common.basic.models.options._basic.Utils
import wangdaye.com.geometricweather.common.utils.DisplayUtils

// actual speed = speed(km/h) * factor.
enum class SpeedUnit(
    override val id: String,
    override val unitFactor: Float
): UnitEnum<Float> {

    KPH("kph", 1f),
    MPS("mps", 1f / 3.6f),
    KN("kn", 1f / 1.852f),
    MPH("mph", 1f / 1.609f),
    FTPS("ftps", 0.9113f);

    companion object {

        @JvmStatic
        fun getInstance(
            value: String
        ) = when (value) {
            "mps" -> MPS
            "kn" -> KN
            "mph" -> MPH
            "ftps" -> FTPS
            else -> KPH
        }
    }

    override val valueArrayId = R.array.speed_unit_values
    override val nameArrayId = R.array.speed_units
    override val voiceArrayId = R.array.speed_unit_voices

    override fun getName(context: Context) = Utils.getName(context, this)

    override fun getVoice(context: Context) = Utils.getVoice(context, this)

    override fun getValueWithoutUnit(valueInDefaultUnit: Float) = valueInDefaultUnit * unitFactor

    override fun getValueInDefaultUnit(valueInCurrentUnit: Float) = valueInCurrentUnit / unitFactor

    override fun getValueTextWithoutUnit(
        valueInDefaultUnit: Float
    ) = Utils.getValueTextWithoutUnit(this, valueInDefaultUnit, 1)!!

    override fun getValueText(
        context: Context,
        valueInDefaultUnit: Float
    ) = getValueText(context, valueInDefaultUnit, DisplayUtils.isRtl(context))

    override fun getValueText(
        context: Context,
        valueInDefaultUnit: Float,
        rtl: Boolean
    ) = Utils.getValueText(
        context = context,
        enum = this,
        valueInDefaultUnit = valueInDefaultUnit,
        decimalNumber = 1,
        rtl = rtl
    )

    override fun getValueVoice(
        context: Context,
        valueInDefaultUnit: Float
    ) = getValueVoice(context, valueInDefaultUnit, DisplayUtils.isRtl(context))

    override fun getValueVoice(
        context: Context,
        valueInDefaultUnit: Float,
        rtl: Boolean
    ) = Utils.getVoiceText(
        context = context,
        enum = this,
        valueInDefaultUnit = valueInDefaultUnit,
        decimalNumber = 1,
        rtl = rtl
    )
}