package com.example.yandexsummerschool.ui.screens.incomesScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.yandexsummerschool.R
import com.example.yandexsummerschool.ui.components.BottomNavigationBar
import com.example.yandexsummerschool.ui.components.ErrorScreen
import com.example.yandexsummerschool.ui.components.FloatingActionButton
import com.example.yandexsummerschool.ui.components.ListItem
import com.example.yandexsummerschool.ui.components.ListItemData
import com.example.yandexsummerschool.ui.components.LoadingIndicator
import com.example.yandexsummerschool.ui.components.TopAppBar
import com.example.yandexsummerschool.ui.components.TopAppBarElement
import com.example.yandexsummerschool.ui.components.TrailingIconArrowRight

@Composable
fun IncomesScreen(
    navController: NavController,
    viewModel: IncomesScreenViewModel = hiltViewModel(),
) {
    val incomeState by viewModel.incomesState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { TopAppBar(TopAppBarElement.Incomes, navController) },
        bottomBar = { BottomNavigationBar(navController = navController) },
        floatingActionButton = { FloatingActionButton(navController) },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
            when (val state = incomeState) {
                is IncomeState.Content -> {
                    val incomesList = state.incomes
                    val incomesSum = state.incomeSum
                    val total =
                        ListItemData(
                            title = stringResource(R.string.Sum),
                            trailingText = incomesSum,
                        )

                    ListItem(
                        total,
                        Modifier.background(color = MaterialTheme.colorScheme.secondary),
                    )

                    LazyColumn(modifier = Modifier.padding(vertical = 3.dp)) {
                        items(incomesList) { income ->
                            val listItemData =
                                ListItemData(
                                    lead = income.emoji,
                                    title = income.categoryName,
                                    trailingText = income.amount,
                                    trailingIcon = { TrailingIconArrowRight() },
                                )
                            ListItem(
                                listItemData = listItemData,
                                modifier = Modifier.height(70.dp),
                            )
                        }
                    }
                }
                IncomeState.Empty -> Text("Пусто")
                IncomeState.Loading -> LoadingIndicator()
                is IncomeState.Error -> ErrorScreen("Ошибка")
            }
        }
    }
}
