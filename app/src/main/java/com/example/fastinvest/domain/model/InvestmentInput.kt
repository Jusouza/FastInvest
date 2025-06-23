package com.example.fastinvest.domain.model

data class InvestmentInput(
    val amount: Float,
    val months: Int,
    val incomeRate: Float,
    val feeRate: Float? = null
)