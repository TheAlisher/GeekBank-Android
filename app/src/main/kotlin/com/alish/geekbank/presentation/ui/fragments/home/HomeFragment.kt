package com.alish.geekbank.presentation.ui.fragments.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.databinding.FragmentHomeBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.models.NewsModelUI
import com.alish.geekbank.presentation.state.UIState
import com.alish.geekbank.presentation.ui.adapters.NewsAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel,FragmentHomeBinding>(R.layout.fragment_home),
    OnMapReadyCallback {
    private var xCoOrdinate = 0f
    private lateinit var googleMap: GoogleMap
    private val adapter: NewsAdapter = NewsAdapter(this::clickNewsItem)

    private fun clickNewsItem(model: NewsModelUI) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailNews(model))
    }

    override val viewModel:HomeViewModel by viewModels()
    override val binding by viewBinding(FragmentHomeBinding::bind)

    override fun initialize() {
        binding.map.onCreate(null)
        binding.map.onResume()
        binding.map.getMapAsync(this)
        binding.recyclerNews.adapter = adapter

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setupListeners() {
        clickForAllNews()
        clickForSeeFullMap()
        binding.ivFirst.setOnTouchListener(View.OnTouchListener { view, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    xCoOrdinate = view.x - event.rawX
                }
                MotionEvent.ACTION_MOVE -> {
                    view.animate().x(event.rawX + xCoOrdinate)
                        .setDuration(0)
                        .start()
                }
                MotionEvent.ACTION_UP -> {

                    findNavController().navigate(R.id.action_homeFragment_to_cardFragment)

                    Log.e("anime", "onViewCreated: $xCoOrdinate")

                }

                else -> {
                    view.clearAnimation()
                    return@OnTouchListener false
                }
            }
            true
        })

    }

    private fun clickForSeeFullMap() {
        binding.txtShowAllMap.setOnClickListener {
            findNavController().navigate(R.id.mapFull)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun clickForAllNews() {
        binding.txtShowAll.setOnClickListener {
            findNavController().navigate(R.id.allNews)
        }
    }

    override fun setupRequests() {
        viewModel.newsState.collectUIState{
            when(it){
                is UIState.Error -> {}
                is UIState.Loading -> {}
                is UIState.Success -> {
                    var list: ArrayList<NewsModelUI> = ArrayList()
                    for(i in it.data){
                        if (list.size < 3) {
                            list.add(i)
                        }else{
                            break
                        }


                    }
                    adapter.submitList(list)
                }
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        MapsInitializer.initialize(requireContext())
        googleMap = map

        val geekTech = LatLng(42.813358, 73.845158)
        val bizone = LatLng(42.816208, 73.844670)
        val geekTech1 = LatLng(42.813351, 73.845718)
        val geekTech2 = LatLng(42.813036, 73.845793)
        val geekTech3 = LatLng(42.814240, 73.845375)

        googleMap.addMarker(MarkerOptions().position(geekTech).title("GeekTech"))
        googleMap.addMarker(MarkerOptions().position(bizone).title("Bizone"))
        googleMap.addMarker(MarkerOptions().position(geekTech1).title("vozle geeka"))
        googleMap.addMarker(MarkerOptions().position(geekTech2).title("avtomoyka"))
        googleMap.addMarker(MarkerOptions().position(geekTech3).title("chto to"))


        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(geekTech,17f))
    }

}


