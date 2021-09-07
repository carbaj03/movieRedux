package com.acv.movieredux

import android.app.Application
import com.acv.movieredux.data.APIService
import com.acv.movieredux.domain.actions.MoviesActionsAsync
import com.acv.movieredux.domain.actions.PeopleActionsAsync
import com.acv.movieredux.ui.preferences.AppUserDefaults
import com.acv.movieredux.ui.preferences.settings
import kotlinx.coroutines.Dispatchers

val store = create()
lateinit var movieActions: MoviesActionsAsync
lateinit var peopleActions: PeopleActionsAsync

class App : Application() {
    private val apiService = APIService(Dispatchers.IO, Dispatchers.Main)
    private lateinit var appUserDefaults: AppUserDefaults

    override fun onCreate() {
        super.onCreate()
        appUserDefaults = AppUserDefaults(settings(this))
        movieActions = MoviesActionsAsync(apiService, appUserDefaults)
        peopleActions = PeopleActionsAsync(apiService)
    }
}