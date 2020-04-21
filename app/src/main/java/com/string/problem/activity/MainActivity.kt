package com.string.problem.activity

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.string.problem.R
import com.string.problem.adapter.ItemRecyclerAdapter
import com.string.problem.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.older_stack_layout.*
import kotlinx.android.synthetic.main.recent_stack_layout.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recentAdapter: ItemRecyclerAdapter
    private lateinit var oldAdapter: ItemRecyclerAdapter
    private lateinit var recentStringList: ArrayList<String>
    private lateinit var oldStringList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        initListener()
        initData()
        initLayoutManager()
        initAdapter()
        initRecentList()
        initOldList()
    }

    override fun onResume() {
        super.onResume()
        string_input.hideKeyboard()
    }

    private fun initData() {
        recentStringList = ArrayList()
        oldStringList = ArrayList()
    }

    private fun initAdapter() {
        recentAdapter = ItemRecyclerAdapter(recentStringList)
        oldAdapter = ItemRecyclerAdapter(oldStringList)
        recent_recycler_view.adapter = recentAdapter
        older_recycler_view.adapter = oldAdapter
    }

    private fun initLayoutManager() {
        recent_recycler_view.layoutManager = LinearLayoutManager(this)
        older_recycler_view.layoutManager = LinearLayoutManager(this)
    }

    private fun initViewModel() {
        mainViewModel =
            ViewModelProvider(this, defaultViewModelProviderFactory).get(MainViewModel::class.java)
    }

    private fun initListener() {
        add_value.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.add_value -> {
                addValue()
                initClearEditText()
            }
        }
    }

    private fun addValue() {
        val stringData = string_input.text.toString().trim()
        if (TextUtils.isEmpty(stringData)) {
            Toast.makeText(this, getString(R.string.empty_string), Toast.LENGTH_SHORT).show()
        } else
            mainViewModel.addStringValueToQueue(stringData)
    }

    private fun initRecentList() {
        mainViewModel.mutableRecentStackData.observe(this, Observer {
            recentStringList.clear()
            recentStringList.addAll(it)
            recentAdapter.notifyDataSetChanged()
        })
    }

    private fun initOldList() {
        mainViewModel.mutableOlderStackData.observe(this, Observer {
            oldStringList.clear()
            oldStringList.addAll(it)
            oldAdapter.notifyDataSetChanged()
        })
    }

    private fun initClearEditText() {
        string_input.text.clear()
        string_input.hideKeyboard()
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}