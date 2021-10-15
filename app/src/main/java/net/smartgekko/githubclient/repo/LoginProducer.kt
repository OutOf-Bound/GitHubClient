package net.smartgekko.githubclient.repo

import io.reactivex.subjects.BehaviorSubject
import java.lang.Exception

class LoginProducer {
    private val TIMEOUT = 1500L
    private val LOGINS_COUNT = 8
    private var generatorEnabled: Boolean = true;
    private var randomUserLogin: String = generateNewLogin()
    private val behaviorSubject = BehaviorSubject.createDefault<String>(randomUserLogin)

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
                if (counter == LOGINS_COUNT)  {
                    generatorEnabled = false
                    behaviorSubject.onComplete()
                }
            }
        }.start()
    }

    private fun generateNewLogin(): String {
        return "user_" + (1..3)
            .map { i -> kotlin.random.Random.nextInt(0, 9) }
            .joinToString("")
    }
}