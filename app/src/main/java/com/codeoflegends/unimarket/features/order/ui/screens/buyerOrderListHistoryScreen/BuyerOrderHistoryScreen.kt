import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.ui.components.Filter
import com.codeoflegends.unimarket.core.ui.components.InfiniteScrollList
import com.codeoflegends.unimarket.core.ui.components.ListItemComponent
import com.codeoflegends.unimarket.features.order.ui.viewModel.BuyerOrderHistoryViewModel
import com.codeoflegends.unimarket.features.order.ui.viewModel.HistoryOrderActionState
import java.util.UUID

@Composable
fun BuyerOrderHistoryScreen(
    viewModel: BuyerOrderHistoryViewModel = hiltViewModel(),
    manager: NavigationManager? = null,
    onOrderClick: (UUID) -> Unit = {},
) {

    val uiState by viewModel.uiState.collectAsState()
    val actionState by viewModel.actionState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(top = 48.dp, start = 16.dp, end = 16.dp)) {
        // Título: Pedidos
        Text(
            text = "Pedidos",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Componente Filter
        Filter(
            viewModel = viewModel,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        // Lista de órdenes
        InfiniteScrollList(
            items = uiState.orders,
            isLoading = actionState is HistoryOrderActionState.Loading,
            onLoadMore = { viewModel.loadOrders() },
            itemContent = { order ->
                Column {
                    ListItemComponent(
                        id = order.id,
                        image = rememberImagePainter(data = order.clientPhoto),
                        title = order.clientName,
                        subtitle = "${order.productCount} productos ~ $${order.totalPrice}",
                        rightInfo = order.date,
                        tag1 = order.status,
                        tag1Color = when (order.status) {
                            "Pendiente" -> Color.Yellow
                            "Completado" -> Color.Green
                            else -> Color.Gray
                        },
                        modifier = Modifier.clickable {
                            Log.d("OrderListScreen", "Orden seleccionada: ${order.id}")
                            manager?.navController?.navigate(
                                Routes.OrderHistory.createRoute(order.id.toString())
                            )
                        }
                    )
                    Divider(color = Color.LightGray, thickness = 1.dp)
                }
            },
            emptyContent = {
                Text(
                    text = "No hay órdenes disponibles",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        )
    }
}
