package com.alish.geekbank.presentation.ui.fragments.home

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.common.constants.Constants
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.databinding.FragmentHomeBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.extensions.overrideOnBackPressed
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
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(R.layout.fragment_home),
    OnMapReadyCallback {
    private var xCoOrdinate = 0f
    private lateinit var googleMap: GoogleMap
    private val adapter: NewsAdapter = NewsAdapter(this::clickNewsItem)

    @Inject
    lateinit var preferencesHelper: PreferencesHelper

    private fun clickNewsItem(model: NewsModelUI) {

        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailNews(model))
    }

    override val viewModel: HomeViewModel by viewModels()
    override val binding by viewBinding(FragmentHomeBinding::bind)

    override fun initialize() {
        binding.map.onCreate(null)
        binding.map.onResume()
        binding.map.getMapAsync(this)
        binding.recyclerNews.adapter = adapter
    }

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    override fun setupListeners() {
        clickForAllNews()
        clickForSeeFullMap()
        clickForQrScanner()

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

                    findNavController().navigate(R.id.cardFragment)

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

    private fun clickForQrScanner() {
        binding.qrCode.setOnClickListener {
            findNavController().navigate(R.id.scannerFragment)
        }
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

    @SuppressLint("SetTextI18n")
    override fun setupRequests() {
        viewModel.stateCard.collectUIState() {
            when (it) {
                is UIState.Error -> {}
                is UIState.Loading -> {}
                is UIState.Success -> {
                    it.data.forEach { data ->
                        if (data?.id == preferencesHelper.getString(Constants.USER_ID)) {
                            binding.tvCash.text = data?.money.toString()

                            binding.numberCard.text =
                                "**** **** **** ****" + data?.cardNumber.toString().substring(
                                        data?.cardNumber.toString().length - 4
                                    )
//                            binding.qrView.setImageBitmap(
//                                generateQrCode(
//                                    cardNumber = data?.cardNumber.toString()
//                                )
//                            )

                        }

                    }
                }
            }
        }

        viewModel.newsState.collectUIState {
            when (it) {
                is UIState.Error -> {}
                is UIState.Loading -> {}
                is UIState.Success -> {
                    var list: ArrayList<NewsModelUI> = ArrayList()
                    for (i in it.data) {
                        if (list.size < 3) {
                            list.add(i)
                        } else {
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

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(geekTech, 17f))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        overrideOnBackPressed { activity?.finish() }
    }

//    private fun generateQrCode(cardNumber: String?): Bitmap? {
//        val writer = MultiFormatWriter()
//        var bitmap: Bitmap? = null
//
//        try {
//            val matrix = writer.encode(cardNumber, BarcodeFormat.QR_CODE, 550, 550)
//            val encoder = BarcodeEncoder()
//            bitmap = encoder.createBitmap(matrix)
//        } catch (e: WriterException) {
//        }
//        return bitmap
//    }

}
