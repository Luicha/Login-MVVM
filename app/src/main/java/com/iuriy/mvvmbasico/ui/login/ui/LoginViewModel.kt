package com.iuriy.mvvmbasico.ui.login.ui

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class LoginViewModel : ViewModel() {

    //EstoS son LiveData, un par de valores
    private val _email = MutableLiveData<String>() //_email con _ porque es privado.
    val email : LiveData<String> = _email
    //Haciendo esto, nosotros solo modificamos email, e email va a modificar el estado final _email
    //Se hace solo desde el LoginViewModel.

    private val _password = MutableLiveData<String>() //_email con _ porque es privado.
    val password : LiveData<String> = _password

    private val _loginEnable = MutableLiveData<Boolean>() //_email con _ porque es privado.
    val loginEnable : LiveData<Boolean> = _loginEnable

    private val _isLoading = MutableLiveData<Boolean>() //_email con _ porque es privado.
    val isLoading : LiveData<Boolean> = _isLoading

    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _loginEnable.value = (isValidEmail(email) && isValidPassword(password))
    }

    private fun isValidPassword(password: String): Boolean = password.length > 8

    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()



    //Esto es corrutina
    suspend fun onLoginSelected() {
        _isLoading.value = true
        delay(4000)
        _isLoading.value = false
    }

}