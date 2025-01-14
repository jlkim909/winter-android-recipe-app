package com.surivalcoding.composerecipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.surivalcoding.composerecipeapp.task.day2.TaskSubmissionPressedButton
import com.surivalcoding.composerecipeapp.ui.theme.ComposeRecipeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeRecipeAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .background(color = MaterialTheme.colorScheme.onPrimary)
                    ) {
                        TaskSubmissionPressedButton()
//                        DynamicAsyncImage(
//                            imageUrl = "https://t1.daumcdn.net/cfile/tistory/992371395B3B0B2731",
//                            contentDescription = null,
//                            modifier = Modifier.size(80.dp)
//                        )
                    }
                }
            }
        }
    }
}

