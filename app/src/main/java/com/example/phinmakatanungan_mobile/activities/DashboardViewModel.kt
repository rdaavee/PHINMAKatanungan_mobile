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
    private val _posts = MutableLiveData<Map<String, Map<String, List<Post>>>>()
    val posts: LiveData<Map<String, Map<String, List<Post>>>> get() = _posts

    fun fetchPosts() {
        val call: Call<PostResponse> = phinmaApi.getPosts()

        call.enqueue(object : Callback<PostResponse> {
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                if (response.isSuccessful) {
                    val postResponse: PostResponse? = response.body()
                    val posts: List<Post> = postResponse?.posts ?: emptyList()
                    // Group posts first by department, then by course
                    val postsMap = posts.groupBy { it.user.department_id }
                        .mapValues { (_, posts) -> posts.groupBy { it.user.course_id } }
                    _posts.value = postsMap
                } else {
                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
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