package com.unava.dia.weatherforecast.ui.fragments.future

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unava.dia.weatherforecast.R
import com.unava.dia.weatherforecast.data.model.future.FutureWeatherResponse
import com.unava.dia.weatherforecast.ui.fragments.base.BaseFragment
import com.unava.dia.weatherforecast.utils.MarginItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentFuture : BaseFragment(R.layout.fragment_future_fragment) {

    private lateinit var viewModel: FragmentFutureViewModel

    private var city: String = "London"

    private var rvMonth: RecyclerView? = null
    private var adapter: MounthAdapter? = null

    companion object {
        fun newInstance() = FragmentFuture()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        city = getCityFromShared()
        if (city == "") city = "London"

        this.initUi()
        this.setupRecyclerView()
        this.bindViewModel()
    }


    private fun getForecast() {
        viewModel.getFutureWeather(city, 7)
    }

    override fun bindViewModel() {
        viewModel = ViewModelProvider(this)[FragmentFutureViewModel::class.java]
        this.getForecast()
        this.observeViewModel()
    }


    override fun observeViewModel() {
        viewModel.error.observe(viewLifecycleOwner, {
            showError(it, requireContext())
        })
        viewModel.futureWeather.observe(viewLifecycleOwner, {
            updateView(it)
        })
    }

    override fun initUi() {
        rvMonth = requireActivity().findViewById(R.id.rvMonth)
        city = getCityFromShared()
        if (city == "") city = "London"
    }

    private fun setupRecyclerView() {
        rvMonth?.layoutManager = GridLayoutManager(requireContext(), 7)
        rvMonth?.addItemDecoration(
            MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin))
        )
        //rvMounth?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        //rvMounth.addOnItemTouchListener(RecyclerItemClickListener(requireContext(), this))
    }

    private fun updateView(response: FutureWeatherResponse) {
        val list = response.forecast?.forecastday?.toMutableList()
        rvMonth?.visibility = View.VISIBLE
        if (adapter == null) {
            adapter = MounthAdapter(list!!)
            rvMonth?.adapter = adapter
        } else {
            adapter?.addWeather(list!!)
        }
    }
}