package fr.echos.articles.presentation

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class DateFormatter @Inject constructor() {

    fun format(date: LocalDateTime): String {
        return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
    }

}
