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

package com.here.gluecodium

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import java.io.File

@RunWith(Parameterized::class)
class NamespaceSmokeTest(
    featureDirectory: File,
    generatorName: String,
    @Suppress("UNUSED_PARAMETER") featureName: String
) : AcceptanceTestBase(featureDirectory, generatorName) {

    override fun getGluecodiumOptions() = Gluecodium.Options(
        cppInternalNamespace = Gluecodium.DEFAULT_INTERNAL_NAMESPACE,
        cppRootNamespace = listOf("root", "space"),
        javaInternalPackages = listOf("foo", "bar")
    )

    @Test
    fun smokeTest() {
        runTest()
    }

    companion object {
        private const val RESOURCE_PREFIX = "namespace_smoke"

        @JvmStatic
        @Parameters(name = "{2}, {1}")
        fun data(): Collection<Array<Any>> {
            return getData(RESOURCE_PREFIX)
        }
    }
}
