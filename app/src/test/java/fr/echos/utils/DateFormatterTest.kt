package fr.echos.utils

import fr.echos.utils.DateFormatter
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class DateFormatterTest {

        @InjectMocks
        private lateinit var dateFormatter: DateFormatter

        @Test
        fun `format date`() {
            val date = LocalDateTime.of(2021, 1, 1, 0, 0)
            val formattedDate = dateFormatter.format(date)
            assertEquals("01 Jan 2021", formattedDate)
        }
}