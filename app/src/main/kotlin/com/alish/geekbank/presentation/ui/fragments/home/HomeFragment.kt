package com.alish.geekbank.presentation.ui.fragments.home

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.databinding.FragmentCardBinding
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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(R.layout.fragment_home),
    OnMapReadyCallback {
    private var xCoOrdinate = 0f
    private lateinit var googleMap: GoogleMap
    private val adapter: NewsAdapter = NewsAdapter(this::clickNewsItem)
    override val viewModel: HomeViewModel by viewModels()
    override val binding by viewBinding(FragmentHomeBinding::bind)
    private var bottomSheet: BottomSheetBehavior<ConstraintLayout>? = null
    @Inject
    lateinit var preferencesHelper: PreferencesHelper

    private fun clickNewsItem(model: NewsModelUI) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailNews(model))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.motionLayout.setTransitionListener(object : MotionLayout.TransitionListener{
            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                findNavController().navigate(R.id.action_homeFragment_to_cardFragment)
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
        })
    }

    class CardFragment : Fragment(R.layout.fragment_card){
        private lateinit var binding: FragmentCardBinding

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = FragmentCardBinding.inflate(inflater,container,false)
            return binding.root
        }
    }

    override fun initialize() = with(binding){
        bottomSheet?.state = BottomSheetBehavior.STATE_EXPANDED
        bottomInclude.recycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        bottomSheet?.state = BottomSheetBehavior.STATE_EXPANDED

        binding.bottomInclude.map.onCreate(null)
        binding.bottomInclude.map.onResume()
        binding.bottomInclude.map.getMapAsync(this@HomeFragment)
        binding.bottomInclude.recyclerNews.adapter = adapter
    }


    private fun setupBottomSheet() {
    bottomSheet = BottomSheetBehavior.from(binding.bottomInclude.bottomSheetHome)
    bottomSheet?.peekHeight = resources.displayMetrics.heightPixels / 3
    bottomSheet?.isHideable = false
    bottomSheet?.addBottomSheetCallback(object :
        BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            when (newState) {
                BottomSheetBehavior.STATE_HIDDEN -> {
//                    bottomSheet.s = BottomSheetBehavior.STATE_COLLAPSED
                }
                else -> {}
            }
        }
        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            binding.bottomInclude.imageBack.rotation = slideOffset * 180
        }
    })
}


    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    override fun setupListeners() {
        clickForAllNews()
        setupBottomSheet()
        clickForSeeFullMap()
//        clickForQrScanner()

//
    }

//    private fun clickForQrScanner() {
//        binding.qrCode.setOnClickListener {
//            findNavController().navigate(R.id.scannerFragment)
//        }
//    }

    private fun clickForSeeFullMap() {
        binding.bottomInclude.txtShowAllMap.setOnClickListener {
            findNavController().navigate(R.id.mapFull)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun clickForAllNews() {
        binding.bottomInclude.txtShowAll.setOnClickListener {
            findNavController().navigate(R.id.allNews)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun setupRequests() {
        viewModel.stateUser.collectUIState() {
            when (it) {
                is UIState.Error -> {}
                is UIState.Loading -> {}
                is UIState.Success -> {
                    it.data.forEach { data ->
                        if (data?.id == preferencesHelper.getString("id")) {
                            binding.tvCash.text = data?.firstCard?.get("money").toString()
                            binding.bottomInclude.numberCard.text =
                                "**** **** **** ****" + data?.firstCard?.get("cardNumber")
                                    .toString().substring(
                                        data?.firstCard?.get("cardNumber").toString().length - 4
                                    )
                            binding.bottomInclude.qrView.setImageBitmap(
                                generateQrCode(
                                    cardNumber = data?.firstCard?.get("cardNumber")
                                        .toString()
                                )
                            )
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

    private fun generateQrCode(cardNumber: String?): Bitmap? {
        val writer = MultiFormatWriter()
        var bitmap: Bitmap? = null

        try {
            val matrix = writer.encode(cardNumber, BarcodeFormat.QR_CODE, 550, 550)
            val encoder = BarcodeEncoder()
            bitmap = encoder.createBitmap(matrix)
        } catch (e: WriterException) {
        }
        return bitmap
    }

}


