package de.iu.tftradio.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.iu.tftradio.R
import java.io.IOException

@Composable
internal fun ErrorScreen(exception: Throwable, onRetry: () -> Unit) {
    val errorMessage: String
    val errorImage: Painter

    when (exception) {
        is IOException -> {
            errorImage = painterResource(id = R.drawable.ic_error)
            errorMessage = stringResource(id = R.string.check_network_message)
        }

        else -> {
            errorImage = painterResource(id = R.drawable.error)
            errorMessage = stringResource(id = R.string.try_again_message)
        }
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            // Icon
            Image(
                painter = errorImage,
                contentDescription = stringResource(id = R.string.error_Image),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(180.dp)
                    .padding(bottom = 16.dp)
            )
        }

        item {
            // Large text
            Text(
                text = stringResource(id = R.string.error_message),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .padding(top = 60.dp)
            )
        }

        item {
            // Smaller text
            Text(
                text = errorMessage,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 50.dp)
            )
        }

        item {
            // Retry Button
            Button(
                onClick = onRetry,
                modifier = Modifier.padding(bottom = 50.dp)
            ) {
                Text(text = stringResource(id = R.string.retry_button_text))
            }
        }

        item {
            when (exception.localizedMessage) {
                is String -> {
                    //Description
                    Text(
                        text = exception.localizedMessage ?: "",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }
        }
    }

}

@Preview
@Composable
fun PreviewErrorScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        color = MaterialTheme.colorScheme.background,
    ) {
        ErrorScreen(
            exception = ArithmeticException("Ein Fehler ist aufgetreten, tut uns Leid"),
            onRetry = {})
    }
}
