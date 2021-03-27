open class LibraryDependency private constructor(private val notation: String) :
    CharSequence by notation {

    constructor(name: String, version: String) : this("$name:$version")

    override fun toString() = notation
}
