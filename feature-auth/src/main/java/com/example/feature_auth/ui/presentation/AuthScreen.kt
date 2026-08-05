package com.example.feature_auth.ui.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navigation.DeeplinkNavigator
import com.example.navigation.Route

@Composable
fun AuthScreen(
state : AuthContract.State,
handleIntent : (AuthContract.Intent)-> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Personal Information",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = state.name,
                onValueChange = { handleIntent(AuthContract.Intent.SetName(it)) },
                label = { Text("User Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.surname,
                onValueChange = { handleIntent(AuthContract.Intent.SetSurname(it)) },
                label = { Text("Surname") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.email,
                onValueChange = { handleIntent(AuthContract.Intent.SetEmail(it)) },
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.phone,
                onValueChange = { handleIntent(AuthContract.Intent.SetPhone(it))},
                label = { Text("Phone Number") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick ={ handleIntent(AuthContract.Intent.OnSaveClick)},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = state.buttonEnable
            ) {
                Text(text = "Save", fontSize = 16.sp)
            }

            OutlinedButton(
                onClick = { handleIntent(AuthContract.Intent.OnNextClick(Route.NavigateDeeplinkRoute(
                    DeeplinkNavigator.CardsInfo)))},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = state.buttonEnable
            ) {
                Text(text = "Go to Next Page", fontSize = 16.sp)
            }
        }
    }
    if(state.loading){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator(modifier = Modifier.size(36.dp), color = Color.Black)
        }
    }
}
