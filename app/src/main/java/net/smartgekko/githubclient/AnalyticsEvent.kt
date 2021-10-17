package net.smartgekko.githubclient

class AnalyticsEvent {
    class SendAnalytics:AnalyticsEventBus.Event(){
        val user:String = ""
        val state:Int = 10
    }
}