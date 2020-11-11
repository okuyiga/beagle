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

package br.com.zup.beagle.sample.actions

import android.view.View
import br.com.zup.beagle.analytics2.ActionAnalyticsConfig
import br.com.zup.beagle.android.action.ActionAnalytics
import br.com.zup.beagle.android.widget.RootView
import br.com.zup.beagle.annotation.RegisterAction
import br.com.zup.beagle.core.ServerDrivenComponent

@RegisterAction
data class CustomAndroidAction(
    val value: String,
    val intValue: Int,
    override var analytics: ActionAnalyticsConfig?,
    override val type: String?
) : ActionAnalytics() {
    override fun execute(rootView: RootView, origin: View, originComponent : ServerDrivenComponent?) {

    }
}