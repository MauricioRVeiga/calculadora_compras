package com.fatec.calculadoracompras.domain

import com.fatec.calculadoracompras.model.ItemCarrinho

object CalculadoraCarrinho {

    fun subtotalBruto(itens: List<ItemCarrinho>): Double {
        return itens.sumOf { it.produto.preco * it.quantidade }
    }

    fun totalDescontos(itens: List<ItemCarrinho>): Double {
        return itens.sumOf { item ->
            val precoOriginal = item.produto.preco * item.quantidade
            val precoComDesconto = item.calcularValorTotal()
            precoOriginal - precoComDesconto
        }
    }

    fun totalFinal(itens: List<ItemCarrinho>): Double {
        return itens.sumOf { it.calcularValorTotal() }
    }
}
