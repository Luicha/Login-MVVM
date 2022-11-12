package com.iuriy.mvvmbasico.ui.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iuriy.mvvmbasico.R
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Login(Modifier.align(Alignment.Center), viewModel) //Este Modifier acá es para que tome el Box como padre en cuanto a x,y
    }
}


@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel) { //acá recibe el modifier del Box

    //esto engancha las vistas al LiveData del viewModel.
    val email : String by viewModel.email.observeAsState(initial = "")
    val password : String by viewModel.password.observeAsState(initial = "")
    val loginEnable: Boolean by viewModel.loginEnable.observeAsState(initial = false)
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()

    if(isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        Column(modifier = modifier) { //y acá lo aplica
            HeaderImage(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.padding(16.dp))
            EmailField(email) { viewModel.onLoginChanged(it , password) }
            Spacer(modifier = Modifier.padding(4.dp))
            PasswordField(password) { viewModel.onLoginChanged(email, it) }
            Spacer(modifier = Modifier.padding(8.dp))
            ForgotPassword(Modifier.align(Alignment.End))
            Spacer(modifier = Modifier.padding(16.dp))
            LoginButton(loginEnable) {
                coroutineScope.launch {
                    viewModel.onLoginSelected()
                }
            }
        }
    }
}

@Composable
fun LoginButton(loginEnable: Boolean , onLoginSelected: () -> Unit) {
    Button(onClick = { onLoginSelected() },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
        backgroundColor = Color(0xFFF9A825) ,
        disabledBackgroundColor = Color(0xFF5F4733) ,
        contentColor = Color.White,
        disabledContentColor = Color.White,
    ), enabled = loginEnable
    ) {
        Text(text = "Iniciar Sesión")
    }
}

@Composable
fun ForgotPassword(modifier: Modifier) {
    Text(
        text = "Olvidé mi contraseña" ,
        modifier = modifier.clickable { },
        fontSize = 12.sp ,
        fontWeight = FontWeight.Bold,

    )
}

@Composable
fun PasswordField(password: String, onTextFieldChanged:(String) -> Unit) {
    TextField(
        value = password ,
        onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(textColor = Color.Blue),
        placeholder = { Text(text = "Contraseña") }
    )
}

@Composable
fun EmailField(email: String, onTextFieldChanged:(String) -> Unit) {

    // var email by remember { mutableStateOf("") }  así sería sin arquitectura  LiveData

    TextField(value = email,
        onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Email")},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Blue,
            backgroundColor = Color(0xFFFFDEDD),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun HeaderImage(modifier: Modifier) {
   Image(painter = painterResource(id = R.drawable.logo) , contentDescription = "Logo", modifier = modifier)
}