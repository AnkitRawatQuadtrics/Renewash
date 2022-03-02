package com.quadtric.renewash.activities

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.animation.doOnEnd
import com.quadtric.renewash.R

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addInitialDataListener()
        loadAppView()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // custom exit on splashScreen
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                // custom animation.
                ObjectAnimator.ofFloat(
                    splashScreenView,
                    View.TRANSLATION_X,
                    0f,
                    splashScreenView.width.toFloat()
                ).apply {
                    duration = 5000
                    // Call SplashScreenView.remove at the end of your custom animation.
                    doOnEnd {
                        splashScreenView.remove()
                    }
                }.also {
                    // Run your animation.
                    it.start()
                }
            }
        }
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun addInitialDataListener() {
        val content: View = findViewById(android.R.id.content)
        // This would be called until true is not returned from the condition
        content.viewTreeObserver.addOnPreDrawListener {
            return@addOnPreDrawListener true
        }
    }

    private fun loadAppView() {
        setContentView(R.layout.activity_main)
    }
}