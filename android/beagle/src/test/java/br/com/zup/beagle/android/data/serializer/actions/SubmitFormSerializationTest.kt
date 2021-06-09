/*
 * Copyright 2020 ZUP IT SERVICOS EM TECNOLOGIA E INOVACAO SA
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
 */

package br.com.zup.beagle.android.data.serializer.actions

import br.com.zup.beagle.android.action.Action
import br.com.zup.beagle.android.action.SubmitForm
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Given a Moshi Adapter")
class SubmitFormSerializationTest : BaseActionSerializationTest() {
    @DisplayName("When try to deserialize json SubmitForm")
    @Nested
    inner class DeserializeJsonSubmitFormTest {

        @DisplayName("Then should return correct object")
        @Test
        fun testDeserializeJsonSubmitForm() {
            // Given
            val json = makeSubmitFormJson()

            // When
            val actual = moshi.adapter(Action::class.java).fromJson(json)

            // Then
            Assertions.assertNotNull(actual)
            Assertions.assertTrue(actual is SubmitForm)
        }

    }

    @DisplayName("When try serialize object SubmitForm")
    @Nested
    inner class SerializeObjectSubmitFormTest {

        @DisplayName("Then should return correct json")
        @Test
        fun testSerializeJsonSubmitForm() {
            // Given
            val expectedJson = makeSubmitFormJson().replace("\\s".toRegex(), "")
            val action = makeObjectSubmitForm()

            // When
            val actual = moshi.adapter(Action::class.java).toJson(action)
            val actualJson = actual.replace("\\s".toRegex(), "")

            // Then
            Assertions.assertNotNull(actual)
            Assertions.assertEquals(expectedJson, actualJson)
        }
    }

    private fun makeSubmitFormJson() = """
    {
        "_beagleAction_": "beagle:submitform"
    }
"""

    private fun makeObjectSubmitForm() = SubmitForm()
}