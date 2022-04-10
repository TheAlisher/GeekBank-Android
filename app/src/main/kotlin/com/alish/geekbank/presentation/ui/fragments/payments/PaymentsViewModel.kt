package com.alish.geekbank.presentation.ui.fragments.payments

import com.alish.geekbank.R
import com.alish.geekbank.presentation.base.BaseViewModel
import com.alish.geekbank.presentation.models.PaymentsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel @Inject constructor():BaseViewModel() {

    private val images = intArrayOf(
       R.drawable.fines,
        R.drawable.cellular,
        R.drawable.internet,
        R.drawable.publik
    )

    private val names11 = arrayOf(
        "Fines",
        "Cellular Operators",
        "Internet",
        "Public utilities"
    )

    var list: ArrayList<PaymentsModel>? = null
        get() {
            if (field != null)
                return field
            field = ArrayList()
            for (i in images.indices){
                val imageId = images[i]
                val nameId = names11[i]

                val model = PaymentsModel(nameId,imageId)
                field!!.add(model)
            }
            return field
        }
}