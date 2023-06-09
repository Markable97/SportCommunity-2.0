import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class LaunchMainBuilder(
    private val coroutineScope: CoroutineScope
) {
    private var onErrorAction: (Throwable) -> Unit = {}
    private var onStartAction: () -> Unit = {}
    private var onFinalAction: () -> Unit = {}

    fun doOnStart(action: () -> Unit = {}): LaunchMainBuilder {
        this.onStartAction = action
        return this
    }

    fun doFinally(action: () -> Unit = {}): LaunchMainBuilder {
        this.onFinalAction = action
        return this
    }

    fun onError(action: (Throwable) -> Unit = {}): LaunchMainBuilder {
        this.onErrorAction = action
        return this
    }

    fun start(block: suspend CoroutineScope.() -> Unit = {}): Job {
        val handler = createCoroutineExceptionHandler { exception ->
            onErrorAction(exception)
            onFinalAction()
        }

        return coroutineScope.launch(handler) {
            onStartAction()
            block()
            onFinalAction()
        }
    }
}

fun ViewModel.launchInMain(coroutineScope: CoroutineScope = viewModelScope) =
    LaunchMainBuilder(coroutineScope)

fun ViewModel.observeOnMain(
    errorCallback: (Throwable) -> Unit = {},
    block: suspend CoroutineScope.() -> Unit
): Job {
    val handler = createCoroutineExceptionHandler { errorCallback(it) }
    return viewModelScope.launch(handler) {
        block()
    }
}

inline fun createCoroutineExceptionHandler(
    crossinline errorCallback: (Throwable) -> Unit
): CoroutineExceptionHandler =
    CoroutineExceptionHandler { _, throwable ->
        Timber.w(throwable)
        errorCallback(throwable)
    }