package io.github.cottonmc.functionapi.util

import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier

class MissingResourceException(identifier: FunctionAPIIdentifier) : Exception("Resource with id " + identifier.asString() + " not found!")