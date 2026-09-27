package com.fatec.calculadoracompras.domain

import android.util.Log
import com.fatec.calculadoracompras.model.ItemCarrinho
import java.text.NumberFormat
import java.util.Locale

object RelatorioCarrinho {

    private const val TAG = "RelatorioCarrinho"

    fun imprimirRelatorio(itens: List<ItemCarrinho>) {
        val formato = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

        val itensComDesconto = itens
            .filter { it.produto.descontoPercentual > 0.0 }
            .map { it.produto.nome to it.calcularValorTotal() }
            .sortedByDescending { it.second }

        Log.d(TAG, "===== Relatório de Produtos com Desconto =====")
        itensComDesconto.forEach { (nome, valorFinal) ->
            Log.d(TAG, "$nome - ${formato.format(valorFinal)}")
        }

        val subtotal = CalculadoraCarrinho.subtotalBruto(itens)
        val descontos = CalculadoraCarrinho.totalDescontos(itens)
        val total = CalculadoraCarrinho.totalFinal(itens)

        Log.d(TAG, "Subtotal bruto: ${formato.format(subtotal)}")
        Log.d(TAG, "Descontos aplicados: ${formato.format(descontos)}")
        Log.d(TAG, "Valor Total Final: ${formato.format(total)}")
        Log.d(TAG, "===============================================")
    }
}
