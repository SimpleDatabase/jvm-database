package de.plk.database.meta

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Table(
    val tableName: String
)
