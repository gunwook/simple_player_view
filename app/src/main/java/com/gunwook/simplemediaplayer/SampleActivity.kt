package com.gunwook.simplemediaplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gunwook.simplemediaplayer.model.MusicModel
import com.gunwook.simplemediaplayer.model.VideoModel
import kotlinx.android.synthetic.main.activity_main.*

class SampleActivity : AppCompatActivity() {

    private var mDialog : MusicDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        button1.setOnClickListener {
            val model = MusicModel("https://cdn.gunwooklee.com/sound.mp3" , "제목입니다.")

            mDialog =  MusicDialog(this, mutableListOf(model , model , model , model , model, model) , "hello")
            mDialog?.show()
        }

        button2.setOnClickListener {
            val model = MusicModel("https://cdn.gunwooklee.com/sound.mp3" , "제목입니다.")


            MusicPopUp.show(this ,  mutableListOf(model , model , model , model , model, model))
        }

        button3.setOnClickListener{
            MusicPopUp.dismiss()
        }

        button4.setOnClickListener {

            Intent(this , VideoActivity::class.java).apply {
                this.putParcelableArrayListExtra("video_urls" , arrayListOf(
                    VideoModel("https://cdn.gunwooklee.com/sample.jpeg", "https://cdn.gunwooklee.com/winter.mp4" , "제목입니다."),
                    VideoModel("https://cdn.gunwooklee.com/sample.jpeg", "https://cdn.gunwooklee.com/winter.mp4" , "제목입니다."),
                    VideoModel("https://cdn.gunwooklee.com/sample.jpeg", "https://cdn.gunwooklee.com/winter.mp4" , "제목입니다."),
                    VideoModel("https://cdn.gunwooklee.com/sample.jpeg", "https://cdn.gunwooklee.com/winter.mp4" , "제목입니다."),
                    VideoModel("https://cdn.gunwooklee.com/sample.jpeg", "https://cdn.gunwooklee.com/winter.mp4" , "제목입니다.")

                ) )
                startActivity(this)
            }
        }
    }
}
