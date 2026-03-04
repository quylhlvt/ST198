package com.female.maker.oc.creator.ui.setting

import android.view.View
import com.female.maker.oc.creator.base.AbsBaseActivity
import com.female.maker.oc.creator.ui.language.LanguageActivity
import com.female.maker.oc.creator.utils.RATE
import com.female.maker.oc.creator.utils.SharedPreferenceUtils
import com.female.maker.oc.creator.utils.newIntent
import com.female.maker.oc.creator.utils.onSingleClick
import com.female.maker.oc.creator.utils.policy
import com.female.maker.oc.creator.utils.rateUs
import com.female.maker.oc.creator.utils.shareApp
import com.female.maker.oc.creator.utils.unItem
import com.female.maker.oc.creator.R
import com.female.maker.oc.creator.databinding.ActivitySettingBinding
import com.female.maker.oc.creator.utils.show
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingActivity : AbsBaseActivity<ActivitySettingBinding>() {
    @Inject
    lateinit var sharedPreferences: SharedPreferenceUtils
    override fun getLayoutId(): Int = R.layout.activity_setting

    override fun initView() {
        binding.actionBar.backCustom.show()
        binding.actionBar.txtCustom.text = getString(R.string.setting)
        binding.actionBar.txtCustom.isSelected = true
        if (sharedPreferences.getBooleanValue(RATE)) {
            binding.llRateUs.visibility = View.GONE
        }
        unItem = {
            binding.llRateUs.visibility = View.GONE
        }
    }

    override fun onStop() {
        super.onStop()
    }
    override fun initAction() {
        binding.apply {
            llLanguage.onSingleClick {
                startActivity(
                    newIntent(
                        applicationContext,
                        LanguageActivity::class.java
                    )
                )
            }
//            imvMusic.onSingleClick {
//                initMusic(imvMusic)
//            }
            llRateUs.onSingleClick {
                rateUs(0)
            }
            llShareApp.onSingleClick {
                shareApp()
            }
            llPrivacy.onSingleClick {
                policy()
            }
            binding.actionBar.backCustom.onSingleClick {
                finish()
            }
        }
    }
}