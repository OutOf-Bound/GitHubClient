package net.smartgekko.githubclient.repo

interface ControlableUser {
    fun subscribeOnActionBus()
    fun sendAnalytics(userState: Int)
}