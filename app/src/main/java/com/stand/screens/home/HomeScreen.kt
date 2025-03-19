package com.stand.screens.home

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.stand.R

@Composable
fun HomeScreen(
    viewModel: HomeViewModel
) {
    val state = viewModel.homeState.collectAsState().value

    Home(
        state = state,
        setImageUri = viewModel::setImageUri,
        navigateToCamera = viewModel::navigateToCamera
    )
}

@Composable
fun Home(
    modifier: Modifier = Modifier,
    state: HomeState,
    setImageUri: (Uri?) -> Unit,
    navigateToCamera: () -> Unit,
) {
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        setImageUri(uri)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if(state.imageUri == null) {
            Image(
                painter = painterResource(id = R.drawable.insert_image_here),
                contentDescription = "Selected Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(20.dp)
                    .clickable {
                        navigateToCamera()
                    },
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = rememberAsyncImagePainter(state.imageUri),
                contentDescription = "Selected Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(20.dp)
                    .clickable {
                        navigateToCamera()
                    },
                contentScale = ContentScale.Crop
            )
        }


        Spacer(modifier = modifier.height(20.dp))

        Row(
            modifier = modifier.padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                modifier = modifier.weight(1f),
                onClick = {
                    galleryLauncher.launch("image/*")
                }) {
                Text("Select from gallery")
            }

            Spacer(modifier = modifier.width(10.dp))

            Button(
                modifier = modifier.weight(1f),
                onClick = { /*TODO*/ }) {
                Text("Analyze")
            }
        }
    }
}
