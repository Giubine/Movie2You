package com.example.movie2you.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.movie2you.utils.Command

abstract class BaseFragment : Fragment() {

    abstract var command: MutableLiveData<Command>
}

