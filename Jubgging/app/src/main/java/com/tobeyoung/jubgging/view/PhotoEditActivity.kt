package com.tobeyoung.jubgging.view

import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.tobeyoung.jubgging.R
import com.tobeyoung.jubgging.databinding.ActivityPhotoEditBinding
import com.tobeyoung.jubgging.viewmodel.PhotoShareViewModel
import ja.burhanrashid52.photoeditor.OnSaveBitmap
import ja.burhanrashid52.photoeditor.PhotoEditor
import ja.burhanrashid52.photoeditor.PhotoEditorView
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class PhotoEditActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityPhotoEditBinding
    private val viewModel: PhotoShareViewModel by viewModels()
    private var colorCode: String = "#FF000000"
    private var color: Int = 0
    private var logo: Bitmap? = null
    private var uriString = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_photo_edit)
        binding.photoShareVm = viewModel
        binding.lifecycleOwner = this
        val uriString = intent.getStringExtra("photoUri")

        val photoUri = Uri.parse(uriString)
        val speed = intent.getStringExtra("speed")
        val distance = intent.getStringExtra("distance")
        val time = intent.getStringExtra("time")

        color = ResourcesCompat.getColor(this.resources, R.color.black, theme)


        val photoEditorView = findViewById<PhotoEditorView>(R.id.pe_v)

        val pretendardFont = ResourcesCompat.getFont(this, R.font.pretendard_semibold)

        val photoEditor = PhotoEditor.Builder(this, photoEditorView)


        if (photoUri != null) {
            Glide.with(this).load(photoUri).into(binding.peV.source)
        }
        photoEditor.build()
            .addImage(Bitmap.createBitmap(BitmapFactory.decodeResource(this.resources,
                R.drawable.ic_app_title_black)))


        binding.peGetSpeedBtn.setOnClickListener {
            photoEditor.build().addText(pretendardFont, speed, color)
            //icon 추가
            photoEditor.build().addImage(getBitmapFromVectorDrawable(this,
                R.drawable.ic_baseline_speed_24,
                colorCode))

        }
        binding.peGetDistanceBtn.setOnClickListener {
            photoEditor.build().addText(pretendardFont, "$distance Km", color)
            //icon 추가
            photoEditor.build().addImage(getBitmapFromVectorDrawable(this,
                R.drawable.ic_baseline_electric_bolt_24,
                colorCode))

        }
        binding.peGetTimeBtn.setOnClickListener {
            photoEditor.build().addText(pretendardFont, time, color)
            //icon 추가
            photoEditor.build().addImage(getBitmapFromVectorDrawable(this,
                R.drawable.ic_baseline_access_time_24,
                colorCode))
        }

        binding.peLogoBlackBtn.setOnClickListener {
            logo = Bitmap.createBitmap(BitmapFactory.decodeResource(this.resources,
                R.drawable.ic_app_title_black))
            photoEditor.build().addImage(logo)
        }
        binding.peLogoWhiteBtn.setOnClickListener {
            logo = Bitmap.createBitmap(BitmapFactory.decodeResource(this.resources,
                R.drawable.ic_app_title_white))
            photoEditor.build().addImage(logo)
        }

        binding.peBlackBtn.setOnClickListener(this)
        binding.peWhiteBtn.setOnClickListener(this)
        binding.peRedBtn.setOnClickListener(this)
        binding.peGreenBtn.setOnClickListener(this)
        binding.peBlueBtn.setOnClickListener(this)
        binding.peYellowBtn.setOnClickListener(this)

        binding.editSaveBtn.setOnClickListener {
            photoEditor.build().saveAsBitmap(object : OnSaveBitmap {
                override fun onBitmapReady(saveBitmap: Bitmap?) {
                    imageSaveAndShare()
                }

                override fun onFailure(e: Exception?) {
                    Log.d("TAG", "onFailure: fail ")
                }
            })

        }
    }
    fun imageSaveAndShare() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val bgBitmap = drawBackgroundBitmap()
            val bgUri = saveImageAtCacheDir(bgBitmap)

            val viewBitmap = drawViewBitmap()
            val viewUri = saveImageAtCacheDir(viewBitmap)
            saveImageOnAboveAndroidQ(viewBitmap)
            //instaShare(bgUri, viewUri)
            instaShare(bgUri,viewUri)
        } else {
            val bgBitmap = drawBackgroundBitmap()
            val bgUri = saveImageAtCacheDir(bgBitmap)

            val viewBitmap = drawViewBitmap()
            val viewUri = saveImageAtCacheDir(viewBitmap)
            saveImageOnUnderAndroidQ(viewBitmap)
            //instaShare(bgUri, viewUri)
            instaShare(bgUri,viewUri)
        }
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.peBlackBtn -> {
                colorCode = "#FF000000"
                color = ResourcesCompat.getColor(this.resources, R.color.black, theme)
            }
            binding.peWhiteBtn -> {
                colorCode = "#FFFFFFFF"
                color = ResourcesCompat.getColor(this.resources, R.color.white, theme)
            }
            binding.peRedBtn -> {
                colorCode = "#FF3939"
                color = ResourcesCompat.getColor(this.resources, R.color.orangey_red, theme)
            }
            binding.peGreenBtn -> {
                colorCode = "#00B786"
                color = ResourcesCompat.getColor(this.resources, R.color.green_blue, theme)

            }
            binding.peBlueBtn -> {
                colorCode = "#335AFF"
                color = ResourcesCompat.getColor(this.resources, R.color.lightish_blue, theme)

            }
            binding.peYellowBtn -> {
                colorCode = "#FFC400"
                color = ResourcesCompat.getColor(this.resources, R.color.marigold, theme)

            }
        }
    }

    private fun getBitmapFromVectorDrawable(
        context: Context,
        drawableId: Int,
        colorCode: String,
    ): Bitmap? {
        val drawable = ContextCompat.getDrawable(context, drawableId)
        drawable?.setTintList(ColorStateList.valueOf(Color.parseColor(colorCode)))
        val bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth,
            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }


    private fun instaShare(bgUri: Uri?, viewUri: Uri?) {
// Define image asset URI
        val stickerAssetUri = Uri.parse(viewUri.toString())
        val sourceApplication = "com.tobeyoung.jubgging"

// Instantiate implicit intent with ADD_TO_STORY action,
// sticker asset, and background colors
        val intent = Intent("com.instagram.share.ADD_TO_STORY")
        intent.putExtra("source_application", sourceApplication)

        intent.type = "image/png"
        intent.setDataAndType(bgUri, "image/png");
        intent.putExtra("interactive_asset_uri", stickerAssetUri)

// Instantiate activity and verify it will resolve implicit intent
        grantUriPermission(
            "com.instagram.android", stickerAssetUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
        )

        grantUriPermission(
            "com.instagram.android", bgUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
        )

        try {
            this.startActivity(intent)
        } catch (e : ActivityNotFoundException) {
            Toast.makeText(applicationContext, "인스타그램 앱이 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    // 화면에 나타난 View를 Bitmap에 그릴 용도.
    private fun drawBackgroundBitmap(): Bitmap {
        //기기 해상도를 가져옴.
        val backgroundWidth = resources.displayMetrics.widthPixels
        val backgroundHeight = resources.displayMetrics.heightPixels

        val backgroundBitmap = Bitmap.createBitmap(backgroundWidth, backgroundHeight, Bitmap.Config.ARGB_8888) // 비트맵 생성
        val canvas = Canvas(backgroundBitmap) // 캔버스에 비트맵을 Mapping.

        val bgColor = binding.photoShareVm?.background?.value // 뷰모델의 현재 설정된 배경색을 가져온다.
        if(bgColor != null) {
            val color = ContextCompat.getColor(baseContext, bgColor)
            canvas.drawColor(color) // 캔버스에 현재 설정된 배경화면색으로 칠한다.
        }

        return backgroundBitmap
    }

    private fun drawViewBitmap(): Bitmap {
        val imageView = binding.peV
//        val margin = resources.displayMetrics.density * 20
        val width = imageView.width
        val height = imageView.height

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val imageViewBitmap = Bitmap.createBitmap(imageView.width, imageView.height, Bitmap.Config.ARGB_8888)
        val imageViewCanvas = Canvas(imageViewBitmap)
        imageView.draw(imageViewCanvas)
        /*imageViewCanvas를 통해서 imageView를 그린다.
         *이 때 스케치북은 imageViewBitmap이므로 imageViewBitmap에 imageView가 그려진다.
         */

        val imageViewLeft = ((width - imageView.width) / 2).toFloat()

        canvas.drawBitmap(imageViewBitmap, imageViewLeft, (0).toFloat(), null)

        return bitmap
    }

    //Android Q (Android 10, API 29 이상에서는 이 메서드를 통해서 이미지를 저장한다.)
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveImageOnAboveAndroidQ(bitmap: Bitmap): Uri? {
        val fileName = System.currentTimeMillis().toString() + ".png" // 파일이름 현재시간.png

        /*
        * ContentValues() 객체 생성.
        * ContentValues는 ContentResolver가 처리할 수 있는 값을 저장해둘 목적으로 사용된다.
        * */
        val contentValues = ContentValues()
        contentValues.apply {
            put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/ImageSave") // 경로 설정
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName) // 파일이름을 put해준다.
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.IS_PENDING, 1) // 현재 is_pending 상태임을 만들어준다.
            // 다른 곳에서 이 데이터를 요구하면 무시하라는 의미로, 해당 저장소를 독점할 수 있다.
        }

        // 이미지를 저장할 uri를 미리 설정해놓는다.
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        try {
            if(uri != null) {
                val image = contentResolver.openFileDescriptor(uri, "w", null)
                // write 모드로 file을 open한다.

                if(image != null) {
                    val fos = FileOutputStream(image.fileDescriptor)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
                    //비트맵을 FileOutputStream를 통해 compress한다.
                    fos.close()

                    contentValues.clear()
                    contentValues.put(MediaStore.Images.Media.IS_PENDING, 0) // 저장소 독점을 해제한다.
                    contentResolver.update(uri, contentValues, null, null)
                }
            }
        } catch(e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return uri
    }

    // Android Q 미만에서 파일 저장 후 Uri 반환해주는 메서드
    private fun saveImageOnUnderAndroidQ(bitmap: Bitmap): Uri? {
        val fileName = System.currentTimeMillis().toString() + ".png"
        val externalStorage = Environment.getExternalStorageDirectory().absolutePath
        val path = "$externalStorage/DCIM/imageSave"
        val dir = File(path)

        if(dir.exists().not()) {
            dir.mkdirs() // 폴더 없을경우 폴더 생성
        }

        val fileItem = File("$dir/$fileName")
        try {
            fileItem.createNewFile()
            //0KB 파일 생성.

            val fos = FileOutputStream(fileItem) // 파일 아웃풋 스트림

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            //파일 아웃풋 스트림 객체를 통해서 Bitmap 압축.

            fos.close() // 파일 아웃풋 스트림 객체 close

            MediaScannerConnection.scanFile(applicationContext, arrayOf(fileItem.toString()), null, null)

            //sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(fileItem)))
            // 브로드캐스트 수신자에게 파일 미디어 스캔 액션 요청. 그리고 데이터로 추가된 파일에 Uri를 넘겨준다. - Deprecated 위 코드로 수정
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return FileProvider.getUriForFile(applicationContext, "com.tobeyoung.jubgging.fileprovider", fileItem)
    }

    // 이미지를 캐시에 저장하는 메서드. Android 버전과 상관 없다.
    private fun saveImageAtCacheDir(bitmap: Bitmap): Uri? {
        val fileName = System.currentTimeMillis().toString() + ".png"
        val cachePath = "$cacheDir/file"
        val dir = File(cachePath)

        if(dir.exists().not()) {
            dir.mkdirs() // 폴더 없을경우 폴더 생성
        }

        val fileItem = File("$dir/$fileName")
        try {
            fileItem.createNewFile()
            //0KB 파일 생성.

            val fos = FileOutputStream(fileItem) // 파일 아웃풋 스트림

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            //파일 아웃풋 스트림 객체를 통해서 Bitmap 압축.

            fos.close() // 파일 아웃풋 스트림 객체 close

            MediaScannerConnection.scanFile(applicationContext, arrayOf(fileItem.toString()), null, null)

            //sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(fileItem)))
            // 브로드캐스트 수신자에게 파일 미디어 스캔 액션 요청. 그리고 데이터로 추가된 파일에 Uri를 넘겨준다. - Deprecated 위 코드로 수정
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return FileProvider.getUriForFile(applicationContext, "com.tobeyoung.jubgging.fileprovider", fileItem)
    }

}