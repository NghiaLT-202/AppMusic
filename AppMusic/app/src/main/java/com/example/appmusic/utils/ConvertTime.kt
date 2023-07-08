package com.example.appmusic.utils

import android.content.Context
import android.os.Build
import com.example.tfmmusic.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.util.Calendar

object ConvertTime {
    fun partStringDuration(duration: String?): Long {
        val format = SimpleDateFormat("'PT'm'M's'S'")
        return try {
            val date = format.parse(duration)
            date.time
        } catch (e: ParseException) {
            e.printStackTrace()
            0
        }
    }

    @SuppressLint("SetTextI18n")
    fun convertLikeCount(items: Items?): String? {
        var textView: String? = null
        val like: String = items.getStatistics().getLikeCount()
        if (like == null) {
            textView = "0"
        } else {
            if (items != null) {
                val likeCount = like.toInt().toLong()
                if (likeCount < 1000) {
                    textView = likeCount.toString() + ""
                } else if (likeCount / 1000 < 1000) {
                    textView = (likeCount / 1000).toString() + "K"
                } else if (likeCount / 1000000 < 1000 && likeCount / 1000000 > 0) {
                    textView = (likeCount / 1000000).toString() + "M"
                } else {
                    textView = (likeCount / 1000000000).toString() + "B"
                }
            }
        }
        return textView
    }

    @SuppressLint("SetTextI18n")
    fun convertCommentView(videoYoutube: Items?): String? {
        var textView: String? = null
        if (videoYoutube != null) {
            val comment: String = videoYoutube.getStatistics().getCommentCount()
            if (comment == null) {
                textView = "0"
            } else {
                val likeCount: Long = videoYoutube.getStatistics().getCommentCount().toInt()
                if (likeCount < 1000) {
                    textView = likeCount.toString() + ""
                } else if (likeCount / 1000 < 1000) {
                    textView = (likeCount / 1000).toString() + "K"
                } else if (likeCount / 1000000 < 1000 && likeCount / 1000000 > 0) {
                    textView = (likeCount / 1000000).toString() + "M"
                } else {
                    textView = (likeCount / 1000000000).toString() + "B"
                }
            }
        }
        return textView
    }

    fun convertDisLikeCount(items: Items?): String? {
        var textView: String? = null
        if (items != null) {
            val like: String = items.getStatistics().getDislikeCount()
            if (like == null) {
                textView = "0"
            } else {
                val likeCount = like.toInt().toLong()
                if (likeCount < 1000) {
                    textView = likeCount.toString() + ""
                } else if (likeCount / 1000 < 1000) {
                    textView = (likeCount / 1000).toString() + "K"
                } else if (likeCount / 1000000 < 1000 && likeCount / 1000000 > 0) {
                    textView = (likeCount / 1000000).toString() + "M"
                } else {
                    textView = (likeCount / 1000000000).toString() + "B"
                }
            }
        }
        return textView
    }

    @SuppressLint("SetTextI18n")
    fun convertViewCount(context: Context, view: String?): String? {
        var textView: String? = null
        if (view == null) {
            textView = "0"
        } else {
            val viewCount = view.toLong()
            if (viewCount < 1000) {
                textView = "$viewCount " + context.getString(R.string.view_count)
            }
            if (viewCount >= 1000 && viewCount < 1000000) {
                textView = (viewCount / 1000).toString() + context.getString(R.string.view_count_k)
            }
            if (viewCount >= 1000000 && viewCount < 1000000000) {
                textView = (viewCount / 1000000).toString() + context.getString(R.string.view_count_m)
            }
            if (viewCount >= 1000000000) {
                textView = (viewCount / 1000000000).toString() + context.getString(R.string.view_count_b)
            }
        }
        return textView
    }

    @SuppressLint("SetTextI18n")
    fun convertSub(items: Items): String? {
        var textView: String? = null
        val view: String = items.getStatistics().getSubscriberCount()
        if (items.getStatistics() != null && view == null) {
            textView = "0"
        } else {
            val viewCount = view.toLong()
            if (viewCount >= 1000 && viewCount < 1000000) {
                textView = (viewCount / 1000).toString() + " " + "K views"
            }
            if (viewCount >= 1000000 && viewCount < 1000000000) {
                textView = (viewCount / 1000000).toString() + " " + "M views"
            }
            if (viewCount >= 1000000000) {
                textView = (viewCount / 1000000000).toString() + " " + "B views"
            }
        }
        return textView
    }

    fun convertTime(myDate: String): String? {
        val time: String? = null
        val today = Calendar.getInstance()
        //        String myDate = item.getSnippet().getPublishedAt();
        var inputModified = myDate.replace(" ", "T")
        Timber.e("huongdt: myDate $myDate")
        Timber.e("huongdt: inputModified $inputModified")
        val lengthOfAbbreviatedOffset = 3
        if (inputModified.indexOf("+") == inputModified.length - lengthOfAbbreviatedOffset) {
            Timber.e("huongdt inputModifyLength: " + inputModified.length)
            Timber.e("huongdt: indexOf+ " + inputModified.indexOf("+"))
            Timber.e("huongdt: indexOf- " + inputModified.indexOf("-"))
            // If third character from end is a PLUS SIGN, append ':00'.
            inputModified = "$inputModified:00"
        }
        if (inputModified.indexOf("-") == inputModified.length - lengthOfAbbreviatedOffset) {
            // If third character from end is a PLUS SIGN, append ':00'.
            inputModified = "$inputModified:00"
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val odt = OffsetDateTime.parse(inputModified)
            val millis = odt
                .toInstant().toEpochMilli()
            val diff = today.timeInMillis - millis
            val days = diff / (24 * 60 * 60 * 1000)
            val year = days / 365
            val month = (days - year * 365) / 30
            val day = days - year * 365 - month * 30
            val hour = diff / (60 * 60 * 1000) - year * 365 * 24 - month * 30 * 24 - day * 24
            val minute =
                diff / (60 * 1000) - year * 365 * 24 * 60 - month * 30 * 24 * 60 - day * 24 * 60 - hour * 60

//            if (year > 0) {
//                time = year + " " + context.getString(R.string.year_ago);
//            } else if (month > 0 && year == 0) {
//                time = month + " " + context.getString(R.string.mon_ago);
//            } else if (day > 0 && month == 0 && year == 0) {
//                time = day + " " + context.getString(R.string.day_ago);
//            } else if (hour > 0 && day == 0 && month == 0 && year == 0) {
//                time = hour + " " + context.getString(R.string.hour_ago);
//            } else if (minute > 0 && hour == 0 && day == 0 && month == 0 && year == 0) {
//                time = minute + " " + context.getString(R.string.minute_ago);
//            }
        }
        return time
    }

    fun convertDuration(time: Long): String {
        var duration = "01:52:33"
        if (time > 0 && time < 60) {
            duration = if (time < 10) {
                "00:0$time"
            } else {
                "00:$time"
            }
        }
        if (time >= 60 && time < 3600) {
            if (time / 60 < 10) {
                duration = if (time % 60 < 10) {
                    "0$time" / 60 + ":0" + time % 60
                } else {
                    "0$time" / 60 + ":" + time % 60
                }
            } else {
                if (time % 60 < 10) {
                    duration = (time / 60).toString() + ":0" + time % 60
                } else {
                    duration = (time / 60).toString() + ":" + time % 60
                }
            }
        }
        if (time >= 3600) {
            duration = if (time / 3600 < 10) {
                if (time % 3600 / 60 < 10) {
                    if (time % 3600 % 60 < 10) {
                        "0$time" / 3600 + ":0" + time % 3600 / 60 + ":0" + time % 3600 % 60
                    } else {
                        "0$time" / 3600 + ":0" + time % 3600 / 60 + ":" + time % 3600 % 60
                    }
                } else {
                    if (time % 3600 % 60 < 10) {
                        "0$time" / 3600 + ":" + time % 3600 / 60 + ":0" + time % 3600 % 60
                    } else {
                        "0$time" / 3600 + ":" + time % 3600 / 60 + ":" + time % 3600 % 60
                    }
                }
            } else {
                if (time % 3600 / 60 < 10) {
                    if (time % 3600 % 60 < 10) {
                        (time / 3600).toString() + ":0" + time % 3600 / 60 + ":0" + time % 3600 % 60
                    } else {
                        (time / 3600).toString() + ":0" + time % 3600 / 60 + ":" + time % 3600 % 60
                    }
                } else {
                    if (time % 3600 % 60 < 10) {
                        (time / 3600).toString() + ":" + time % 3600 / 60 + ":0" + time % 3600 % 60
                    } else {
                        (time / 3600).toString() + ":" + time % 3600 / 60 + ":" + time % 3600 % 60
                    }
                }
            }
        }
        return duration
    }
}