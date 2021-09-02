package com.example.businesscard.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.businesscard.R
import com.example.businesscard.databinding.ActivityMainBinding
import com.example.businesscard.ui.register.RegisterActivity
import com.example.businesscard.util.ShareImage
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModel<MainViewModel>()

    private lateinit var listener: Listener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
        mainViewModel.getAll()

        binding.recycler.layoutManager = LinearLayoutManager(this)

        observer()

        listener = object : Listener {
            override fun onClick(card: View) {
                ShareImage.share(this@MainActivity, card)
            }

            override fun onLongClick(id: Int) {
                val intent = Intent(applicationContext, RegisterActivity::class.java)
                val bundle = Bundle()
                bundle.putInt("card", id)
                intent.putExtras(bundle)

                startActivity(intent)
            }

        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            binding.fabRegister.id -> {
                goToRegisterActivity()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.getAll()
    }

    private fun goToRegisterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun setListeners() {
        binding.fabRegister.setOnClickListener(this)
    }

    private fun observer(){
        mainViewModel.businessCardList.observe( this, { card ->
            card.let {
                val adapter = CardAdapter(cardList = it)
                binding.recycler.adapter = adapter
                search(adapter)
                adapter.attachListener(listener)
            }
        })
    }

    private fun search(adapter: CardAdapter){
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }

        })
    }
}