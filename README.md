# Simple_player_view is a simple video and music player.

## 1. Download

### Gradle
    repositories {
      google()
      jcenter()
    }

    dependencies {
      implementation 'com.gunwook.simplemediaplayer:simple-media-player:0.0.2'
    }
### Maven
    <dependency>
      <groupId>com.gunwook.simplemediaplayer</groupId>
      <artifactId>simple-media-player</artifactId>
      <version>0.0.1</version>
      <type>pom</type>
    </dependency>
  
  
  
## 2. How do I use simple_player_view?
### 1.1. Dialog?
    MusicDialog(this, mutableListOf(MusicModel(mp3,mp3_title)),title).show()
    
### 1.2. PopupWindow?
    MusicPopUp.show(this ,  mutableListOf(MusicModel(mp3,mp3_title)))
    MusicPopUp.dismiss(this ,  mutableListOf(MusicModel(mp3,mp3_title)))
    
### 1.3. Activity?
    val intent = Intent(this , VideoActivity::class.java)
    intent.putParcelableArrayListExtra("video_urls" , arrayListOf(VideoModel(thumbNail, videoUrl , title)))
    startActivity(intent)
    
## 3. Permission ?
    <uses-permission android:name="android.permission.INTERNET" />

## 4. Requirements ?
    minSdkVersion 17

    compileOptions {
            sourceCompatibility JavaVersion.VERSION_1_8
            targetCompatibility JavaVersion.VERSION_1_8
    }

    dataBinding {
            enabled = true
    }

