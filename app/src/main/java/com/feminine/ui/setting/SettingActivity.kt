package com.feminine.ui.setting

import android.view.View
import com.feminine.base.AbsBaseActivity
import com.feminine.ui.language.LanguageActivity
import com.feminine.utils.RATE
import com.feminine.utils.SharedPreferenceUtils
import com.feminine.utils.newIntent
import com.feminine.utils.onSingleClick
import com.feminine.utils.policy
import com.feminine.utils.rateUs
import com.feminine.utils.shareApp
import com.feminine.utils.unItem
import com.feminine.R
import com.feminine.databinding.ActivitySettingBinding
import com.feminine.utils.show
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