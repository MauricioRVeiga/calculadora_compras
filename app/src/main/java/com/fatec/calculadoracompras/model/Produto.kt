package com.fatec.calculadoracompras.model

data class Produto(
    val nome: String,
    val preco: Double,
    val descricao: String? = null,
    val descontoPercentual: Double = 0.0
) : Pagavel {
    override fun calcularValorTotal(): Double {
        return preco - (preco * descontoPercentual / 100.0)
    }
}
