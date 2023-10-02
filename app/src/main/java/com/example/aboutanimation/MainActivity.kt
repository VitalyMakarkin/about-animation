package com.example.aboutanimation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.LinearInterpolator
import androidx.core.view.isVisible
import com.example.aboutanimation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//         FIRST
        binding.clickMeBtn.setOnClickListener {
            it.animate().alpha(0F)
                .setDuration(1000)
                .setInterpolator(LinearInterpolator())
                .start()
        }

//        SECOND - ValueAnimator
        val defaultAnimator = ValueAnimator.ofInt(100).apply {
            duration = 2000
            addUpdateListener {
                Log.d("Animation", it.animatedValue.toString())
                binding.clickMeBtn.alpha = (animatedValue as Int).toFloat() / 100
            }
        }

        val accelerateAnimator = ValueAnimator.ofInt(100).apply {
            duration = 2000
            addUpdateListener {
                Log.d("Animation", it.animatedValue.toString())
                interpolator = AccelerateInterpolator()
                binding.clickMeBtn.alpha = (animatedValue as Int).toFloat() / 100
            }
        }

        binding.clickMePlsBtn.setOnClickListener {
            defaultAnimator.start()
        }

        val bounceAnimator = ValueAnimator.ofInt(1000).apply {
            duration = 2000
            addUpdateListener {
                Log.d("Animation", it.animatedValue.toString())
                interpolator = BounceInterpolator()
                binding.helloTv.translationY = (animatedValue as Int - 1000).toFloat()
            }
        }

        binding.clickMeTooBtn.setOnClickListener {
            bounceAnimator.start()
        }

//        THIRD - ObjectAnimator
        binding.clickAndMeBtn.setOnClickListener { currentButton ->
            // в property можно передать текст, т.е. свойство, даже кастомное свойство
            ObjectAnimator.ofFloat(currentButton, View.ALPHA, 0f) // 1f
                .apply {
                    duration = 1000
                    interpolator = LinearInterpolator()
                }
                .start()
        }

//        FOURTH - ViewPropertyAnimator
        binding.clickCapsBtn.setOnClickListener {
//            binding.clickMeBtn.animate()
//                .translationX(200f)
//                .start()

//            binding.helloTv.animate()
//                .rotationBy(90f)
//                .start()

//            binding.clickMePlsBtn.animate()
//                .alpha(0f)
//                .start()

//            binding.clickMePlsBtn.animate()
//                .alpha(0f)
//                .setListener(object : Animator.AnimatorListener {
//                    override fun onAnimationStart(animation: Animator) {
//                        TODO("Not yet implemented")
//                    }
//
//                    override fun onAnimationEnd(animation: Animator) {
//                        TODO("Not yet implemented")
//                    }
//
//                    override fun onAnimationCancel(animation: Animator) {
//                        TODO("Not yet implemented")
//                    }
//
//                    override fun onAnimationRepeat(animation: Animator) {
//                        TODO("Not yet implemented")
//                    }
//
//                })
//                .start()

            binding.clickMePlsBtn.animate()
                .alpha(0f)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
//                        binding.clickMePlsBtn.isClickable = false
//                        binding.clickMePlsBtn.isVisible = false
                        binding.clickMePlsBtn.visibility = View.INVISIBLE
                    }
                })
                .start()
        }

//        FOURTH - AnimationSet
        val rotationAnimator = ObjectAnimator.ofFloat(binding.helloTv, View.ROTATION, 360f)
            .apply {
                duration = 2000
                interpolator = LinearInterpolator()
            }

        val translationYAnimator =
            ObjectAnimator.ofFloat(binding.helloTv, View.TRANSLATION_Y, -400f, 0f)
                .apply {
                    duration = 2000
                    interpolator = AccelerateInterpolator()
                }

        binding.smileBtn.setOnClickListener {
            AnimatorSet().apply {
                playTogether(rotationAnimator, translationYAnimator)
                start()
            }
        }
    }
}