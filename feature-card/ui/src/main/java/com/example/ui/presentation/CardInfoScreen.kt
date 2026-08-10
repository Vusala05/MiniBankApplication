package com.example.ui.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.BalanceSection
import com.example.ui.components.BankCardItem
import com.example.navigation.DeeplinkNavigator
import com.example.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardInfoScreen(
  state : CardInfoContract.State,
  handleIntent : (CardInfoContract.Intent) -> Unit
) {
    Scaffold(
        topBar = {
            Row(modifier = Modifier.fillMaxWidth().padding(top = 50.dp).padding(horizontal = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Transfer",
                    fontSize = 15.sp,
                    modifier = Modifier.clickable{
                        handleIntent(CardInfoContract.Intent.OnNavigateBalanceTransfer(Route.NavigateDeeplinkRoute(
                            DeeplinkNavigator.Transfer)))
                    }

                )
                Text(
                    text = "Transaction",
                    fontSize = 15.sp,
                    modifier = Modifier.clickable{
                        handleIntent(CardInfoContract.Intent.OnNavigateBalanceTransfer(Route.NavigateDeeplinkRoute(
                            DeeplinkNavigator.Transaction)))
                    }

                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(state.cardWithBalanceList){ item ->
                BankCardItem(
                    card = item.cardDO,
                    isLoading = state.isCardSectionLoading
                )
                BalanceSection(
                   balanceUiState = item.balanceUiState
                )

            }
        }
    }
    }


