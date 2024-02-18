package fr.echos.articles.presentation

data class DomainDisplayModel(
    val name: String,
    val isSelected: Boolean = false,
    val displayMode: DomainDisplayMode = DomainDisplayMode.VERTICAL,
)

enum class DomainDisplayMode {
    VERTICAL,
    HORIZONTAL,
}

internal val fullDomainList = listOf(
    DomainDisplayModel(
        name = "bbc.co.uk",
        isSelected = true,
    ),
    DomainDisplayModel(
        name = "techcrunch.com",
        isSelected = true,
        displayMode = DomainDisplayMode.HORIZONTAL,
    ),
    DomainDisplayModel(
        name = "engadget.com",
        displayMode = DomainDisplayMode.HORIZONTAL,
    ),
    DomainDisplayModel(
        name = "mashable.com",
    ),
    DomainDisplayModel(
        name = "thenextweb.com",
    ),
    DomainDisplayModel(
        name = "wired.com",
    ),
    DomainDisplayModel(
        name = "arstechnica.com",
        displayMode = DomainDisplayMode.HORIZONTAL,
    ),
    DomainDisplayModel(
        name = "techradar.com",
    ),
    DomainDisplayModel(
        name = "theverge.com",
    ),
    DomainDisplayModel(
        name = "recode.net",
    ),
    DomainDisplayModel(
        name = "venturebeat.com",
        displayMode = DomainDisplayMode.HORIZONTAL,
    ),
    DomainDisplayModel(
        name = "cnet.com",
        displayMode = DomainDisplayMode.HORIZONTAL,
    ),
    DomainDisplayModel(
        name = "gizmodo.com",
        displayMode = DomainDisplayMode.HORIZONTAL,
    ),
    DomainDisplayModel(
        name = "slashdot.org",
    ),
    DomainDisplayModel(
        name = "lifehacker.com",
    ),
    DomainDisplayModel(
        name = "gigaom.com",
    ),
)