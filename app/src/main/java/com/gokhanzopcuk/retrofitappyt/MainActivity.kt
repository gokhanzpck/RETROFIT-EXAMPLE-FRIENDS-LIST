package com.gokhanzopcuk.retrofitappyt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gokhanzopcuk.retrofitappyt.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var rvMain: RecyclerView
    lateinit var myAdapter: MyAdapter
    private var nl = ArrayList<UsersItem>()
    var BASE_URL = "https://api.github.com"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    ara(newText)
                }
                return true
            }


        })

        rvMain = findViewById(R.id.RV)
        rvMain.layoutManager = LinearLayoutManager(this)
        getAllData()
    }

    private fun getAllData() {
        var retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        var retroData = retrofit.getData()
        retroData.enqueue(object : Callback<List<UsersItem>> {
            override fun onResponse(
                call: Call<List<UsersItem>>,
                response: Response<List<UsersItem>>, ) {
                var data = response.body()
                Log.d("data", data.toString())
                nl= data as ArrayList<UsersItem>

                myAdapter = MyAdapter(baseContext, nl)
                rvMain.adapter = myAdapter

            }

            override fun onFailure(call: Call<List<UsersItem>>, t: Throwable) {
            }

        })
    }

    fun ara(aramaKelimesi: String) {
        if (aramaKelimesi != null) {
            val fl = ArrayList<UsersItem>()
            for (i in nl) {
                if (i.login.lowercase(Locale.ROOT).contains(aramaKelimesi)) {
                    //lowercase büyük kücük harfi aldırış etmeden arıyor
                    //contains arama kelimesi ve dizideki gelen veriyle eşleştiriyor

                    fl.add(i)
                }
                if (fl.isEmpty()) {

                } else {
                    myAdapter.setFilteredList(fl)
                }
            }

        }
    }
}