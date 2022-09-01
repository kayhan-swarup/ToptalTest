package com.kayhan.toptaltest.views

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.kayhan.toptaltest.R
import com.kayhan.toptaltest.databinding.PostDetailsBinding
import com.kayhan.toptaltest.util.Status
import com.kayhan.toptaltest.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class PostDetailsFragment
@Inject constructor(val glide: RequestManager)
    : Fragment(R.layout.post_details) {
    var binding : PostDetailsBinding? =null

    lateinit var viewModel: PostViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PostDetailsBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(PostViewModel::class.java)
        binding!!.artImageView.setOnClickListener {
            findNavController().navigate(PostDetailsFragmentDirections.actionPostDetailsFragmentToImageApiFragment())
        }

        subscribeToObserver()

        val callback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        binding!!.saveButton.setOnClickListener {
            findNavController().popBackStack()
            viewModel.makePost(binding!!.title.text.toString(),binding!!.author.text.toString(),binding!!.location.text.toString())

        }
    }

    private fun subscribeToObserver() {
        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer {url->
            binding.let {
                glide.load(url).into(binding!!.artImageView)
            }
        })
        viewModel.insertPostMessage.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    viewModel.resetInsertPostMsg()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                }
                Status.LOADING ->{
                    Toast.makeText(requireContext(),"Loading",Toast.LENGTH_LONG).show()

                }
            }
        })
    }
}