package net.smartgekko.githubclient.repo

import io.reactivex.subjects.BehaviorSubject

class LoginProducer {
    private val TIMEOUT = 1000L
    private val LOGINS_COUNT = 10
    private var generatorEnabled: Boolean = true
    private var randomUserLogin: String = generateNewLogin()
    private val behaviorSubject = BehaviorSubject.createDefault(randomUserLogin)

    init {
        turnOnGenerator()
    }

    fun getLogin() = behaviorSubject

    private fun loginChanged() {
        behaviorSubject.onNext(randomUserLogin)
    }

    private fun turnOnGenerator() {
        var counter = 0
        Thread {
            while (generatorEnabled) {
                Thread.sleep(TIMEOUT)
                randomUserLogin = generateNewLogin()
                loginChanged()
                counter++
                if (counter == LOGINS_COUNT-1) {
                    generatorEnabled = false
                    behaviorSubject.onComplete()
                }
            }
        }.start()
    }

    private fun generateNewLogin(): String {
        return "user_" + (1..3)
            .map { kotlin.random.Random.nextInt(0, 9) }
            .joinToString("")
    }
}