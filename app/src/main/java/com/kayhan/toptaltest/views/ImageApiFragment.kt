package com.kayhan.toptaltest.views

import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kayhan.toptaltest.R
import com.kayhan.toptaltest.adapter.ImageRecyclerAdapter
import com.kayhan.toptaltest.databinding.FragmentImageApiBinding
import com.kayhan.toptaltest.util.Status
import com.kayhan.toptaltest.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ImageApiFragment @Inject constructor(
    private val imageRecyclerAdapter: ImageRecyclerAdapter
): Fragment(R.layout.fragment_image_api) {
    lateinit var viewModel: PostViewModel

    private var binding : FragmentImageApiBinding? = null;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(PostViewModel::class.java)

        binding = FragmentImageApiBinding.bind(view);

        binding!!.imageRecyclerView.adapter = imageRecyclerAdapter
        binding!!.imageRecyclerView.layoutManager = GridLayoutManager(requireContext(),3)

        subscribeToObservers()

        imageRecyclerAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)
        }

        var job: Job? = null
        binding!!.searchText.addTextChangedListener{it->
            job?.cancel()
            job = lifecycleScope.launch {
                delay(100)
                it?.let {
                    if(it.toString().isNotEmpty()){
                        viewModel.searchForImage(it.toString())
                    }
                }
            }

        }

    }

    private fun subscribeToObservers() {
        viewModel.imageList.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    val urls = it.data!!.hits.map { imageResult ->
                        imageResult.previewURL
                    }
                    imageRecyclerAdapter.images = urls ?: listOf()
                    binding?.progressBar?.visibility = View.GONE
                }
                Status.LOADING -> {
                    binding!!.progressBar?.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding?.progressBar?.visibility = View.GONE
                }
            }
        })
    }
}