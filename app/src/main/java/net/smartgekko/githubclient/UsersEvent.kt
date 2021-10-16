package net.smartgekko.githubclient

class UsersEvent

const val MANUAL_COUNTER_BUS = "man_counter_bus"
const val AUTO_COUNTER_BUS = "auto_counter_bus"
class ActionEvent: EventBus.Event()
class AnalitycsEvent: EventBus.Event()