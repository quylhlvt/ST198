package com.female.maker.oc.creator.ui.splash

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.female.maker.oc.creator.base.AbsBaseActivity
import com.female.maker.oc.creator.data.callapi.reponse.DataResponse
import com.female.maker.oc.creator.data.callapi.reponse.LoadingStatus
import com.female.maker.oc.creator.data.model.BodyPartModel
import com.female.maker.oc.creator.data.model.ColorModel
import com.female.maker.oc.creator.data.model.CustomModel
import com.female.maker.oc.creator.data.repository.ApiRepository
import com.female.maker.oc.creator.ui.language.LanguageActivity
import com.female.maker.oc.creator.ui.tutorial.TutorialActivity
import com.female.maker.oc.creator.utils.CONST
import com.female.maker.oc.creator.utils.DataHelper
import com.female.maker.oc.creator.utils.DataHelper.getData
import com.female.maker.oc.creator.utils.SharedPreferenceUtils
import com.female.maker.oc.creator.utils.music.MusicLocal
import com.female.maker.oc.creator.R
import com.female.maker.oc.creator.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.forEach
import kotlin.collections.toList
@AndroidEntryPoint
class SplashActivity : AbsBaseActivity<ActivitySplashBinding>() {
    @Inject
    lateinit var apiRepository: ApiRepository
    @Inject
    lateinit var sharedPreferenceUtils: SharedPreferenceUtils
    private var minDelayPassed = false
    private var dataReady = false
    override fun getLayoutId(): Int = R.layout.activity_splash
    override fun initView() {
        MusicLocal.isInSplashOrTutorial = true
        // Observe data loading TRƯỚC khi load
        observeDataLoading()
        lifecycleScope.launch {
            delay(3000)
            minDelayPassed = true
            // Nếu data đã sẵn sàng thì chuyển màn ngay
            if (dataReady) {
                navigateToNextScreen()
            }
        }



    }
    override fun onResume() {
        super.onResume()
    }
    override fun initAction() {
        // Bắt đầu load data
        lifecycleScope.launch(Dispatchers.IO) {
            getData(apiRepository)
        }
    }

    private fun observeDataLoading() {
        DataHelper.arrDataOnline.observe(this) { response ->
            response?.let {
                when (it.loadingStatus) {
                    LoadingStatus.Loading -> {
                        // Đang loading
                    }

                    LoadingStatus.Success -> {
                        // XỬ LÝ DATA ONLINE (logic từ MainActivity cũ)
                        if (DataHelper.arrBlackCentered.isNotEmpty() && !DataHelper.arrBlackCentered[0].checkDataOnline) {
                            val listA = (it as DataResponse.DataSuccess).body

                            if (listA != null) {
                                // Sort và merge data online
                                val sortedMap = listA
                                    .toList()
                                    .sortedBy { (_, list) ->
                                        list.firstOrNull()?.level ?: Int.MAX_VALUE
                                    }
                                    .toMap()

                                sortedMap.forEach { key, list ->
                                    val bodyPartList = arrayListOf<BodyPartModel>()

                                    list.forEach { x10 ->
                                        val colorList = arrayListOf<ColorModel>()

                                        x10.colorArray.split(",").forEach { color ->
                                            val pathList = arrayListOf<String>()

                                            if (color == "") {
                                                for (i in 1..x10.quantity) {
                                                    pathList.add(CONST.BASE_URL + "${CONST.BASE_CONNECT}/${x10.position}/${x10.parts}/${i}.png")
                                                }
                                                colorList.add(ColorModel("#", pathList))
                                            } else {
                                                for (i in 1..x10.quantity) {
                                                    pathList.add(CONST.BASE_URL + "${CONST.BASE_CONNECT}/${x10.position}/${x10.parts}/${color}/${i}.png")
                                                }
                                                colorList.add(ColorModel(color, pathList))
                                            }
                                        }

                                        bodyPartList.add(
                                            BodyPartModel(
                                                "${CONST.BASE_URL}${CONST.BASE_CONNECT}$key/${x10.parts}/nav.png",
                                                colorList
                                            )
                                        )
                                    }

                                    val dataModel = CustomModel(
                                        "${CONST.BASE_URL}${CONST.BASE_CONNECT}$key/avatar.png",
                                        bodyPartList,
                                        true
                                    )

                                    // Thêm "dice" và "none"
                                    dataModel.bodyPart.forEach { mbodyPath ->
                                        if (mbodyPath.icon.substringBeforeLast("/")
                                                .substringAfterLast("/").substringAfter("-") == "1"
                                        ) {
                                            mbodyPath.listPath.forEach { colorModel ->
                                                if (colorModel.listPath[0] != "dice") {
                                                    colorModel.listPath.add(0, "dice")
                                                }
                                            }
                                        } else {
                                            mbodyPath.listPath.forEach { colorModel ->
                                                if (colorModel.listPath[0] != "none") {
                                                    colorModel.listPath.add(0, "none")
                                                    colorModel.listPath.add(1, "dice")
                                                }
                                            }
                                        }
                                    }

                                    DataHelper.arrBlackCentered.add(0, dataModel)
                                }
                            }
                        }

                        // Set dataReady sau khi merge xong
                        dataReady = true

                        // Nếu đã qua 3 giây thì chuyển màn ngay
                        if (minDelayPassed) {
                            navigateToNextScreen()
                        }
                    }

                    LoadingStatus.Error -> {
                        // Nếu lỗi nhưng đã có data offline thì vẫn cho qua
                        if (DataHelper.arrBlackCentered.isNotEmpty()) {
                            dataReady = true
                            if (minDelayPassed) {
                                navigateToNextScreen()
                            }
                        } else {
                            // Thử load lại sau 2 giây
                            lifecycleScope.launch(Dispatchers.IO) {
                                delay(2000)
                                getData(apiRepository)
                            }
                        }
                    }
                    else -> {
                        // Loading hoặc trạng thái khác - đợi
                    }
                }
            }
        }
    }

    private fun navigateToNextScreen() {
        // Double-check: CHỈ navigate khi data thực sự sẵn sàng
        if (!dataReady || DataHelper.arrBlackCentered.isEmpty()) {
            return
        }
        if (!sharedPreferenceUtils.getBooleanValue(CONST.LANGUAGE)) {
            startActivity(Intent(this@SplashActivity, LanguageActivity::class.java))
        } else {
            startActivity(Intent(this@SplashActivity, TutorialActivity::class.java))
        }
        finish()
    }

    override fun onBackPressed() {
        // Không cho phép back ở splash
    }
}