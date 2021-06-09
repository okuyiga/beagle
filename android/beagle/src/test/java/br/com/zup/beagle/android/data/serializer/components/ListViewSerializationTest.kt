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

package br.com.zup.beagle.android.data.serializer.components

import br.com.zup.beagle.android.components.ListView
import br.com.zup.beagle.android.components.Text
import br.com.zup.beagle.android.components.layout.Container
import br.com.zup.beagle.android.components.utils.Template
import br.com.zup.beagle.android.context.expressionOf
import br.com.zup.beagle.core.ServerDrivenComponent
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Given a Moshi Adapter")
class ListViewSerializationTest : BaseComponentSerializationTest() {

    @DisplayName("When try to deserialize json ListView")
    @Nested
    inner class DeserializeJsonListViewTest {

        @DisplayName("Then should return correct object")
        @Test
        fun testDeserializeJsonListView() {
            // Given
            val expectedComponent = makeObjectListView()
            val json = makeListViewJson()

            // When
            val actual = moshi.adapter(ServerDrivenComponent::class.java).fromJson(json)

            // Then
            Assertions.assertNotNull(actual)
            Assertions.assertEquals(expectedComponent, actual)
        }
    }

    @DisplayName("When try serialize object ListView")
    @Nested
    inner class SerializeObjectListViewTest {

        @DisplayName("Then should return correct json")
        @Test
        fun testSerializeJsonListView() {
            // Given
            val expectedJson = makeListViewJson().replace("\\s".toRegex(), "")
            val component = makeObjectListView()

            // When
            val actual = moshi.adapter(ServerDrivenComponent::class.java).toJson(component)
            val actualJson = actual.replace("\\s".toRegex(), "")

            // Then
            Assertions.assertNotNull(actual)
            Assertions.assertEquals(expectedJson, actualJson)
        }
    }

    private fun makeListViewJson() = """
    {
       "_beagleComponent_":"beagle:listview",
       "direction":"VERTICAL",
       "dataSource":"@{characters}",
       "isScrollIndicatorVisible":false,
       "iteratorName":"item",
       "templates":[
          {
             "case":"@{eq(item.race,'Half-skaa')}",
             "view":{
                "_beagleComponent_":"beagle:container",
                "children":[
                   {
                      "_beagleComponent_":"beagle:text",
                      "text":"Name:@{item.name}"
                   }
                ]
             }
          }
       ]
    }
"""

    private fun makeObjectListView() = ListView(
        dataSource = expressionOf("@{characters}"),
        templates = listOf(
            Template(
                case = expressionOf("@{eq(item.race,'Half-skaa')}"),
                view = Container(
                    children = listOf(Text("Name:@{item.name}"))
                )
            )
        ),
        iteratorName = "item"
    )
}