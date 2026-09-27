package com.fatec.calculadoracompras.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun ItemCarrinhoRow(
    nome: String,
    descricao: String?,
    precoUnitario: String,
    quantidade: Int,
    valorTotal: String,
    modifier: Modifier = Modifier,
    onAumentarQuantidade: (() -> Unit)? = null,
    onDiminuirQuantidade: (() -> Unit)? = null,
    onRemover: (() -> Unit)? = null
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = nome,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = descricao ?: "Sem descrição",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$precoUnitario  x$quantidade",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = valorTotal,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            if (onAumentarQuantidade != null || onDiminuirQuantidade != null || onRemover != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    if (onDiminuirQuantidade != null) {
                        IconButton(onClick = onDiminuirQuantidade) {
                            Text(text = "−", style = MaterialTheme.typography.titleLarge)
                        }
                    }
                    if (onAumentarQuantidade != null) {
                        IconButton(onClick = onAumentarQuantidade) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "Aumentar quantidade")
                        }
                    }
                    if (onRemover != null) {
                        IconButton(onClick = onRemover) {
                            Icon(imageVector = Icons.Filled.Delete, contentDescription = "Remover item")
                        }
                    }
                }
            }
        }
    }
}
