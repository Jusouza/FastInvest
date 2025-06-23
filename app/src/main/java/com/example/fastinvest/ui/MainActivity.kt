package com.example.fastinvest.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fastinvest.domain.useCase.InvestmentCalculator
import com.example.fastinvest.domain.model.InvestmentInput
import com.example.fastinvest.R
import com.example.fastinvest.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initiateValues()
        areRequiredFieldsFilled()
        calculateFinalValues()
        clearFields()
    }


    private fun calculateInvestments() {
        if (!areRequiredFieldsFilled()) {
            showErrorMessage()
            return
        }

        val totalInvest = binding.edtMonthInvestmentValue.text.toString().toFloat()
        val totalMonths = binding.edtMonthsValue.text.toString().toInt()
        val totalIncome = binding.edtIncomeValue.text.toString().toFloat()
        val totalFees = binding.edtFeesValue.text?.toString()?.toFloatOrNull()

        // Usa a classe separada para calcular
        val input = InvestmentInput(
            amount = totalInvest,
            months = totalMonths,
            incomeRate = totalIncome,
            feeRate = totalFees
        )

        val result = InvestmentCalculator.calculate(input)

        // Exibe o resultado na UI
        binding.tvTotal.text = result.finalValue.toCurrency(result.finalValue)
        binding.tvIncomeTotal.text = result.profit.toCurrency(result.profit)
    }


    private fun areRequiredFieldsFilled(): Boolean {
        return binding.edtMonthInvestmentValue.text?.isNotEmpty() == true &&
                binding.edtMonthsValue.text?.isNotEmpty() == true &&
                binding.edtIncomeValue.text?.isNotEmpty() == true
    }

    private fun showErrorMessage() {
        Snackbar.make(
            binding.root,
            getString(R.string.fill_fields),
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun calculateFinalValues(){
        binding.btnCalculate.setOnClickListener {
            calculateInvestments()
        }
    }

    private fun clearFields(){
        binding.btnClean.setOnClickListener {
            binding.edtMonthInvestmentValue.text?.clear()
            binding.edtMonthsValue.text?.clear()
            binding.edtFeesValue.text?.clear()
            binding.edtIncomeValue.text?.clear()
            binding.tvTotal.text = getString(R.string.fields_empty)
            binding.tvIncomeTotal.text = getString(R.string.fields_empty)
        }
    }

    private fun initiateValues(){
        binding.tvTotal.text = getString(R.string.fields_empty)
        binding.tvIncomeTotal.text = getString(R.string.fields_empty)
    }

    private fun Float.toCurrency(value: Float): String = getString(R.string.value_field, value)

}