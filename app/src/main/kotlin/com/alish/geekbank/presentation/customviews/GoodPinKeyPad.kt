package com.alish.geekbank.presentation.customviews

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.databinding.KeyPadLayoutBinding
import java.util.*


class GoodPinKeyPad : LinearLayout {
    companion object {
        private val TAG = GoodPinKeyPad::class.java.simpleName

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }
    private val binding by viewBinding(KeyPadLayoutBinding::bind)


    private val keypads: MutableList<TextView> = ArrayList()
    private val indicators = Stack<String>()
    private var listener: KeyPadListerner? = null
    private var mBackRight: ImageButton? = null
    private var mBackLeft: ImageButton? = null
    private var numberOfPins = 0
       private var mRootLayout: LinearLayout? = null
    private var keyPadContainer: LinearLayout? = null
    private val solidMap = HashMap<Int, Drawable>()
    private val holoMap = HashMap<Int, Drawable>()
    private val canCelMap = HashMap<Int, Drawable>()
    private val backMap = HashMap<Int, Drawable>()
    private var themeId = 0
    private var theme: Drawable? = null
    private var solid: Drawable? = null
    private var hollow: Drawable? = null
    private var keypadCallAllIcon: Drawable? = null
    private var keypadBackPressIcon: Drawable? = null
    private var back: Drawable? = null
    private var cancel: Drawable? = null
    private var marginTop = 0f
    private var marginLeft = 0f
    private var marginRight = 0f
    private var marginBottom = 0f
    private var keySize = 0f
    private var keyTextSize = 0f
    private var errorTextSize = 0f
    private var background_color = 0
    private var keypadTextColor = 0
    private var backPressVisibility = 0
    private var keyBackground: Drawable? = null
    private var selectedIndicator: Drawable? = null
    private var defaultIndicator: Drawable? = null
    private var errorIndicator: Drawable? = null
    private var errorText: String = ""
    private var errorTextView: TextView? = null
    private var font: Typeface? = null


    constructor(context: Context) : super(context) {
        initViews(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttributes(context, attrs)
        initViews(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initAttributes(context, attrs)
        initViews(context)
    }

    fun setKeyPadListener(listerner: KeyPadListerner?) {
        this.listener = listerner
    }



    @SuppressLint("UseCompatLoadingForDrawables", "ResourceType")
    private fun initAttributes(context: Context, attrs: AttributeSet?) {


        solidMap[1] = context.resources.getDrawable(R.drawable.green_solid_dot, context.theme)

        holoMap[1] = context.resources.getDrawable(R.drawable.white_solid_dot, context.theme)

        backMap[1] = context.resources.getDrawable(R.drawable.delete, context.theme)

        canCelMap[1] =
            context.resources.getDrawable(R.drawable.clear_white, context.theme)

        val typedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.GoodPinKeyPad, 0, 0)
        try {
            background_color = typedArray.getColor(
                R.styleable.GoodPinKeyPad_backgroundColor,
                ContextCompat.getColor(context, R.color.transparent)
            )

            numberOfPins = typedArray.getInt(R.styleable.GoodPinKeyPad_pinEntry, 4)
            themeId = typedArray.getInt(R.styleable.GoodPinKeyPad_keyPadStyle, 1)
            //theme = themeMap[themeId]
            solid = solidMap[themeId]
            hollow = holoMap[themeId]
            back = backMap[themeId]
            cancel = canCelMap[themeId]
            keypadTextColor = typedArray.getColor(
                R.styleable.GoodPinKeyPad_textColor,
                ContextCompat.getColor(context, R.color.transparent)
            )

            backPressVisibility =
                typedArray.getInteger(R.styleable.GoodPinKeyPad_controlsPressVisibility, 1)


        }
        finally {
            typedArray.recycle()
        }
    }

    private fun setThemes() {


        for (textView in keypads) {
            textView.background = theme
            textView.setTextColor(keypadTextColor)

            if (keyBackground != null) {
                textView.background = keyBackground
            }

            if (font != null) {
                textView.typeface = font
            }

            if (keyTextSize != 0f) {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, keyTextSize)
            }

            if (keySize != 0f) {
                val textViewParam = textView.layoutParams as ViewGroup.LayoutParams
                textViewParam.height = keySize.toInt()
                textViewParam.width =  keySize.toInt()
                textView.layoutParams = textViewParam
            }

        }
        if (keypadBackPressIcon != null) {
            //mBackRight.setImageResource(this.keypadBackPressIcon);
            mBackRight!!.setImageDrawable(keypadBackPressIcon)
        }
        if (keypadCallAllIcon != null) {
            //mBackLeft.setImageResource(this.keypadCallAllIcon);
            mBackLeft!!.setImageDrawable(keypadCallAllIcon)
        }
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        params.setMargins(
            dpToPx(marginLeft),
            dpToPx(marginTop),
            dpToPx(marginRight),
            dpToPx(marginBottom)
        )
        keyPadContainer!!.layoutParams = params
        if (backPressVisibility == 0) {
            mBackLeft!!.visibility = GONE
            mBackRight!!.visibility = GONE
        } else {
            mBackLeft!!.visibility = VISIBLE
            mBackRight!!.visibility = VISIBLE
            if (keypadBackPressIcon == null && keypadCallAllIcon == null) {
                mBackLeft!!.setImageDrawable(cancel)
                mBackRight!!.setImageDrawable(back)
            }
        }

        if (selectedIndicator != null) {
            solid = selectedIndicator
        }

        if (defaultIndicator != null) {
            hollow = defaultIndicator
        }

        if (font != null) {
            errorTextView!!.typeface = font
        }
    }

    private fun initViews(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.key_pad_layout, this)
        mBackRight = view.findViewById(R.id.back_right)
        mBackLeft = view.findViewById(R.id.back_left)
        errorTextView = view.findViewById(R.id.mErrorText)
        keypads.add(view.findViewById<View>(R.id.key0) as TextView)
        keypads.add(view.findViewById<View>(R.id.key1) as TextView)
        keypads.add(view.findViewById<View>(R.id.key2) as TextView)
        keypads.add(view.findViewById<View>(R.id.key3) as TextView)
        keypads.add(view.findViewById<View>(R.id.key4) as TextView)
        keypads.add(view.findViewById<View>(R.id.key5) as TextView)
        keypads.add(view.findViewById<View>(R.id.key6) as TextView)
        keypads.add(view.findViewById<View>(R.id.key7) as TextView)
        keypads.add(view.findViewById<View>(R.id.key8) as TextView)
        keypads.add(view.findViewById<View>(R.id.key9) as TextView)
        keypads.add(view.findViewById<View>(R.id.key11) as TextView)
        mRootLayout = view.findViewById(R.id.root_layout)
        keyPadContainer = view.findViewById(R.id.key_pad_container)
        setListener()
        setThemes()
        if (numberOfPins == 4) {
            binding.indicator5.visibility = GONE
            binding.indicator6.visibility = GONE
        }
        if (numberOfPins == 5) {
            binding.indicator6.visibility = GONE
        }
        mRootLayout!!.setBackgroundColor(background_color)
        binding.indicator1.background = hollow
        binding.indicator2.background = hollow
        binding.indicator3.background = hollow
        binding.indicator4.background = hollow
        binding.indicator5.background = hollow
        binding.indicator6.background = hollow

        errorTextView!!.text = errorText
    }

    private fun setListener() {
        for (keypad in keypads) {
            keypad.setOnClickListener {
                if (listener != null) {
                    indicators.push(keypad.text.toString())
                    if (indicators.size <= numberOfPins) {
                        setIndicators(indicators.size)
                        if (indicators.size == numberOfPins) {
                            listener!!.onKeyPadPressed(pinToString(indicators))
                        }
                    } else {
                        indicators.pop()
                    }
                } else {
                    indicators.push(keypad.text.toString())
                    if (indicators.size <= numberOfPins) {
                        setIndicators(indicators.size)
                    } else {
                        indicators.pop()
                    }
                }
            }
        }

        mBackRight!!.setOnClickListener { doClearPin() }

        mBackLeft!!.setOnClickListener {
           clearAll()
        }
    }

    fun clearAll() {
        if (listener != null) {
            listener!!.onClear()
            try {
                indicators.clear()
                setIndicators(indicators.size)
                // listener!!.onKeyPadPressed(pinToString(indicators))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun doClearPin() {
        if (listener != null) {
            listener!!.onKeyBackPressed()
            try {
                if (indicators.size != 0) {
                    indicators.pop()
                    setIndicators(indicators.size)
                } else {
                    setIndicators(indicators.size)
                }
                // listener!!.onKeyPadPressed(pinToString(indicators))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun setError() {
        if (!TextUtils.isEmpty(errorText)) {
            errorTextView!!.visibility = VISIBLE
            errorTextView!!.text = errorText
        }

        if (errorTextSize != 0f) {
            errorTextView!!.textSize = errorTextSize
        }
    }

    private fun setIndicators(size: Int) {
        when (size) {
            0 -> {
                binding.indicator1.background = hollow
                binding.indicator2.background = hollow
                binding.indicator3.background = hollow
                binding.indicator4.background = hollow
                binding.indicator5.background = hollow
                binding.indicator6.background = hollow
                errorTextView!!.visibility = INVISIBLE
            }
            1 -> {
                binding.indicator1.background = solid
                binding.indicator2.background = hollow
                binding.indicator3.background = hollow
                binding.indicator4.background = hollow
                binding.indicator5.background = hollow
                binding.indicator6.background = hollow
                errorTextView!!.visibility = INVISIBLE
            }
            2 -> {
                binding.indicator1.background = solid
                binding.indicator2.background = solid
                binding.indicator3.background = hollow
                binding.indicator4.background = hollow
                binding.indicator5.background = hollow
                binding.indicator6.background = hollow
                errorTextView!!.visibility = INVISIBLE
            }
            3 -> {
                binding.indicator1.background = solid
                binding.indicator2.background = solid
                binding.indicator3.background = solid
                binding.indicator4.background = hollow
                binding.indicator5.background = hollow
                binding.indicator6.background = hollow
                errorTextView!!.visibility = INVISIBLE
            }
            4 -> {
                binding.indicator1.background = solid
                binding.indicator2.background = solid
                binding.indicator3.background = solid
                binding.indicator4.background = solid
                binding.indicator5.background = hollow
                binding.indicator6.background = hollow
                errorTextView!!.visibility = INVISIBLE
            }
            5 -> {
                binding.indicator1.background = solid
                binding.indicator2.background = solid
                binding.indicator3.background = solid
                binding.indicator4.background = solid
                binding.indicator5.background = solid
                binding.indicator6.background = hollow
                errorTextView!!.visibility = INVISIBLE
            }
            6 -> {
                binding.indicator1.background = solid
                binding.indicator2.background = solid
                binding.indicator3.background = solid
                binding.indicator4.background = solid
                binding.indicator5.background = solid
                binding.indicator6.background = solid
                errorTextView!!.visibility = INVISIBLE
            }
            -1 -> {
                binding.indicator1.background = errorIndicator
                binding.indicator2.background = errorIndicator
                binding.indicator3.background = errorIndicator
                binding.indicator4.background = errorIndicator
                binding.indicator5.background = errorIndicator
                binding.indicator6.background = errorIndicator
                setError()
            }
        }
    }

    private fun pinToString(indicators: Stack<String>): String {
        var str = ""
        for (s in indicators) {
            str += s
        }
        return str
    }


    @SuppressLint("WrongConstant")
    private fun dpToPx(valueInDp: Float): Int {
        return TypedValue.applyDimension(1, valueInDp, this.resources.displayMetrics)
            .toInt()
    }
}