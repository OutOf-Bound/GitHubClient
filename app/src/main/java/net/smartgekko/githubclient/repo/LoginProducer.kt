package net.smartgekko.githubclient.repo

import io.reactivex.subjects.BehaviorSubject

class LoginProducer {
    private val TIMEOUT = 1500L
    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    private var generatorEnabled: Boolean = true;
    private var randomUserLogin: String = generateNewLogin()
    private val behaviorSubject = BehaviorSubject.createDefault<String>(randomUserLogin)

    init {
        turnOnLoginGenerator()
    }

    fun getLogin() = behaviorSubject

    private fun loginChanged() {
        behaviorSubject.onNext(randomUserLogin)
    }

    private fun turnOnLoginGenerator() {
        Thread {
            while (generatorEnabled) {
                Thread.sleep(TIMEOUT)
                randomUserLogin = generateNewLogin()
                loginChanged()
            }

        }.start()
    }

    private fun generateNewLogin(): String{
        return (1..8)
            .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }

    fun turnOffGenerator() {
        generatorEnabled = false;
    }
}