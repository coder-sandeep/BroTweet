package com.codersandeep.brotweet.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.codersandeep.brotweet.R
import com.codersandeep.brotweet.adapter.TweetsAdapter
import com.codersandeep.brotweet.databinding.ActivityAddTweetBinding
import com.codersandeep.brotweet.models.AddTweetRequest
import com.codersandeep.brotweet.models.Tweet
import com.codersandeep.brotweet.utils.APIResult
import com.codersandeep.brotweet.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddTweetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTweetBinding
    private val homeViewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddTweetBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnAddTweet.setOnClickListener {
            if (binding.etTweetContent.text.toString().isNullOrBlank())
                Toast.makeText(this, "Please enter a tweet", Toast.LENGTH_SHORT).show()
            else
                homeViewModel.addTweet(
                    AddTweetRequest(binding.etTweetContent.text.toString()),
                    tokenManager.getToken().toString()
                )
            homeViewModel.addTweetResponse.observe(this) { it ->
                when (it) {
                    is APIResult.Loading -> {
                        binding.llLoader.visibility = View.VISIBLE
                    }

                    is APIResult.Success -> {
                        binding.llLoader.visibility = View.GONE
                        Toast.makeText(this, "Tweet added successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    }

                    is APIResult.Error -> {
                        binding.llLoader.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}