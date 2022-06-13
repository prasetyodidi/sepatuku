package com.didi.sepatuku.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.didi.sepatuku.adapter.ListShoppingChartAdapter
import com.didi.sepatuku.database.ShoppingChart
import com.didi.sepatuku.databinding.FragmentChartBinding
import com.didi.sepatuku.viewmodel.ChartViewModel
import timber.log.Timber

class ChartFragment : Fragment() {
    private var _binding: FragmentChartBinding? = null
    private val binding get() = _binding
    private val chartViewModel: ChartViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChartBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chartViewModel.listItems.observe(viewLifecycleOwner){ listItems ->
            if (listItems.isNotEmpty()){
                val adapter = ListShoppingChartAdapter(listItems)
                binding?.rvChart?.layoutManager = LinearLayoutManager(requireActivity())
                binding?.rvChart?.setHasFixedSize(true)
                adapter.setOnDeleteClickCallback(object : ListShoppingChartAdapter.OnDeleteClickCallback{
                    override fun onDeleteClicked(item: ShoppingChart) {
                        Timber.d("click delete")
                        chartViewModel.setItem(item)
                        val fragment = DeleteConfirmFragment()
                        fragment.show(parentFragmentManager, "DeleteDialogFragment")
                    }
                })
                binding?.rvChart?.adapter = adapter
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ChartFragment()
    }
}

