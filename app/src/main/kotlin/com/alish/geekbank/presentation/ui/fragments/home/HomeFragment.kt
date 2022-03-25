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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.common.constants.Constants
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.databinding.FragmentCardBinding
import com.alish.geekbank.databinding.FragmentHomeBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.models.CardListUIModel
import com.alish.geekbank.presentation.models.CardModelUI
import com.alish.geekbank.presentation.models.NewsModelUI
import com.alish.geekbank.presentation.models.exchange.ExchangeModelsUI
import com.alish.geekbank.presentation.state.UIState
import com.alish.geekbank.presentation.ui.adapters.CardDetailAdapter
import com.alish.geekbank.presentation.ui.adapters.CardDetailListAdapter
import com.alish.geekbank.presentation.ui.adapters.ExchangeAdapter
import com.alish.geekbank.presentation.ui.adapters.NewsAdapter
import com.alish.geekbank.presentation.ui.fragments.exchange.ExchangeViewModel
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
    private val cardDetailListAdapter = CardDetailListAdapter()
    private val exchangeAdapter = ExchangeAdapter()
    private val cardDetailAdapter = CardDetailAdapter()
    val list = ArrayList<CardModelUI>()
    private var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>? = null

    override val viewModel: HomeViewModel by viewModels()
    override val binding by viewBinding(FragmentHomeBinding::bind)
    private var bottomSheet: BottomSheetBehavior<ConstraintLayout>? = null
    @Inject
    lateinit var preferencesHelper: PreferencesHelper

    private fun clickNewsItem(model: NewsModelUI) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailNews(model))
    }

    override val viewModel: HomeViewModel by viewModels()
    private val viewModelExchange: ExchangeViewModel by viewModels()
    override val binding by viewBinding(FragmentHomeBinding::bind)
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


    override fun initialize() {
        binding.bottomSheetInclude.map.onCreate(null)
        binding.bottomSheetInclude.map.onResume()
        binding.bottomSheetInclude.map.getMapAsync(this)
        binding.bottomSheetInclude.recyclerNews.adapter = adapter
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
        binding.bottomSheetInclude.recyclerExchange.adapter = exchangeAdapter
        binding.bottomSheetInclude.recyclerExchange.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.bottomSheetInclude.recycler.adapter = cardDetailListAdapter
        binding.bottomSheetInclude.recycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }


    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    override fun setupListeners() {
        clickForAllNews()
        clickForSeeFullMap()
        clickForQrScanner()
        clickForExchange()
        setupAction()
        setupBottomSheet()

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

    private fun setupBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetInclude.bottomSheetHome)
        bottomSheetBehavior?.peekHeight = resources.displayMetrics.heightPixels / 3
        bottomSheetBehavior?.isHideable = false
        bottomSheetBehavior?.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
                    }
                    else -> {}
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.bottomSheetInclude.imageBack.rotation = slideOffset * 180
            }
        })
    }

    private fun clickForQrScanner() {
        binding.qrCode.setOnClickListener {
            findNavController().navigate(R.id.scannerFragment)
        }
    }

    private fun clickForSeeFullMap() {
        binding.bottomSheetInclude.txtShowAllMap.setOnClickListener {
            findNavController().navigate(R.id.mapFull)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun clickForAllNews() {
        binding.bottomSheetInclude.txtShowAll.setOnClickListener {
            findNavController().navigate(R.id.allNews)
        }
    }

    private fun clickForExchange() {
        binding.bottomSheetInclude.txtShowAllExchange.setOnClickListener {
            findNavController().navigate(R.id.exchangeFragment)
        }
    }

    private fun setupAction() = with(binding) {
        buttonHorizontal.setOnClickListener {
            findNavController().navigate(R.id.transferFragment)
        }
        buttonWallet.setOnClickListener {
            findNavController().navigate(R.id.paymentsFragment)
        }
        buttonExchange.setOnClickListener {
            findNavController().navigate(R.id.exchangeFragment)
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

                            binding.bottomSheetInclude.numberCard.text =
                                "**** **** **** ****" + data?.cardNumber.toString().substring(
                                    data?.cardNumber.toString().length - 4
                                )
                            binding.bottomSheetInclude.qrView.setImageBitmap(
                                generateQrCode(
                                    cardNumber = data?.cardNumber.toString()
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
        viewModel.stateCard.collectUIState {
            when (it) {
                is UIState.Error -> {}
                is UIState.Loading -> {}
                is UIState.Success -> {
                    if (list.size == 0)
                        it.data.forEach { data ->
                            if (data?.id == preferencesHelper.getString(Constants.USER_ID)) {
                                if (data != null) {
                                    list.add(data)
                                    cardDetailAdapter.submitList(list)

                                }
                            }

                        }
                }
            }
        }
        val list2: ArrayList<CardListUIModel> = ArrayList()
        list2.add(CardListUIModel(R.drawable.airbnb, "Airbnb", 1))
        cardDetailListAdapter.submitList(list2)

        viewModelExchange.exchangeState.collectUIState {
            when (it) {
                is UIState.Error -> {}
                is UIState.Loading -> {}
                is UIState.Success -> {
                    val listExchange = ArrayList<ExchangeModelsUI>()
                    it.data.let { data ->
                        listExchange.add(
                            ExchangeModelsUI(
                                it.data.conversion_rates["KGS"].toString(),
                                it.data.conversion_rates["KGS"].toString(),
                            )
                        )

                        listExchange.add(
                            ExchangeModelsUI(
                                it.data.conversion_rates["USD"].toString(),
                                it.data.conversion_rates["USD"].toString(),
                            )
                        )

                        listExchange.add(
                            ExchangeModelsUI(
                                it.data.conversion_rates["EUR"].toString(),
                                it.data.conversion_rates["EUR"].toString(),
                            )
                        )

                        listExchange.add(
                            ExchangeModelsUI(
                                it.data.conversion_rates["RUS"].toString(),
                                it.data.conversion_rates["RUS"].toString(),
                            )
                        )

                        exchangeAdapter.submitList(listExchange)
                    }
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
