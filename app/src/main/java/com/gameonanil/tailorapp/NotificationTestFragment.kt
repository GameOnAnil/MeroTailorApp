package com.gameonanil.tailorapp

import android.app.*
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.gameonanil.tailorapp.databinding.FragmentNotificationTestBinding
import com.gameonanil.tailorapp.utils.*
import com.gameonanil.tailorapp.utils.Notification
import java.util.*


class NotificationTestFragment : Fragment() {
    companion object {
        private const val TAG = "NotificationFragment"
    }

    private var _binding: FragmentNotificationTestBinding? = null
    private val binding: FragmentNotificationTestBinding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationTestBinding.inflate(inflater, container, false)

        createNotificationChannel()

        binding.apply {
            btnNotify.setOnClickListener {
                scheduleNotification()
            }
            btnDeleteNotification.setOnClickListener {
                deleteNotification()
            }
        }

        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun scheduleNotification() {
        val intent = Intent(requireContext().applicationContext, Notification::class.java)
        val title = "Notify title"
        val message = "Custom msg"
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext().applicationContext,
            notificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getCustomTime()

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
        showAlert(time, title, message)
        Log.d(TAG, "scheduleNotification:alarm set at=  $time")
    }

    private fun showAlert(time: Long, title: String, message: String) {
        val date = Date(time)
        val dateFormat =
            android.text.format.DateFormat.getLongDateFormat(requireContext().applicationContext)
        val timeFormat =
            android.text.format.DateFormat.getTimeFormat(requireContext().applicationContext)

        AlertDialog.Builder(requireContext())
            .setTitle("Notification Scheduled")
            .setMessage(
                "Title: $title, Message: $message at :${dateFormat.format(date)} ${
                    timeFormat.format(
                        time
                    )
                }"
            )
            .show()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getTime(): Long {
        binding.apply {
            val minute = timePicker.minute
            val hour = timePicker.hour
            val day = datePicker.dayOfMonth
            val month = datePicker.month
            val year = datePicker.year

            val calendar = Calendar.getInstance()
            calendar.set(year, month, day, hour, minute)
            return calendar.timeInMillis
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun deleteNotification() {
        val intent = Intent(requireContext().applicationContext, Notification::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext().applicationContext,
            notificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.cancel(pendingIntent)

    }

    private fun getCustomTime(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + 3)

        return calendar.timeInMillis
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Notification Channel"
            val desc = "A description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)
            channel.description = desc
            val notificationManager =
                requireActivity().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}