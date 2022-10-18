package xyz.teamgravity.retrofitfileupload.presentation.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import xyz.teamgravity.retrofitfileupload.R
import xyz.teamgravity.retrofitfileupload.presentation.viewmodel.ImageUploadViewModel

@Composable
fun ImageUploadScreen(viewModel: ImageUploadViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        FileUploadWithProgress(
            progress = viewModel.imageProgressState.value,
            isProgressing = viewModel.isImageProgressing.value,
            buttonTag = R.string.upload_image
        ) {
            viewModel.onUploadImage()
        }

        FileUploadWithProgress(
            progress = viewModel.pdfProgressState.value,
            isProgressing = viewModel.isPDFProgressing.value,
            buttonTag = R.string.upload_pdf
        ) {
            viewModel.onUploadPDF()
        }
    }
}

@Composable
fun FileUploadWithProgress(
    progress: Int = 10,
    @StringRes buttonTag: Int = -1,
    isProgressing: Boolean = false,
    onClick: () -> Unit,
) {
    Column {
        Button(onClick = onClick) {
            Text(text = stringResource(id = buttonTag))
        }

        if (isProgressing) {
            LinearProgressIndicator(
                progress = (progress.toFloat() / 100),
//                color = Color.Red,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ImageUploadWithProgressPreview() {
    FileUploadWithProgress() {}
}
