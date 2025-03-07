package dev.himanshu.inshorts.task.mealsearchapprxjava.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.SubcomposeAsyncImage
import dev.himanshu.inshorts.task.mealsearchapprxjava.view.ui.theme.MealSearchAppRXJavaTheme
import dev.himanshu.inshorts.task.mealsearchapprxjava.viewModel.MealViewModel
import dev.himanshu.inshorts.task.mealsearchapprxjava.viewModel.UiState

class MainActivity : ComponentActivity() {

    val viewModel : MealViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MealSearchAppRXJavaTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                    var query by rememberSaveable { mutableStateOf("") }
                    MainContent(
                        modifier = Modifier.padding(innerPadding),
                        uiState = uiState,
                        value = query,
                        onValueChange = {
                            query = it
                            viewModel.updateQuery(query)
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun MainContent(
    modifier: Modifier = Modifier, uiState: UiState,
    value: String, onValueChange: (String) -> Unit
) {

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TextField(
                value = value, onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                ),
                placeholder = {
                    Text(text = "Search here....")
                }
            )
        }) { innerPadding ->

        if (uiState.isLoading) {
            Box(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        if (uiState.error.isNotEmpty()) {
            Box(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text(uiState.error)
            }
        }

        uiState.results?.let { data ->

            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {

                items(data) {
                    Card(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column {

                            SubcomposeAsyncImage(
                                model = it.strMealThumb,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(320.dp),
                                loading = {
                                    Box(
                                        Modifier
                                            .fillMaxWidth()
                                            .height(300.dp), contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                },
                                contentScale = ContentScale.Crop
                            )
                            Spacer(Modifier.height(12.dp))
                            Text(
                                text = it.strMeal, style = MaterialTheme.typography.headlineMedium,
                                modifier = Modifier
                                    .padding(horizontal = 12.dp)
                                    .fillMaxWidth()
                            )
                            Spacer(Modifier.height(12.dp))
                            Text(
                                text = it.strInstructions,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxWidth(),
                                maxLines = 4,
                                overflow = TextOverflow.Ellipsis
                            )

                        }

                    }
                }

            }

        }


    }

}