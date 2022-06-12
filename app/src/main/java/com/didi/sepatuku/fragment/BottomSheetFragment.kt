package com.didi.sepatuku.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.didi.sepatuku.Shoes
import com.didi.sepatuku.database.ShoppingChart
import com.didi.sepatuku.databinding.FragmentBottomSheetBinding
import com.didi.sepatuku.viewmodel.ChartViewModel
import com.google.android.material.chip.Chip
import timber.log.Timber

class BottomSheetFragment : Fragment() {
    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding
    private lateinit var chartViewModel: ChartViewModel
    private var shoesData: Shoes? = null
    private var amount: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("created bottomSheet")
        arguments?.let {
            shoesData = it.getParcelable(DetailShoesFragment.EXTRA_SHOES)
        }
        chartViewModel = ChartViewModel(requireActivity().application)
        binding?.chipsTypes?.isClickable = true
        binding?.chipsTypes?.isSingleSelection = true
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.d("name: ${shoesData?.name}")
        Timber.d("sizes: ${shoesData?.sizes}")

        context?.let { context ->
            binding?.let { binding ->
                Glide.with(context)
                    .load(shoesData?.photo)
                    .apply(RequestOptions().override(80, 80))
                    .into(binding.imgItem)

                binding.textPrice.text = "Rp. ${shoesData?.price}"
                shoesData?.sizes.let { sizes ->
                    binding.textTypeSelected.text = sizes?.get(0).toString()
                    for (i in sizes as List) {
                        binding.chipsTypes.addView(addChip(context, i))
                    }
                }
                binding.btnClose.setOnClickListener {
                    childFragmentManager.popBackStack()
                }

                binding.chipsTypes.setOnCheckedStateChangeListener { group, checkedIds ->
                    Timber.d("checkedIds : $checkedIds")
                    binding.chipsTypes.check(checkedIds[0])
                    val children: Chip = binding.chipsTypes.findViewById(checkedIds[0])
                    val text = children.text
                    binding.textTypeSelected.text = text
                    Timber.d("text: $text")
                }
                binding.textAmount.text = amount.toString()
                binding.btnMin.setOnClickListener {
                    if (amount > 1) {
                        amount--
                        binding.textAmount.text = amount.toString()
                    }
                }
                binding.btnAdd.setOnClickListener {
                    amount++
                    binding.textAmount.text = amount.toString()
                }
                binding.btnAddToChart.setOnClickListener {
                    shoesData?.let {
                        val shoppingChart = ShoppingChart(
                            name = it.name,
                            price = it.price,
                            img = it.photo,
                            amount = this.amount,
                            type = binding.textTypeSelected.text.toString()
                        )

                        chartViewModel.insert(shoppingChart = shoppingChart)
                    }
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding?.root
    }

    fun addChip(context: Context, value: Int? = 0): Chip {
        val chip = Chip(context)
        chip.text = value.toString()
        chip.isCheckable = true

        return chip
    }

    companion object {
        @JvmStatic
        fun newInstance() = BottomSheetFragment().apply {
            arguments = Bundle().apply {
                putParcelable(DetailShoesFragment.EXTRA_SHOES, shoesData)
            }
        }
    }
}