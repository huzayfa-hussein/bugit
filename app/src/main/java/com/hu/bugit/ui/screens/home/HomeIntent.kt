package com.hu.bugit.ui.screens.home

sealed interface HomeIntent {

    data object OnCreateBugClicked : HomeIntent
    data object OnRefreshBugList : HomeIntent
    data class OnBugItemClicked(val url: String) : HomeIntent
    data object OnImageReceived : HomeIntent
}