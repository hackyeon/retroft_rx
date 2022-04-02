package com.hackyeon.retrofit_rx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.hackyeon.retrofit_rx.databinding.ActivityMainBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val API = MainAPI()
    private lateinit var disposeBag: CompositeDisposable
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        disposeBag = CompositeDisposable()


        binding.testButton.setOnClickListener {
            val text = binding.testEdit.text.toString()
            API.GetPapago(text)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    binding.testText.text = it
                }, {
                    // 오류
                }).disposeBy(disposeBag)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeBag.clear()
    }

    private fun Disposable.disposeBy(disposeBag: CompositeDisposable) {
        disposeBag.add(this)
    }
}