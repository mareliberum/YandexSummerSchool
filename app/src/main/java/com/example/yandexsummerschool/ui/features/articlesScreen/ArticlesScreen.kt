package com.example.yandexsummerschool.ui.features.articlesScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yandexsummerschool.R
import com.example.yandexsummerschool.ui.common.components.BottomNavigationBar
import com.example.yandexsummerschool.ui.common.components.ListItem
import com.example.yandexsummerschool.ui.common.components.ListItemData
import com.example.yandexsummerschool.ui.common.components.LoadingIndicator
import com.example.yandexsummerschool.ui.common.components.TopAppBar
import com.example.yandexsummerschool.ui.common.components.TopAppBarElement
import com.example.yandexsummerschool.ui.common.screens.BasicEmptyScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesScreen(
    navController: NavController,
    viewModelFactory: ViewModelProvider.Factory,
) {
    val viewModel: ArticlesScreenViewModel = viewModel(factory = viewModelFactory)
    val articleState by viewModel.articlesScreenState.collectAsStateWithLifecycle()
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(TopAppBarElement.Articles, navController) },
        bottomBar = { BottomNavigationBar(navController = navController) },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
            DockedSearchBar(
                shape = SearchBarDefaults.fullScreenShape,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                query = query,
                onQueryChange = { query = it },
                onSearch = { active = false },
                active = active,
                onActiveChange = { },
                placeholder = { Text("Найти статью") },
                trailingIcon = { Icon(Icons.Default.Search, null) },
                colors =
                    SearchBarDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    ),
            ) {
            }

            when (val state = articleState) {
                ArticlesScreenState.Empty ->
                    BasicEmptyScreen(
                        stringResource(R.string.no_articles_now),
                        stringResource(R.string.try_later),
                    )
                ArticlesScreenState.Loading -> LoadingIndicator()
                is ArticlesScreenState.Content -> {
                    var articles = state.articleUiModels
                    if (query.isNotBlank()) {
                        articles = articles.filter { it.categoryName.startsWith(query, ignoreCase = true) }
                    }
                    LazyColumn {
                        items(articles) { article ->
                            val listItemData =
                                ListItemData(
                                    lead = article.emoji,
                                    title = article.categoryName,
                                )
                            ListItem(listItemData, Modifier.height(70.dp))
                        }
                    }
                }
            }
        }
    }
}
