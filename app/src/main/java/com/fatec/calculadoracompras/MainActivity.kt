package com.fatec.calculadoracompras

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import com.fatec.calculadoracompras.data.CatalogoProdutos
import com.fatec.calculadoracompras.model.ItemCarrinho
import com.fatec.calculadoracompras.ui.CarrinhoScreen
import com.fatec.calculadoracompras.ui.theme.CalculadoraComprasTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val produtos = CatalogoProdutos.produtos
        val carrinhoInicial = listOf(
            ItemCarrinho(produto = produtos[0], quantidade = 2),
            ItemCarrinho(produto = produtos[1], quantidade = 1),
            ItemCarrinho(produto = produtos[2], quantidade = 1)
        )

        setContent {
            CalculadoraComprasTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    CarrinhoScreen(catalogo = produtos, itensIniciais = carrinhoInicial)
                }
            }
        }
    }
}
