package com.hu.bugit.ui.screens.home

/**
 * Represents the possible intents that can be sent to the HomeViewModel.
 */
sealed interface HomeIntent {

    data object OnCreateBugClicked : HomeIntent
    data object OnRefreshBugList : HomeIntent
    data class OnBugItemClicked(val url: String) : HomeIntent
    data object OnImageReceived : HomeIntent
}