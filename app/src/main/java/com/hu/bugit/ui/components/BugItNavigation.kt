package com.hu.bugit.ui.components

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hu.bugit.ui.components.BugItScreens.BUG_FORM_SCREEN
import com.hu.bugit.ui.components.BugItScreens.HOME_SCREEN
import com.hu.bugit.ui.components.BugItScreens.SETTINGS_SCREEN
import com.hu.bugit.ui.screens.bugForm.BugFormScreen
import com.hu.bugit.ui.screens.home.HomeScreen
import com.hu.bugit.ui.screens.settings.SettingsScreen

/**
 * Composable function that sets up the navigation graph for the BugIt app.
 *
 * This function creates a `NavHost` with three screens: Home, Bug Form, and Settings.
 * The `NavHostController` is used to navigate between these screens based on user actions.
 *
 * @param navController The `NavHostController` used to manage app navigation.
 * @param startDestination The start destination for the navigation graph, defaulting to the home screen.
 */
@Composable
fun BugItNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = HOME_SCREEN
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = HOME_SCREEN) {
            HomeScreen(
                onAddIconButtonClicked = {
                    navController.navigate(route = "$BUG_FORM_SCREEN?${Uri.encode("")}")
                },
                onSettingsIconButtonClicked = {
                    navController.navigate(route = SETTINGS_SCREEN)
                },
                onImageReceived = {
                    navController.navigate(route = "$BUG_FORM_SCREEN?${Uri.encode(it.toString())}")
                }
            )
        }
        composable(
            route = "$BUG_FORM_SCREEN?{imageUri}",
            arguments = listOf(navArgument("imageUri") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            })
        ) { backStackEntry ->
            val imageStringUri = backStackEntry.arguments?.getString("imageUri")
            val imageUri = if (imageStringUri.isNullOrEmpty()) null else Uri.parse(imageStringUri)
            BugFormScreen(
                imageUri = imageUri,
                onBackButtonClicked = {
                    navController.popBackStack(route = HOME_SCREEN, false)
                }
            )
        }
        composable(route = SETTINGS_SCREEN) {
            SettingsScreen(
                onBackButtonClicked = {
                    navController.popBackStack(route = HOME_SCREEN, false)
                }
            )
        }
    }
}

/**
 * Object holding the route constants for the BugIt app's screens.
 */
object BugItScreens {

    const val HOME_SCREEN = "home_screen"
    const val BUG_FORM_SCREEN = "bug_form_screen"
    const val SETTINGS_SCREEN = "settings_screen"
}