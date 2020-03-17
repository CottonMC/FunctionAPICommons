package io.github.cottonmc.functionapi.util.impl

import io.github.cottonmc.functionapi.api.content.block.CollosionShape
import io.github.cottonmc.functionapi.api.content.block.ShapeElement
import io.github.cottonmc.functionapi.api.content.Vector3f

class CollosionShapeImpl(
        override var parent: String = "",
        override var elements: List<ShapeElement> = emptyList()
): CollosionShape {
}

class ShapeElementImpl(override var from: Vector3f = Vector3fImpl(),
                       override var to: Vector3f = Vector3fImpl()
) : ShapeElement {

}