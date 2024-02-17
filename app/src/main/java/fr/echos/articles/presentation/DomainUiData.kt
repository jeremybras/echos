package fr.echos.articles.presentation

data class DomainUiData(
    val name: String,
    val isSelected: Boolean = false,
    val displayMode: DomainDisplayMode = DomainDisplayMode.VERTICAL,
)

enum class DomainDisplayMode {
    VERTICAL,
    HORIZONTAL,
}

internal val fullDomainList = listOf(
    DomainUiData(
        name = "bbc.co.uk",
        isSelected = true,
    ),
    DomainUiData(
        name = "techcrunch.com",
        isSelected = true,
        displayMode = DomainDisplayMode.HORIZONTAL,
    ),
    DomainUiData(
        name = "engadget.com",
        displayMode = DomainDisplayMode.HORIZONTAL,
    ),
    DomainUiData(
        name = "mashable.com",
    ),
    DomainUiData(
        name = "thenextweb.com",
    ),
    DomainUiData(
        name = "wired.com",
    ),
    DomainUiData(
        name = "arstechnica.com",
        displayMode = DomainDisplayMode.HORIZONTAL,
    ),
    DomainUiData(
        name = "techradar.com",
    ),
    DomainUiData(
        name = "theverge.com",
    ),
    DomainUiData(
        name = "recode.net",
    ),
    DomainUiData(
        name = "venturebeat.com",
        displayMode = DomainDisplayMode.HORIZONTAL,
    ),
    DomainUiData(
        name = "cnet.com",
        displayMode = DomainDisplayMode.HORIZONTAL,
    ),
    DomainUiData(
        name = "gizmodo.com",
        displayMode = DomainDisplayMode.HORIZONTAL,
    ),
    DomainUiData(
        name = "slashdot.org",
    ),
    DomainUiData(
        name = "lifehacker.com",
    ),
    DomainUiData(
        name = "gigaom.com",
    ),
)