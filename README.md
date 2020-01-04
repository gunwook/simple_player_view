# Simple_player_view is a library of videos and music.


## 1 Download

### Gradle
    repositories {
      mavenCentral()
      google()
    }

    dependencies {
      implementation 'com.github.bumptech.glide:glide:4.10.0'
      annotationProcessor 'com.github.bumptech.glide:compiler:4.10.0'
    }
### Maven
    <dependency>
      <groupId>com.gunwook.simplemediaplayer</groupId>
      <artifactId>simple-media-player</artifactId>
      <version>0.0.1</version>
      <type>pom</type>
    </dependency>
  
  
  
# 1. How do I use simple_player_view?
## 1.1. Dialog?
    MusicDialog(this, mutableListOf(MusicModel(mp3,mp3_title)),title).show()
    
## 1.2. PopupWindow?
    MusicPopUp.show(this ,  mutableListOf(MusicModel(mp3,mp3_title)))
    MusicPopUp.dismiss(this ,  mutableListOf(MusicModel(mp3,mp3_title)))
    
## 1.3. Activity?
    val intent = Intent(this , VideoActivity::class.java)
    intent.putParcelableArrayListExtra("video_urls" , arrayListOf(VideoModel(thumbNail, videoUrl , title)))
    startActivity(intent)
