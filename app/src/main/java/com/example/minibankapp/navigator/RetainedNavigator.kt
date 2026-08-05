package com.example.minibankapp.navigator

import androidx.core.net.toUri
import androidx.navigation.NavController
import com.example.navigation.DeeplinkNavigator
import com.example.navigation.Navigator
import com.example.navigation.Route
import dagger.hilt.android.scopes.ActivityRetainedScoped
import jakarta.inject.Inject

@ActivityRetainedScoped
class RetainedNavigator @Inject constructor() : Navigator  {

    var navController : NavController?=null

    override fun navigate(route: Route) {
        when(route){
            is Route.NavigateDeeplinkRoute ->{
                navigateWithDeepLink(route.deeplinkNav)
            }
        }
    }

    fun navigateWithDeepLink( deepLinkNav : DeeplinkNavigator){
        when(deepLinkNav) {
            is DeeplinkNavigator.Transfer,
            is DeeplinkNavigator.Transaction,
            is DeeplinkNavigator.CardsInfo -> {
                navController?.navigate(deepLinkNav.routeLink.toUri())
            }
        }
    }

}