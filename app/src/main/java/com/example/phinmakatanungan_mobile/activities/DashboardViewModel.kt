package com.example.phinmakatanungan_mobile.activities

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.phinmakatanungan_mobile.api.PHINMAApi
import com.example.phinmakatanungan_mobile.models.Post
import com.example.phinmakatanungan_mobile.models.PostResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class DashboardViewModel(private val phinmaApi: PHINMAApi) : ViewModel() {

    // Define a MutableLiveData to hold the list of posts
    private val _posts = MutableLiveData<List<Post>>()

    // Expose a LiveData object to observe posts
    val posts: LiveData<List<Post>> get() = _posts

    // Fetch posts from the API
    fun fetchPosts(query: String? = null) {
        // Use the phinmaApi.getPosts() method to fetch posts from the API
        phinmaApi.getPosts().enqueue(object : Callback<PostResponse> {
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                if (response.isSuccessful) {
                    // Received posts from the API
                    val receivedPosts = response.body()?.posts ?: emptyList()

                    // Filter posts based on the query
                    val filteredPosts = if (query.isNullOrEmpty()) {
                        receivedPosts
                    } else {
                        receivedPosts.filter { post ->
                            post.title.contains(query, ignoreCase = true)
                        }
                    }

                    // Log the received and filtered posts for debugging
                    Log.d("DashboardViewModel", "Received posts: $receivedPosts")
                    Log.d("DashboardViewModel", "Filtered posts: $filteredPosts")

                    // Set the filtered posts to the LiveData
                    _posts.value = filteredPosts
                } else {
                    // Handle unsuccessful response
                    Log.e("DashboardViewModel", "Failed to fetch posts: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                // Handle network errors
                Log.e("DashboardViewModel", "Network error: ${t.message}")
            }
        })
    }
}

class DashboardViewModelFactory(private val phinmaApi: PHINMAApi) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(phinmaApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}