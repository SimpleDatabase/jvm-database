package de.plk.database.sql.command

/**
 * Represents the closure for command replacements.
 */
fun interface CommandClosure {

    /**
     * Appliement for the command closure.
     *
     * @return The command string replacements.
     */
    fun apply(): Array<String>

}