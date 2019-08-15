/*
 * Copyright (C) 2016-2019 HERE Europe B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 * License-Filename: LICENSE
 */

package com.here.genium.model.lime

/**
 * This class represents a top-level container which is either a class, an interface or a `types`
 * collection (as indicated by [type] field).
 */
class LimeContainer(
    path: LimePath,
    visibility: LimeVisibility = LimeVisibility.PUBLIC,
    comment: LimeComment = LimeComment(),
    attributes: LimeAttributes? = null,
    val type: ContainerType,
    val parent: LimeTypeRef? = null,
    val structs: List<LimeStruct> = emptyList(),
    val enumerations: List<LimeEnumeration> = emptyList(),
    val constants: List<LimeConstant> = emptyList(),
    val typeAliases: List<LimeTypeAlias> = emptyList(),
    val functions: List<LimeFunction> = emptyList(),
    val properties: List<LimeProperty> = emptyList(),
    val exceptions: List<LimeException> = emptyList()
) : LimeType(path, visibility, comment, attributes) {

    enum class ContainerType(private val tag: String) {
        TYPE_COLLECTION("types"),
        INTERFACE("interface"),
        CLASS("class");

        override fun toString() = tag
    }
}
