package com.morsemaster.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.morsemaster.app.ui.screen.HomeScreen
import com.morsemaster.app.ui.screen.LessonScreen
import com.morsemaster.app.ui.screen.ResultScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
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
            HomeScreen(onStartLesson = { lessonId ->
                navController.navigate(Screen.Lesson.createRoute(lessonId))
            })
        }
        composable(Screen.Lesson.route) { backStackEntry ->
            val lessonId = backStackEntry.arguments?.getString("lessonId")?.toIntOrNull() ?: 0
            LessonScreen(
                lessonId = lessonId,
                onFinished = { correct, total ->
                    navController.navigate(Screen.Result.createRoute(correct, total)) {
                        popUpTo(Screen.Home.route)
                    }
                }
            )
        }
        composable(Screen.Result.route) { backStackEntry ->
            val correct = backStackEntry.arguments?.getString("correct")?.toIntOrNull() ?: 0
            val total = backStackEntry.arguments?.getString("total")?.toIntOrNull() ?: 0
            ResultScreen(
                correct = correct,
                total = total,
                onHome = { navController.navigate(Screen.Home.route) { popUpTo(Screen.Home.route) } }
            )
        }
    }
}
