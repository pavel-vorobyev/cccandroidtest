package com.pavelvorobyev.cccandroidtest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pavelvorobyev.cccandroidtest.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observeViewModel()
        viewModel.initDbData()
    }

    private fun openMainFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutView, MainFragment())
            .commit()
    }

    private fun observeViewModel() {
        viewModel.viewState.observe(this, { viewState ->
            when (viewState) {
                MainViewModel.ViewState.InsertingData -> {
                    errorMessageView.visibility = View.GONE
                    frameLayoutView.visibility = View.GONE
                    progressView.visibility = View.VISIBLE
                }
                MainViewModel.ViewState.DataInitialized -> {
                    errorMessageView.visibility = View.GONE
                    progressView.visibility = View.GONE
                    frameLayoutView.visibility = View.VISIBLE
                    openMainFragment()
                }
                MainViewModel.ViewState.Error -> {
                    progressView.visibility = View.GONE
                    frameLayoutView.visibility = View.GONE
                    errorMessageView.visibility = View.VISIBLE
                }
                else -> println("Not implemented")
            }
        })
    }

}