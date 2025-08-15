package de.plk.database.model

/**
 * The operand for where condition.
 */
enum class Operand(

    /**
     * The operand to check in a sql condition.
     */
    val operand: String
) {
    EQUAL("="),
    NONEQUAL("!="),
    LIKE("LIKE"),
    GREATHER(">"),
    GREATHER_THAN(">="),
    SMALLER("<"),
    SMALLER_THAN("<=");
}