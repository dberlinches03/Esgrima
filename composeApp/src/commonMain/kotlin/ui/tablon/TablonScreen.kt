package ui.tablon

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import navigation.NavigationState
import navigation.Screen

@Composable
fun TablonScreen(nav: NavigationState) {

    val vm = remember { TablonViewModel() }
    val comp = vm.competicion.value

    if (comp == null) {
        Text("No hay competición cargada.")
        return
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text("Tablón Eliminatorio", style = MaterialTheme.typography.bodyMedium)

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(onClick = { nav.navigateTo(Screen.MenuPrincipal) }) {
                Text("Volver")
            }

            Button(onClick = { vm.avanzarRonda() }) {
                Text("Avanzar Ronda")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(vm.rondas) { rondaIndex, ronda ->
                RondaItem(
                    rondaIndex = rondaIndex,
                    ronda = ronda,
                    onUpdate = { asaltoIndex, t1, t2 ->
                        vm.actualizarAsalto(rondaIndex, asaltoIndex, t1, t2)
                    }
                )
            }
        }
    }
}

@Composable
fun RondaItem(
    rondaIndex: Int,
    ronda: List<models.Asalto>,
    onUpdate: (asaltoIndex: Int, t1: Int, t2: Int) -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(15.dp)) {

            Text("Ronda ${rondaIndex + 1}", style = MaterialTheme.typography.bodyLarge)

            Spacer(Modifier.height(10.dp))

            ronda.forEachIndexed { index, asalto ->

                var t1 by remember { mutableStateOf(asalto.tocados1.toString()) }
                var t2 by remember { mutableStateOf(asalto.tocados2.toString()) }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column {
                        Text("${asalto.tirador1.firstName} vs ${asalto.tirador2.firstName}")
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                        OutlinedTextField(
                            value = t1,
                            onValueChange = {
                                t1 = it
                                val v1 = it.toIntOrNull() ?: 0
                                val v2 = t2.toIntOrNull() ?: 0
                                onUpdate(index, v1, v2)
                            },
                            label = { Text("T1") },
                            modifier = Modifier.width(60.dp)
                        )

                        OutlinedTextField(
                            value = t2,
                            onValueChange = {
                                t2 = it
                                val v1 = t1.toIntOrNull() ?: 0
                                val v2 = it.toIntOrNull() ?: 0
                                onUpdate(index, v1, v2)
                            },
                            label = { Text("T2") },
                            modifier = Modifier.width(60.dp)
                        )
                    }
                }

                Spacer(Modifier.height(10.dp))
            }
        }
    }
}
