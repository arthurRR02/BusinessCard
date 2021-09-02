package com.example.businesscard.ui.register


import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.example.businesscard.databinding.ActivityRegisterBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel by viewModel<RegisterViewModel>()

    private var mCardId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
        loadData()
        observer()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            binding.btnConfirm.id -> {
                registerViewModel.save(
                    id = mCardId,
                    name = binding.edtName.text.toString(),
                    phone = binding.edtPhone.text.toString(),
                    email = binding.edtEmail.text.toString(),
                    company = binding.edtCompany.text.toString(),
                    color = binding.edtColor.text.toString(),
                    textColor = binding.edtTextColor.text.toString()
                )
                finish()
            }
        }
    }

    private fun setListeners() {
        binding.btnConfirm.setOnClickListener(this)
    }

    private fun loadData(){
        val bundle = intent.extras
        if (bundle != null){
            mCardId = bundle.getInt("card")
            registerViewModel.loadCard(mCardId)
            Log.i("id", mCardId.toString())
        }
    }

    private fun observer(){
        registerViewModel.card.observe(this, {
            binding.edtName.setText(it.name)
            binding.edtPhone.setText(it.phone)
            binding.edtEmail.setText(it.email)
            binding.edtCompany.setText(it.company)
            binding.edtColor.setText(it.color)
            binding.edtTextColor.setText(it.textColor)
        })
    }

}