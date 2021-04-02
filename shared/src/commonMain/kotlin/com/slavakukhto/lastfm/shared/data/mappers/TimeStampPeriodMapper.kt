package com.slavakukhto.lastfm.shared.data.mappers

import com.slavakukhto.lastfm.shared.domain.models.TimeStampPeriod

class TimeStampPeriodMapper {

    fun parsePeriod(period: String): TimeStampPeriod {
        return when (period) {
            TimeStampPeriod.OVERALL.name -> TimeStampPeriod.OVERALL
            TimeStampPeriod.DAYS7.name -> TimeStampPeriod.DAYS7
            TimeStampPeriod.DAYS7.name -> TimeStampPeriod.DAYS7
            TimeStampPeriod.MONTH1.name -> TimeStampPeriod.MONTH1
            TimeStampPeriod.MONTH3.name -> TimeStampPeriod.MONTH3
            TimeStampPeriod.MONTH6.name -> TimeStampPeriod.MONTH6
            else -> TimeStampPeriod.MONTH12
        }
    }

    fun getPeriodQueryValue(period: TimeStampPeriod): String {
        return when (period) {
            TimeStampPeriod.OVERALL -> "ALL"
            TimeStampPeriod.DAYS7 -> "LAST_7_DAYS"
            TimeStampPeriod.MONTH1 -> "LAST_30_DAYS"
            TimeStampPeriod.MONTH3 -> "LAST_90_DAYS"
            TimeStampPeriod.MONTH6 -> "LAST_180_DAYS"
            TimeStampPeriod.MONTH12 -> "LAST_365_DAYS"
        }
    }

    fun getPeriodTopValuesQuery(period: TimeStampPeriod): String {
        return when (period) {
            TimeStampPeriod.OVERALL -> "Overall"
            TimeStampPeriod.DAYS7 -> "7day"
            TimeStampPeriod.MONTH1 -> "1month"
            TimeStampPeriod.MONTH3 -> "3month"
            TimeStampPeriod.MONTH6 -> "6month"
            TimeStampPeriod.MONTH12 -> "12month"
        }
    }
}
