package com.example.movie2you.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie2you.adapter.NowPlayingAdapter
import com.example.movie2you.utils.Command

abstract class BaseFragment : Fragment() {

    abstract var adapter: NowPlayingAdapter
    abstract var layoutManager: LinearLayoutManager
    abstract var command: MutableLiveData<Command>
}

