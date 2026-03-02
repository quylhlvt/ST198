package com.feminine.base

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.feminine.utils.SystemUtils
import com.feminine.utils.music.MusicLocal
import com.feminine.utils.showSystemUI
import com.feminine.R

abstract class AbsBaseActivity<V : ViewDataBinding> : AppCompatActivity() {
    lateinit var binding: V
    open fun isRequireData(): Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SystemUtils.setLocale(this)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        initView()
        initAction()
    }

    override fun onStart() {
        super.onStart()
    }
    override fun onResume() {
        super.onResume()
            showSystemUI()
        if (isRequireData() && isDataLost()) {
            restartToMain()
        }
    }
    private fun isDataLost(): Boolean {
        return com.feminine.utils.DataHelper.arrBlackCentered.isEmpty()
    }
    private fun restartToMain() {
        val intent = android.content.Intent(this, com.feminine.ui.main.MainActivity::class.java).apply {
            flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK or
                    android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }
    override fun onRestart() {
        super.onRestart()
        SystemUtils.setLocale(this)
    }
    abstract fun getLayoutId(): Int
    abstract fun initView()
    abstract fun initAction()
    fun initMusic( musicButtons: ImageView){
        var isPlayer= MusicLocal.status(this@AbsBaseActivity)
        isPlayer = ! isPlayer
        MusicLocal.toggle(this@AbsBaseActivity,isPlayer)
        updateMusicIcon(musicButtons)
    }
    open fun updateMusicIcon( musicButtons: ImageView) {
        val isPlaying = MusicLocal.status(this@AbsBaseActivity)
    if (isPlaying) {
        musicButtons.setImageResource( R.drawable.ic_music_app_true)
        MusicLocal.play(this)
    }
    else {
        musicButtons.setImageResource(R.drawable.ic_music_app_false)
        MusicLocal.pause()
    }
}


}