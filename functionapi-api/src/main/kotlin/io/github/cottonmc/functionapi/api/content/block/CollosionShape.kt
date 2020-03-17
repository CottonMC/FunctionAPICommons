package io.github.cottonmc.functionapi.api.content.block

import io.github.cottonmc.functionapi.api.content.Vector3f

interface CollosionShape {
   var parent:String
   var elements:List<ShapeElement>
}

interface ShapeElement{
    var from: Vector3f
    var to: Vector3f
}

