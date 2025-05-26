import com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.CollaboratorsScreen
import java.util.UUID
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.CollaboratorViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: EntrepreneurshipViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "entrepreneurship_list"
    ) {
        // ... existing routes ...

        composable(
            route = "collaborators/{entrepreneurshipId}",
            arguments = listOf(
                navArgument("entrepreneurshipId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val entrepreneurshipId = backStackEntry.arguments?.getString("entrepreneurshipId")
            val collaboratorViewModel: CollaboratorViewModel = hiltViewModel()
            entrepreneurshipId?.let {
                CollaboratorsScreen(
                    viewModel = collaboratorViewModel,
                    entrepreneurshipId = UUID.fromString(it)
                )
            }
        }
    }
} 