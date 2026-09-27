package com.fatec.calculadoracompras.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fatec.calculadoracompras.domain.CalculadoraCarrinho
import com.fatec.calculadoracompras.domain.RelatorioCarrinho
import com.fatec.calculadoracompras.model.ItemCarrinho
import com.fatec.calculadoracompras.model.Produto
import com.fatec.calculadoracompras.ui.components.ItemCarrinhoRow
import com.fatec.calculadoracompras.ui.components.ProdutoCatalogoRow
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarrinhoScreen(catalogo: List<Produto>, itensIniciais: List<ItemCarrinho>) {
    var abaSelecionada by remember { mutableIntStateOf(0) }
    val titulosAbas = listOf("Catálogo", "Carrinho")
    val carrinho = remember { mutableStateListOf(*itensIniciais.toTypedArray()) }

    LaunchedEffect(carrinho.toList()) {
        RelatorioCarrinho.imprimirRelatorio(carrinho)
    }

    fun adicionarProduto(produto: Produto) {
        val indice = carrinho.indexOfFirst { it.produto == produto }
        if (indice >= 0) {
            val itemAtual = carrinho[indice]
            carrinho[indice] = itemAtual.copy(quantidade = itemAtual.quantidade + 1)
        } else {
            carrinho.add(ItemCarrinho(produto = produto, quantidade = 1))
        }
    }

    fun aumentarQuantidade(item: ItemCarrinho) {
        val indice = carrinho.indexOf(item)
        if (indice >= 0) {
            carrinho[indice] = item.copy(quantidade = item.quantidade + 1)
        }
    }

    fun diminuirQuantidade(item: ItemCarrinho) {
        val indice = carrinho.indexOf(item)
        if (indice >= 0) {
            if (item.quantidade > 1) {
                carrinho[indice] = item.copy(quantidade = item.quantidade - 1)
            } else {
                carrinho.removeAt(indice)
            }
        }
    }

    fun removerItem(item: ItemCarrinho) {
        carrinho.remove(item)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Calculadora de Carrinho", style = MaterialTheme.typography.titleLarge) }
            )
        }
    ) { paddingInterno ->
        Column(modifier = Modifier.padding(paddingInterno)) {
            TabRow(selectedTabIndex = abaSelecionada) {
                titulosAbas.forEachIndexed { indice, titulo ->
                    Tab(
                        selected = abaSelecionada == indice,
                        onClick = { abaSelecionada = indice },
                        text = { Text(text = titulo) }
                    )
                }
            }

            when (abaSelecionada) {
                0 -> CatalogoTab(
                    catalogo = catalogo,
                    onAdicionar = ::adicionarProduto
                )
                else -> CarrinhoTab(
                    itens = carrinho,
                    onAumentar = ::aumentarQuantidade,
                    onDiminuir = ::diminuirQuantidade,
                    onRemover = ::removerItem
                )
            }
        }
    }
}

@Composable
private fun CatalogoTab(catalogo: List<Produto>, onAdicionar: (Produto) -> Unit) {
    val formato = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        for (produto in catalogo) {
            ProdutoCatalogoRow(
                nome = produto.nome,
                descricao = produto.descricao,
                precoFormatado = formato.format(produto.preco),
                descontoPercentual = produto.descontoPercentual,
                onAdicionarAoCarrinho = { onAdicionar(produto) }
            )
        }
    }
}

@Composable
private fun CarrinhoTab(
    itens: List<ItemCarrinho>,
    onAumentar: (ItemCarrinho) -> Unit,
    onDiminuir: (ItemCarrinho) -> Unit,
    onRemover: (ItemCarrinho) -> Unit
) {
    val formato = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    Column(modifier = Modifier.fillMaxSize()) {
        if (itens.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Seu carrinho está vazio. Adicione produtos na aba Catálogo.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
                    .verticalScroll(rememberScrollState())
                    .padding(PaddingValues(16.dp)),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                for (item in itens) {
                    ItemCarrinhoRow(
                        nome = item.produto.nome,
                        descricao = item.produto.descricao,
                        precoUnitario = formato.format(item.produto.calcularValorTotal()),
                        quantidade = item.quantidade,
                        valorTotal = formato.format(item.calcularValorTotal()),
                        onAumentarQuantidade = { onAumentar(item) },
                        onDiminuirQuantidade = { onDiminuir(item) },
                        onRemover = { onRemover(item) }
                    )
                }
            }
        }

        HorizontalDivider()

        ResumoCarrinho(itens = itens)
    }
}

@Composable
private fun ResumoCarrinho(itens: List<ItemCarrinho>) {
    val formato = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    val subtotal = CalculadoraCarrinho.subtotalBruto(itens)
    val descontos = CalculadoraCarrinho.totalDescontos(itens)
    val total = CalculadoraCarrinho.totalFinal(itens)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        LinhaResumo(rotulo = "Subtotal", valor = formato.format(subtotal))
        LinhaResumo(rotulo = "Descontos", valor = "-${formato.format(descontos)}")
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "TOTAL", style = MaterialTheme.typography.titleLarge)
            Text(text = formato.format(total), style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Composable
private fun LinhaResumo(rotulo: String, valor: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = rotulo, style = MaterialTheme.typography.bodyLarge)
        Text(text = valor, style = MaterialTheme.typography.bodyLarge)
    }
}
