package com.glushko.sportcommunity.presentation.core

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp

enum class SearchDisplay {
    /*InitialResults,*//* Suggestions,*/ Results, NoResults
}

@Stable
class SearchState<T>(
    query: TextFieldValue,
    focused: Boolean,
    searching: Boolean,
    //suggestions: List<SuggestionModel>,
    searchResults: List<T>
) {
    var query by mutableStateOf(query)
    var focused by mutableStateOf(focused)
    var searching by mutableStateOf(searching)

    //var suggestions by mutableStateOf(suggestions)
    var searchResults by mutableStateOf(searchResults)

    val searchDisplay: SearchDisplay
        get() = when {
//            !focused && query.text.isEmpty() -> SearchDisplay.Results
//            focused && query.text.isEmpty() -> SearchDisplay.Suggestions
            searchResults.isEmpty() -> SearchDisplay.NoResults
            else -> SearchDisplay.Results
        }

    override fun toString(): String {
        return "🚀 State query: $query, focused: $focused, searching: $searching " +
                //"suggestions: ${suggestions.size}, " +
                "searchResults: ${searchResults.size}, " +
                " searchDisplay: $searchDisplay"

    }
}

@Composable
fun <T> rememberSearchState(
    query: TextFieldValue = TextFieldValue(""),
    focused: Boolean = false,
    searching: Boolean = false,
    //suggestions: List<SuggestionModel> = emptyList(),
    searchResults: List<T> = emptyList()
): SearchState<T> {
    return remember {
        SearchState(
            query = query,
            focused = focused,
            searching = searching,
            //suggestions = suggestions,
            searchResults = searchResults
        )
    }
}

@Composable
private fun SearchHint(hint: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)

    ) {
        Text(
            color = Color(0xff757575),
            text = hint,
        )
    }
}

/**
 * This is a stateless TextField for searching with a Hint when query is empty,
 * and clear and loading [IconButton]s to clear query or show progress indicator when
 * a query is in progress.
 */
@Composable
fun SearchTextField(
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    onClearQuery: () -> Unit,
    searching: Boolean,
    focused: Boolean,
    hint: String,
    modifier: Modifier = Modifier
) {

    val focusRequester = remember { FocusRequester() }

    Surface(
        modifier = modifier
            .then(
                Modifier
                    .height(56.dp)
                    .padding(
                        top = 8.dp,
                        bottom = 8.dp,
                        start = if (!focused) 16.dp else 0.dp,
                        end = 16.dp
                    )
            ),
        color = Color(0xffF5F5F5),
        shape = RoundedCornerShape(percent = 50),
    ) {

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = modifier
            ) {

                if (query.text.isEmpty()) {
                    SearchHint(hint ,modifier.padding(start = 24.dp, end = 8.dp))
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    BasicTextField(
                        value = query,
                        onValueChange = onQueryChange,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .focusRequester(focusRequester)
                            .padding(top = 9.dp, bottom = 8.dp, start = 24.dp, end = 8.dp),
                        singleLine = true
                    )

                    when {
                        searching -> {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(horizontal = 6.dp)
                                    .size(36.dp)
                            )
                        }
                        query.text.isNotEmpty() -> {
                            IconButton(onClick = onClearQuery) {
                                Icon(imageVector = Icons.Filled.Close, contentDescription = null)
                            }
                        }
                    }
                }
            }
        }

    }
}

@ExperimentalAnimationApi
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
    onBack: () -> Unit,
    searching: Boolean,
    focused: Boolean,
    hint: String,
    modifier: Modifier = Modifier
) {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AnimatedVisibility(visible = focused) {
            // Back button
            IconButton(
                modifier = Modifier.padding(start = 2.dp),
                onClick = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    onBack()
                }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        }

        SearchTextField(
            query,
            onQueryChange,
            onClearQuery,
            searching,
            focused,
            hint,
            modifier.weight(1f)
        )
    }
}