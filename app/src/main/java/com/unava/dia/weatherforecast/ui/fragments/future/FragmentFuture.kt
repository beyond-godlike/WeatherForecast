package com.unava.dia.weatherforecast.ui.fragments.future

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unava.dia.weatherforecast.R
import com.unava.dia.weatherforecast.data.model.future.FutureWeatherResponse
import com.unava.dia.weatherforecast.ui.fragments.base.BaseFragment
import com.unava.dia.weatherforecast.ui.fragments.base.SharedViewModel
import com.unava.dia.weatherforecast.ui.hourly.HourlyActivity
import com.unava.dia.weatherforecast.ui.main.MainActivity
import com.unava.dia.weatherforecast.utils.MarginItemDecoration
import com.unava.dia.weatherforecast.utils.RecyclerViewClickListener
import com.unava.dia.weatherforecast.utils.obtainViewModel
import com.unava.dia.weatherforecast.utils.showError
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FragmentFuture : BaseFragment(R.layout.fragment_future_fragment), RecyclerViewClickListener {

    private val viewModel: SharedViewModel by lazy {
        obtainViewModel(requireActivity(),
            SharedViewModel::class.java,
            defaultViewModelProviderFactory)
    }

    private var rvMonth: RecyclerView? = null
    private var adapter: MounthAdapter? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.initUi()
        this.setupRecyclerView()
        this.observeViewModel()
    }

    override fun observeViewModel() {
        viewModel.error.observe(viewLifecycleOwner) {
            showError(it, requireContext())
        }
        viewModel.futureWeather.observe(viewLifecycleOwner) {
            if (it != null) {
                updateView(it)
            }
        }
    }

    override fun initUi() {
        rvMonth = requireActivity().findViewById(R.id.rvMonth)
    }

    private fun setupRecyclerView() {
        rvMonth?.layoutManager = GridLayoutManager(requireContext(), 7)
        rvMonth?.addItemDecoration(
            MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin))
        )
    }

    private fun updateView(response: FutureWeatherResponse) {
        val list = response.forecast?.forecastday?.toMutableList()
        rvMonth?.visibility = View.VISIBLE
        if (adapter == null) {
            adapter = MounthAdapter(list!!, this)
            rvMonth?.adapter = adapter
        } else {
            rvMonth?.adapter = adapter
            adapter?.addWeather(list!!)
        }
    }

    override fun onClick(view: View?, position: Int) {
        activity?.let {
            val intent = Intent(it, HourlyActivity::class.java)
            intent.putExtra("day", position)
            it.startActivity(intent)
        }
    }
}