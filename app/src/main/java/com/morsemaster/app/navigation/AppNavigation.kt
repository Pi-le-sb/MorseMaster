package com.morsemaster.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.morsemaster.app.ui.screen.*

sealed class Screen(val route: String) {
    object Home         : Screen("home")
    object Keyboard     : Screen("keyboard")
    object Achievements : Screen("achievements")
    object Settings     : Screen("settings")
    object Review       : Screen("review")
    object Lesson : Screen("lesson/{lessonId}") {
        fun createRoute(lessonId: Int) = "lesson/$lessonId"
    }
    object Result : Screen("result/{correct}/{total}") {
        fun createRoute(correct: Int, total: Int) = "result/$correct/$total"
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                onStartLesson = { navController.navigate(Screen.Lesson.createRoute(it)) },
                onOpenKeyboard = { navController.navigate(Screen.Keyboard.route) },
                onOpenAchievements = { navController.navigate(Screen.Achievements.route) },
                onOpenSettings = { navController.navigate(Screen.Settings.route) },
                onOpenReview = { navController.navigate(Screen.Review.route) }
            )
        }
        composable(Screen.Lesson.route) { back ->
            val lessonId = back.arguments?.getString("lessonId")?.toIntOrNull() ?: 0
            LessonScreen(lessonId = lessonId, onFinished = { correct, total ->
                navController.navigate(Screen.Result.createRoute(correct, total)) { popUpTo(Screen.Home.route) }
            })
        }
        composable(Screen.Result.route) { back ->
            val correct = back.arguments?.getString("correct")?.toIntOrNull() ?: 0
            val total   = back.arguments?.getString("total")?.toIntOrNull() ?: 0
            ResultScreen(correct = correct, total = total,
                onHome = { navController.navigate(Screen.Home.route) { popUpTo(Screen.Home.route) } })
        }
        composable(Screen.Keyboard.route) {
            MorseKeyboardScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Achievements.route) {
            AchievementsScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Settings.route) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Review.route) {
            ReviewScreen(onFinished = { navController.popBackStack() })
        }
    }
}
