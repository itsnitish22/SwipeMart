package com.nitishsharma.swipemart.main.addproduct

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nitishsharma.swipemart.R
import com.nitishsharma.swipemart.common.EventObserver
import com.nitishsharma.swipemart.common.Utils
import com.nitishsharma.swipemart.common.isImageWithAspectRatioOneToOne
import com.nitishsharma.swipemart.common.snackBarWithString
import com.nitishsharma.swipemart.common.toast
import com.nitishsharma.swipemart.databinding.FragmentAddProductBinding
import com.nitishsharma.swipemart.main.home.ProductHomeViewModel
import timber.log.Timber

class AddProduct : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddProductBinding
    private val viewModel: ProductHomeViewModel by activityViewModels()
    private var imageUri: Uri? = null
    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_product, container, false)
        binding.viewModel = viewModel
        registerActivityForResultPC()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun registerActivityForResultPC() {
        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            handlePickedMedia(it)
        }
    }

    private fun initViews() {
        binding.apply {
            productPic.setOnClickListener { pickPhoto() }
            addProductButton.setOnClickListener { attemptToAddProduct() }
        }
    }

    private fun initObservers() {
        viewModel.productAdded.observe(viewLifecycleOwner, EventObserver {
            handleProductAdded(it)
        })

        viewModel.addProductLoading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun pickPhoto() {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun attemptToAddProduct() {
        if (validateInputFields()) {
            val file = imageUri?.let { Utils.uriToFile(requireContext(), it) }
            Timber.e(file.toString())
            viewModel.addProduct(
                binding.productTypeSpinner.selectedItem.toString(),
                binding.productNameTv.text.toString().replace("\"", ""),
                binding.productPriceTv.text.toString().replace("\"", ""),
                file
            )
            Timber.e(binding.productNameTv.text.toString())
            Timber.e(binding.productPriceTv.text.toString())
        } else {
            toast("Enter all fields first")
        }
    }

    private fun handleProductAdded(productAdded: Boolean) {
        toast(if (productAdded) "Product added" else "Some error occurred. Try checking your internet connection.")
        dismiss()
    }

    private fun handlePickedMedia(uri: Uri?) {
        uri?.let {
            if (isImageWithAspectRatioOneToOne(it)) {
                imageUri = it
                binding.productPic.setImageURI(it)
            } else {
                binding.root.snackBarWithString("Image should be of 1:1")
            }
        }
    }

    private fun validateInputFields(): Boolean {
        return binding.productNameTv.text?.isNotBlank() == true && binding.productPriceTv.text?.isNotBlank() == true
    }
}
