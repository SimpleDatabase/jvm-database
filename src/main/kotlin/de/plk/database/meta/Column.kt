package de.plk.database.meta

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Column(
    val primary: Boolean = false,
    val dataType: ColumnDataType,
    val columnName: String,
    val size: Int = 255
)
