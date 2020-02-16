package io.github.cottonmc.functionapi.api

interface FunctionAPIIdentifier {
    val namespace: String
    val path: String
    fun asString(): String? {
        return "$namespace:$path"
    }

    companion object {
        fun isCharValid(char_1: Char): Boolean {
            return char_1 >= '0' && char_1 <= '9' || char_1 >= 'a' && char_1 <= 'z' || char_1 == '_' || char_1 == ':' || char_1 == '/' || char_1 == '.' || char_1 == '-'
        }

        fun isPathValid(string_1: String): Boolean {
            return string_1.chars().allMatch { int_1: Int -> int_1 == 95 || int_1 == 45 || int_1 >= 97 && int_1 <= 122 || int_1 >= 48 && int_1 <= 57 || int_1 == 47 || int_1 == 46 }
        }

        fun isNamespaceValid(string_1: String): Boolean {
            return string_1.chars().allMatch { int_1: Int -> int_1 == 95 || int_1 == 45 || int_1 >= 97 && int_1 <= 122 || int_1 >= 48 && int_1 <= 57 || int_1 == 46 }
        }
    }
}