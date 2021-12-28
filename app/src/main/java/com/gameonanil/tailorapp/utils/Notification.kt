package com.gameonanil.tailorapp.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.gameonanil.tailorapp.R
import com.gameonanil.tailorapp.ui.MainActivity


const val channelId = "channelId1"
const val titleExtra = "Title"
const val messageExtra = "messageExtra"
const val notificationIdExtra = "notificationExtra"

class Notification : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        val i = Intent(context, MainActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, i, 0)

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.tailor)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setContentText(intent.getStringExtra(messageExtra))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(intent.getIntExtra(notificationIdExtra, 0), notification)


    }

}
