package com.acv.movieredux.ui.common

import com.acv.movieredux.domain.AppState
import com.acv.movieredux.redux.Dispatcher

typealias PropsMapper<Props> = (AppState, Dispatcher) -> Props