package com.codersandeep.brotweet.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.codersandeep.brotweet.adapter.TweetsAdapter
import com.codersandeep.brotweet.databinding.ActivityHomeBinding
import com.codersandeep.brotweet.utils.APIResult
import com.codersandeep.brotweet.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.fabAddTweet.setOnClickListener {
            startActivity(Intent(this, AddTweetActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        setUpTweetsRv()
    }

    private fun setUpTweetsRv() {
        homeViewModel.getTweets(tokenManager.getToken().toString())
        homeViewModel.tweetsResponse.observe(this) { it ->
            when (it) {
                is APIResult.Loading -> {
                    binding.llLoader.visibility = View.VISIBLE
                }

                is APIResult.Success -> {
                    binding.llLoader.visibility = View.GONE
                    binding.rvTweets.adapter = TweetsAdapter(it.data)
                    binding.rvTweets.layoutManager = LinearLayoutManager(this)
                }

                is APIResult.Error -> {
                    binding.llLoader.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}