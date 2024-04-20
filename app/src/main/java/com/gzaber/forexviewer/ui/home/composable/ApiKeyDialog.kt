package com.gzaber.forexviewer.ui.home.composable

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.gzaber.forexviewer.ui.theme.ForexViewerTheme

const val TAG_API_KEY_DIALOG = "apiKeyDialogTag"

@Composable
fun ApiKeyDialog(
    title: String,
    apiKeyText: String,
    confirmText: String,
    dismissText: String,
    onApiKeyTextChanged: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onConfirmRequest: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier.testTag(TAG_API_KEY_DIALOG),
        title = {
            Text(title)
        },
        text = {
            OutlinedTextField(
                value = apiKeyText,
                singleLine = true,
                onValueChange = onApiKeyTextChanged
            )
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = { onConfirmRequest(apiKeyText) }
            ) {
                Text(text = confirmText)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text(text = dismissText)
            }
        }
    )
}

@Preview
@Composable
fun ApiKeyDialogPreview() {
    ForexViewerTheme {
        ApiKeyDialog(
            title = "Api key",
            apiKeyText = "demo",
            confirmText = "Confirm",
            dismissText = "Dismiss",
            onApiKeyTextChanged = {},
            onDismissRequest = {},
            onConfirmRequest = {}
        )
    }
}