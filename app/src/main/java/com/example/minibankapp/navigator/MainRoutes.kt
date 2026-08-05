package com.example.minibankapp.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.example.feature_auth.ui.presentation.AuthRoute
import com.example.feature_transaction.ui.presentation.TransactionRoute
import com.example.feature_transfer.ui.presentation.TransferRoute
import com.example.navigation.DeeplinkNavigator
import com.example.ui.presentation.CardInfoRoute

@Composable
fun MainRoutes (
    navController : NavHostController,
    retainedNavigator: RetainedNavigator
){
    LaunchedEffect(navController) {
        retainedNavigator.navController = navController
    }

    NavHost( navController = navController, startDestination = AppRoutes.UserInfo) {

        composable<AppRoutes.UserInfo>
        {
            AuthRoute()
        }


        composable<AppRoutes.CardInfo>(
            deepLinks = listOf(
                navDeepLink { uriPattern = DeeplinkNavigator.CardsInfo.routeLink })
        ){
            CardInfoRoute()
        }

        composable<AppRoutes.Transactions>(
            deepLinks = listOf(
                navDeepLink { uriPattern = DeeplinkNavigator.Transaction.routeLink })
        ){
            TransactionRoute()
        }

        composable<AppRoutes.Transfer>(
            deepLinks = listOf(
                navDeepLink { uriPattern = DeeplinkNavigator.Transfer.routeLink })
        ){
            TransferRoute()
        }



    }

}