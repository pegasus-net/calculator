package com.icarus.calculator.engine

import android.content.Context
import com.icarus.calculator.annotation.CommonCal
import com.icarus.calculator.annotation.TextCal
import com.icarus.calculator.lib.calculator.Calculator
import com.icarus.calculator.lib.calculator.filter.PinyinExprFilter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ActivityComponent::class)
class CalculatorModule {

    @Provides
    @CommonCal
    fun provideCommonCalculator(): CalculatorEngine {
        return CommonCalculator()
    }

    @Provides
    @TextCal
    fun provideTextCalculator(@ApplicationContext context: Context): CalculatorEngine {
        val pinyin: HashMap<Char, String> =
            PinyinExprFilter.loadTokens(context.resources.assets.open("token"))
        return Calculator.createDefault(pinyin)
    }
}