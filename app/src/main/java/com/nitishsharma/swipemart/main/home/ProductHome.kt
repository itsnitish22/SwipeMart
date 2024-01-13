package com.nitishsharma.swipemart.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.nitishsharma.swipemart.R
import com.nitishsharma.swipemart.data.model.LoadingState
import com.nitishsharma.swipemart.databinding.AddProductBgBinding
import com.nitishsharma.swipemart.databinding.FragmentAddProductBinding
import com.nitishsharma.swipemart.databinding.FragmentProductHomeBinding
import com.nitishsharma.swipemart.main.addproduct.AddProduct
import com.nitishsharma.swipemart.main.home.epoxy.HomeController
import org.koin.core.component.KoinComponent
import timber.log.Timber

class ProductHome : Fragment(), KoinComponent {
    private lateinit var binding: FragmentProductHomeBinding
    private val viewModel: ProductHomeViewModel by activityViewModels()
    private val homeController by lazy { HomeController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_home, container, false)
        binding.apply {
            lifecycleOwner = this@ProductHome
            viewModel = this@ProductHome.viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeViewModel()
    }

    private fun setupViews() {
        with(binding) {
            initRecyclerView()
            initSearchListener()
            swipeRefresh.setOnRefreshListener {
                searchEditText.setQuery("", false)
                viewModel?.getAllProducts()
            }
            floatingActionButton.setOnClickListener { openAddProductBottomSheet() }
        }
    }

    private fun openAddProductBottomSheet() {
        AddProduct().show(childFragmentManager, "ADD_PRODUCT")
    }

    private fun initSearchListener() {
        binding.searchEditText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(searchText: String?): Boolean {
                searchText?.let { viewModel.filterList(it) }
                return true
            }

            override fun onQueryTextChange(searchText: String?): Boolean {
                if (searchText.isNullOrBlank()) homeController.setData(viewModel.allProducts.value, false)
                return true
            }
        })
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            adapter = homeController.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observeViewModel() {
        viewModel.apply {
            allProducts.observe(viewLifecycleOwner) {
                if (binding.swipeRefresh.isRefreshing) binding.swipeRefresh.isRefreshing = false
                homeController.setData(it, false)
            }
            loadingModel.observe(viewLifecycleOwner) {
                if ((it.loading == LoadingState.ERROR || it.loading == LoadingState.COMPLETED) && binding.swipeRefresh.isRefreshing)
                    binding.swipeRefresh.isRefreshing = false
            }
            searchedProducts.observe(viewLifecycleOwner) {
                if (it.isNullOrEmpty()) homeController.setData(null, true)
                else with(binding.recyclerView) {
                    visibility = View.VISIBLE
                    homeController.setData(it, false)
                }
            }
        }
    }
}
