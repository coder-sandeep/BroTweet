package com.codersandeep.brotweet.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.codersandeep.brotweet.databinding.ActivitySigninBinding
import com.codersandeep.brotweet.models.LoginRequest
import com.codersandeep.brotweet.models.RegisterRequest
import com.codersandeep.brotweet.utils.APIResult
import com.codersandeep.brotweet.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySigninBinding
    private val signInViewModel: SingInViewModel by viewModels()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySigninBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        goToMainScreen()
        setupClickListeners()
        setUpObservers()
    }

    private fun setupClickListeners() {
        binding.btnToLogin.setOnClickListener(this)
        binding.btnToRegister.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)
        binding.btnStart.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        hideKeyboard()
        when (v?.id) {
            binding.btnToLogin.id -> {
                showLoginForm()
            }

            binding.btnToRegister.id -> {
                showRegisterForm()
            }

            binding.btnRegister.id -> {
                if (binding.tvFirstName.text.isNullOrBlank() || binding.tvLastName.text.isNullOrBlank() || binding.tvEmail.text.isNullOrBlank() || binding.tvPassword.text.isNullOrBlank())
                    Toast.makeText(
                        this, "Please fill all the fields", Toast.LENGTH_SHORT
                    ).show()
                else signInViewModel.register(
                    RegisterRequest(
                        binding.tvFirstName.text.toString(),
                        binding.tvLastName.text.toString(),
                        binding.tvEmail.text.toString(),
                        binding.tvPassword.text.toString()
                    )
                )
            }

            binding.btnStart.id -> {
                goToMainScreen(true)
            }

            binding.btnLogin.id -> {
                if (binding.tvEmailLogin.text.isNullOrBlank() || binding.tvPasswordLogin.text.isNullOrBlank())
                    Toast.makeText(
                        this, "Please fill all the fields", Toast.LENGTH_SHORT
                    ).show()
                else
                    signInViewModel.login(
                        LoginRequest(
                            binding.tvEmailLogin.text.toString(),
                            binding.tvPasswordLogin.text.toString()
                        )
                    )
            }
        }
    }

    private fun setUpObservers() {
        signInViewModel.registerResponse.observe(this) {
            when (it) {
                is APIResult.Loading -> {
                    binding.llLoader.visibility = View.VISIBLE
                }

                is APIResult.Success -> {
                    binding.llLoader.visibility = View.GONE
                    tokenManager.saveToken(it.data.token.toString())
                    signInViewModel.welcome(it.data.token.toString())
                    Toast.makeText(this, "Successfully Registered", Toast.LENGTH_SHORT).show()
                }

                is APIResult.Error -> {
                    binding.llLoader.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        signInViewModel.loginResponse.observe(this) {
            when (it) {
                is APIResult.Loading -> {
                    binding.llLoader.visibility = View.VISIBLE
                }

                is APIResult.Success -> {
                    binding.llLoader.visibility = View.GONE
                    tokenManager.saveToken(it.data.token.toString())
                    signInViewModel.welcome(it.data.token.toString())
                    Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_SHORT).show()
                }

                is APIResult.Error -> {
                    binding.llLoader.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        signInViewModel.welcomeResponse.observe(this) {
            when (it) {
                is APIResult.Loading -> {
                    binding.llLoader.visibility = View.VISIBLE
                }

                is APIResult.Success -> {
                    binding.llLoader.visibility = View.GONE
                    showWelcome()
                    binding.tvWelcomeMessage.text = it.data.message.toString()
                }

                is APIResult.Error -> {
                    binding.llLoader.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showRegisterForm() {
        binding.llLoginForm.visibility = View.GONE
        binding.llRegisterForm.visibility = View.VISIBLE
        binding.rlWelcome.visibility = View.GONE
    }

    private fun showLoginForm() {
        binding.llLoginForm.visibility = View.VISIBLE
        binding.llRegisterForm.visibility = View.GONE
        binding.rlWelcome.visibility = View.GONE
    }

    private fun showWelcome() {
        binding.llLoginForm.visibility = View.GONE
        binding.llRegisterForm.visibility = View.GONE
        binding.rlWelcome.visibility = View.VISIBLE
    }

    private fun goToMainScreen(isButtonClicked: Boolean = false) {
        if (tokenManager.getToken().isNullOrBlank()) {
            if (isButtonClicked)
                Toast.makeText(
                    this, "Something went wrong with token", Toast.LENGTH_SHORT
                ).show()
        } else {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
            hideKeyboard()
        }
    }

    private fun Activity.hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = currentFocus ?: View(this)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}