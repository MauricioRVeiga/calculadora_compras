package com.fatec.calculadoracompras.data

import com.fatec.calculadoracompras.model.Produto

object CatalogoProdutos {

    val produtos: List<Produto> = listOf(
        Produto(
            nome = "Notebook Dell Inspiron 15 3000",
            preco = 3499.00,
            descricao = "Um notebook rápido para o dia a dia, com processador de última geração",
            descontoPercentual = 5.0
        ),
        Produto(
            nome = "Mouse sem fio",
            preco = 89.90,
            descricao = null,
            descontoPercentual = 0.0
        ),
        Produto(
            nome = "Teclado mecânico RGB",
            preco = 349.90,
            descricao = "Switch azul, ABNT2",
            descontoPercentual = 0.0
        ),
        Produto(
            nome = "Monitor Gamer Ultrawide 29 Polegadas Curvo",
            preco = 1899.00,
            descricao = "Tela curva com alta taxa de atualização para jogos",
            descontoPercentual = 10.0
        ),
        Produto(
            nome = "Cadeira Gamer Ergonômica com Apoio Lombar Ajustável",
            preco = 1299.90,
            descricao = "Estrutura reclinável com apoio de braço 4D",
            descontoPercentual = 0.0
        ),
        Produto(
            nome = "SSD Externo Portátil 1TB",
            preco = 549.90,
            descricao = "Armazenamento rápido e compacto para levar a qualquer lugar",
            descontoPercentual = 0.0
        )
    )
}
