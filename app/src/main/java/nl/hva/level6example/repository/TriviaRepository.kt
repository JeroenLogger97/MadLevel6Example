package nl.hva.level6example.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.withTimeout
import nl.hva.level6example.api.TriviaApi
import nl.hva.level6example.api.TriviaApiService
import nl.hva.level6example.model.Trivia

class TriviaRepository {
    private val triviaApiService: TriviaApiService = TriviaApi.createApi()

    private val _trivia: MutableLiveData<Trivia> = MutableLiveData()

    val trivia: LiveData<Trivia>
        get() = _trivia

    suspend fun getRandomNumberTrivia()  {
        try {
            //timeout the request after 5 seconds
            val result = withTimeout(5_000) {
                triviaApiService.getRandomNumberTrivia()
            }

            _trivia.value = result
        } catch (error: Throwable) {
            throw TriviaRefreshError("Unable to refresh trivia", error)
        }
    }

    class TriviaRefreshError(message: String, cause: Throwable) : Throwable(message, cause)

}
