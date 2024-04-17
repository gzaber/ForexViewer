package com.gzaber.forexviewer.ui.home.composable

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.gzaber.forexviewer.util.RobolectricTestActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ApiKeyDialogTest {

    private var apiKeyText: String = "demo"
    private var isDismissRequest: Boolean = false
    private var confirmRequestText: String = ""

    @get:Rule(order = 0)
    val robolectricTestActivityRule = RobolectricTestActivity()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setupApiKeyDialog() {
        composeTestRule.setContent {
            ApiKeyDialog(
                title = "Api key",
                apiKeyText = apiKeyText,
                confirmText = "Confirm",
                dismissText = "Dismiss",
                onApiKeyTextChanged = { apiKeyText = it },
                onDismissRequest = { isDismissRequest = true },
                onConfirmRequest = { confirmRequestText = it }
            )
        }
    }

    @Test
    fun title_isDisplayed() {
        composeTestRule.onNodeWithText("Api key").assertIsDisplayed()
    }

    @Test
    fun buttons_areDisplayed() {
        with(composeTestRule) {
            onNodeWithText("Confirm").assertIsDisplayed().assertHasClickAction()
            onNodeWithText("Dismiss").assertIsDisplayed().assertHasClickAction()
        }
    }

    @Test
    fun onApiKeyTextChanged_textIsChanged() {
        assert(apiKeyText == "demo")
        composeTestRule.onNodeWithText("demo").performTextInput("123")
        assert(apiKeyText == "123demo")
    }

    @Test
    fun onDismissRequest_invokesWhenClicked() {
        assert(isDismissRequest.not())
        composeTestRule.onNodeWithText("Dismiss").assertIsDisplayed().performClick()
        assert(isDismissRequest)
    }

    @Test
    fun onConfirmRequest_returnsTextWhenClicked() {
        assert(confirmRequestText == "")
        composeTestRule.onNodeWithText("Confirm").assertIsDisplayed().performClick()
        assert(confirmRequestText == "demo")
    }
}