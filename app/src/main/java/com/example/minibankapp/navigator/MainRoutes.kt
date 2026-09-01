package com.example.minibankapp.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.example.feature_auth.ui.presentation.pinSetUp.SetUpPinActivity
import com.example.feature_auth.ui.presentation.pinVerified.VerifiedPinActivity
import com.example.feature_transaction.ui.presentation.TransactionRoute
import com.example.navigation.DeeplinkNavigator
import com.example.ui.presentation.CardInfoRoute
import com.example.ui.presentation.ProfileRoute
import com.example.ui.presentation.TransferRoute


@Composable
fun MainRoutes (
    navController : NavHostController,
    retainedNavigator: RetainedNavigator
){
    LaunchedEffect(navController) {
        retainedNavigator.navController = navController
    }

    NavHost( navController = navController, startDestination = AppRoutes.UserAuth) {

        activity<AppRoutes.UserAuth> {
            activityClass = VerifiedPinActivity::class
        }

        composable<AppRoutes.UserProfile>(
            deepLinks = listOf(
                navDeepLink { uriPattern = DeeplinkNavigator.UserProfile.routeLink })
        ){
            ProfileRoute()
        }


        composable<AppRoutes.CardInfo>(
            deepLinks = listOf(
                navDeepLink { uriPattern = DeeplinkNavigator.CardsInfo.routeLink })
        ){
            CardInfoRoute()
        }

        composable<AppRoutes.Transactions>(
            deepLinks = listOf(
                navDeepLink { uriPattern = "${DeeplinkNavigator.TRANSACTIONS_URL}{cardId}" })
        ){ navStackEntry ->
            val args = navStackEntry.toRoute<AppRoutes.Transactions>()
            TransactionRoute(cardId = args.cardId)
        }

        composable<AppRoutes.Transfer>(
            deepLinks = listOf(
                navDeepLink { uriPattern = DeeplinkNavigator.Transfer.routeLink })
        ){
            TransferRoute()
        }



    }

}