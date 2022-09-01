package com.kayhan.toptaltest.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kayhan.toptaltest.R
import com.kayhan.toptaltest.adapter.PostRecyclerAdapter
import com.kayhan.toptaltest.databinding.FragmentPostsBinding
import com.kayhan.toptaltest.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PostFragment @Inject constructor(
    val postRecyclerAdapter: PostRecyclerAdapter
) : Fragment(R.layout.fragment_posts) {


    private var binding : FragmentPostsBinding? = null

    lateinit var viewModel: PostViewModel

    private val swipeCallBack = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition
            val selectedArt = postRecyclerAdapter.posts[layoutPosition]
            viewModel.deletePost(selectedArt)

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(PostViewModel::class.java)
        binding = FragmentPostsBinding.bind(view)

        subscribeToObservers()

        binding!!.recyclerView.adapter = postRecyclerAdapter

        binding!!.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding!!.fab.setOnClickListener {
            findNavController().navigate(PostFragmentDirections.actionPostFragmentToPostDetailsFragment())
        }

        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding!!.recyclerView)
    }

    private fun subscribeToObservers(){
        viewModel.postList.observe(viewLifecycleOwner, Observer {
            postRecyclerAdapter.posts = it
        })
    }

    override fun onDestroyView() {
        binding = null;
        super.onDestroyView()
    }
}