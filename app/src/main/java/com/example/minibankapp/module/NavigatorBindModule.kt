package com.example.minibankapp.module

import com.example.minibankapp.navigator.RetainedNavigator
import com.example.navigation.Navigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class NavigatorBindModule {

    @Binds
    abstract fun bindNavigator(navigatorImpl: RetainedNavigator): Navigator
}