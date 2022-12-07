package com.miempresa.notificacionesv4nina

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.onesignal.OneSignal
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var ID = 1
    val ONESIGNAL_APP_ID = "89a4a736-6be3-452f-9234-c1aa9d0a648b"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)

    }

    fun crearCanalNotificacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = getString(R.string.channel_name)
            val description = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(getString(R.string.channel_id), name, importance)
            channel.description = description
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun notificacionOreo(v: View?){
        crearCanalNotificacion()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(this, 0,intent,0)
        val mBuilder =
            NotificationCompat.Builder(this,getString(R.string.channel_id))
                .setSmallIcon(R.drawable.imgnotificacion)
                .setContentTitle("Notificacion Oreo")
                .setContentText("Mi primera notificacion en Oreo")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.imgnotificacion))
                .setStyle(NotificationCompat.BigPictureStyle()
                    .bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.imagen))
                    .bigLargeIcon(null))
                .setAutoCancel(false)
                .setOngoing(true)
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(ID++,mBuilder.build())
    }
}