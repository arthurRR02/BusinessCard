package com.example.businesscard.util

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.TextureView
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.businesscard.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception

private const val SOLICITAR_PERMISSAO = 1

class ShareImage {

    companion object {
        fun share(context: Context, card: View) {
            val bitmap = getImage(context, card)
            bitmap?.let {
                saveMedia(context, it)
            }
        }

        private fun getImage(context: Context, card: View): Bitmap? {
            var bitmap: Bitmap? = null
            try {
                bitmap = Bitmap.createBitmap(card.width, card.height, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)
                card.draw(canvas)
            } catch (e: Exception) {
                AlertDialog.Builder(context)
                    .setTitle(R.string.error)
                    .setMessage("${R.string.error_message}: ${e.message}")
                    .setNeutralButton("Ok", null)
                    .show()
            }
            return bitmap
        }

        private fun saveMedia(context: Context, bitmap: Bitmap) {
            val fileName = "${System.currentTimeMillis()}.jpg"
            var fos: OutputStream? = null

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                context.contentResolver?.also { resolver ->

                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                    }
                    val imageUri: Uri? =
                        resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                    fos = imageUri?.let {
                        shareIntent(context, it)
                        resolver.openOutputStream(it)
                    }
                }
            } else {
                val imageDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                val image = File(imageDirectory, fileName)
                shareIntent(context, Uri.fromFile(image))
                fos = FileOutputStream(image)
            }

            fos?.use {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                Toast.makeText(context, "Imagem capturada com sucesso", Toast.LENGTH_SHORT).show()
            }
        }

        private fun shareIntent(context: Context, imageUri: Uri) {
            var shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, imageUri)
                type = "image/jpeg"
                putExtra(Intent.EXTRA_TEXT, context.getString(R.string.card_message))
                type = "*/*"
            }
            context.startActivity(
                Intent.createChooser(
                    shareIntent,
                    context.resources.getText(R.string.share)
                )
            )
        }
    }
}