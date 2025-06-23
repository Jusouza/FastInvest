package com.example.fastinvest.domain.useCase

import com.example.fastinvest.domain.model.InvestmentInput
import com.example.fastinvest.domain.model.InvestmentResult
import kotlin.math.pow

object InvestmentCalculator {
    fun calculate(input: InvestmentInput): InvestmentResult {
        val grossFinal = input.amount * (1 + input.incomeRate / 100).pow(input.months)
        val grossProfit = grossFinal - input.amount

        return if (input.feeRate != null) {
            val tax = grossProfit * (input.feeRate / 100)
            val netFinal = grossFinal - tax
            InvestmentResult(finalValue = netFinal, profit = netFinal - input.amount)
        } else {
            InvestmentResult(finalValue = grossFinal, profit = grossProfit)
        }
    }
}