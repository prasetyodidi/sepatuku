package com.didi.sepatuku.presentation.detail_shoe

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.didi.sepatuku.R
import com.didi.sepatuku.data.local.entity.ShoppingCartEntity
import com.didi.sepatuku.databinding.FragmentDetailCartDialogBinding
import com.didi.sepatuku.domain.model.DetailShoe
import com.google.android.material.chip.Chip
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class DetailCartDialogFragment : Fragment() {
    private var _binding: FragmentDetailCartDialogBinding? = null
    private val binding get() = _binding
    private val viewModel: DetailShoeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding?.chipsTypes?.isClickable = true
        binding?.chipsTypes?.isSingleSelection = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailCartDialogBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.state
                    .map { it.detailShoe }
                    .distinctUntilChanged()
                    .collect {
                        if (it != null) {
                            showData(it)
                        }
                    }
            }
        }

        binding?.btnClose?.setOnClickListener {
            closeFragment()
        }

    }
    private fun showData(data: DetailShoe){
        binding?.run {
            tvPrice.text = getString(R.string.currency).plus(data.price)
            tvStock.text = data.stock.toString()
            Glide.with(requireContext())
                .load(data.imageUrl)
                .into(imgItem)
            for (i in data.sizes){
                chipsTypes.addView(addChip(requireContext(), i))
            }
            textTypeSelected.text = data.sizes.last().toString()
            chipsTypes.setOnCheckedStateChangeListener { _, checkedIds ->
                Timber.d("checkedIds : $checkedIds")
                if (checkedIds.isNotEmpty()){
                    chipsTypes.check(checkedIds[0])
                    val children: Chip = chipsTypes.findViewById(checkedIds[0])
                    val text = children.text
                    textTypeSelected.text = text
                    Timber.d("text: $text")
                }
            }
            btnAddToChart.setOnClickListener {
                viewModel.insertShoppingCart(
                    ShoppingCartEntity(
                        name = data.name,
                        price = data.price,
                        imgUrl = data.imageUrl,
                        type = textTypeSelected.text.toString(),
                        amount = 1
                    )
                )
                closeFragment()
            }

        }
    }

    private fun closeFragment(){
        activity?.supportFragmentManager?.popBackStack()
    }

    private fun addChip(context: Context, value: Int? = 0): Chip {
        val chip = Chip(context)
        chip.text = value.toString()
        chip.isCheckable = true

        chip.isChecked = true

        return chip
    }

    companion object {
        @JvmStatic
        fun newInstance() = DetailCartDialogFragment()
    }
}