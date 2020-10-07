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

package br.com.zup.beagle.automatedtests.builders

import NAVIGATE_ACTIONS_ENDPOINT
import br.com.zup.beagle.widget.action.Navigate
import br.com.zup.beagle.widget.action.Route
import br.com.zup.beagle.widget.context.expressionOf
import br.com.zup.beagle.widget.layout.*
import br.com.zup.beagle.widget.ui.Button
import br.com.zup.beagle.widget.ui.Text

object GlobalSampleTextScreenBuilder {
    fun build() = Screen(
        child = Container(
            children =
            listOf(
                Text(
                    text = "Sample Screen"
                ),
                Text(
                    text = expressionOf("@{global}")
                ),
                Button(
                    onPress = listOf(
                        Navigate.PopToView(
                            NAVIGATE_ACTIONS_ENDPOINT
                        )
                    ),
                    text = "Click to PopToView $NAVIGATE_ACTIONS_ENDPOINT"
                )
            )
        )
    )
}
