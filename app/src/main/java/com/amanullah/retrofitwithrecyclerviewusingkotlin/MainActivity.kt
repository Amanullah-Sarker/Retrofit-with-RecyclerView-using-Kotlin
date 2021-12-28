package com.amanullah.retrofitwithrecyclerviewusingkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.amanullah.retrofitwithrecyclerviewusingkotlin.adapter.TodoAdapter
import com.amanullah.retrofitwithrecyclerviewusingkotlin.databinding.ActivityMainBinding
import retrofit2.HttpException
import java.io.IOException

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        getData()

        binding.refresh.setOnRefreshListener {

            getData()
            binding.refresh.isRefreshing  = false
        }
    }

    private fun getData() = lifecycleScope.launchWhenCreated {
        binding.progressBar.isVisible = true

        val response = try {
            RetrofitInstance.api.getTodos()
        } catch (e: IOException) {
            Log.d(TAG, "IOException, you might not have internet connection")
            Toast.makeText(applicationContext, "IOException, you might not have internet connection", Toast.LENGTH_SHORT).show()
            binding.progressBar.isVisible = false
            return@launchWhenCreated
        } catch (e: HttpException) {
            Toast.makeText(applicationContext, "HttpException, unexpected response", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "HttpException, unexpected response")
            binding.progressBar.isVisible = false
            return@launchWhenCreated
        }

        if (response.isSuccessful && response.body() != null) {
            todoAdapter.todos = response.body()!!
        } else {
            Log.d(TAG, "Response not Successful")
        }

        binding.progressBar.isVisible = false
    }

    private fun setupRecyclerView() = binding.recyclerView.apply {
        todoAdapter = TodoAdapter()
        adapter = todoAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
}