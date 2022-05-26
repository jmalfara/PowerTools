package com.jmat.powertools.base.data

import android.graphics.drawable.Drawable
import android.util.Log
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.*
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.resume

class ImageDownloadService(
    private val requestManager: RequestManager
) {
    suspend fun downloadImages(imageUrls: List<String>) = coroutineScope {
        val deferred = async {
            imageUrls.forEach { url ->
                downloadImage(url)
            }
        }
        deferred.await()
    }

    private suspend fun downloadImage(imageUrl: String) {
        suspendCancellableCoroutine<Unit> { continuation ->
            requestManager.downloadOnly()
                .load(imageUrl)
                .into(object : CustomTarget<File>(){
                    override fun onResourceReady(resource: File, transition: Transition<in File>?) {
                        Log.d("ImageLoading", "Ready")
                        continuation.resume(Unit)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        Log.d("ImageLoading", "Cleared")
                        continuation.resume(Unit)
                    }
                })
        }
    }
}
