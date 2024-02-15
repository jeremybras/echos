package fr.test.echos.testutils

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Buffer
import okio.source

fun MockWebServer.enqueue(filename: String) {
    this.enqueueWithCode(filename, 200)
}

fun MockWebServer.enqueueWithCode(filename: String, code: Int) {
    this.enqueue(
        MockResponse().setBody(Buffer().apply {
            writeAll(
                MockWebServer::class.java.classLoader.getResourceAsStream(filename).source()

            )
        }).setResponseCode(code)
    )
}
