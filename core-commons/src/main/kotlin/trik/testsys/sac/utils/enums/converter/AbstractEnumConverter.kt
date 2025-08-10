package trik.testsys.sac.utils.enums.converter

import jakarta.persistence.AttributeConverter
import trik.testsys.sac.utils.enums.Enum
import java.lang.IllegalStateException
import java.lang.reflect.ParameterizedType

/**
 * Generic JPA AttributeConverter that maps enums implementing [Enum]
 * to their dbkey representation and back.
 *
 * @author Roman Shishkin
 * @since 1.1.0
 */
abstract class AbstractEnumConverter<E> : AttributeConverter<E, String>
        where E : kotlin.Enum<E>, E : Enum {

    /**
     * Resolved enum class for the concrete converter implementation.
     *
     * @author Roman Shishkin
     * @since 1.1.0
     */
    @Suppress("UNCHECKED_CAST")
    private val enumClass: Class<E> by lazy {
        val superType = javaClass.genericSuperclass
        val type = (superType as ParameterizedType).actualTypeArguments[0]
        when (type) {
            is Class<*> -> type as Class<E>
            is ParameterizedType -> type.rawType as Class<E>
            else -> throw IllegalStateException("Cannot determine enum type for converter ${javaClass.name}")
        }
    }

    override fun convertToDatabaseColumn(attribute: E?): String? = attribute?.dbkey

    override fun convertToEntityAttribute(dbData: String?): E? {
        if (dbData == null) return null
        val all = enumClass.enumConstants ?: emptyArray()
        return all.firstOrNull { it.dbkey == dbData }
            ?: throw IllegalArgumentException("Unknown dbkey '$dbData' for enum ${enumClass.name}")
    }
}


