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

package br.com.zup.beagle.analytics2

import android.view.View
import br.com.zup.beagle.android.action.ActionAnalytics
import br.com.zup.beagle.android.widget.RootView

class AnalyticsService(private val analyticsProvider: AnalyticsProvider? = null) {

    private lateinit var analyticsConfig: AnalyticsConfig

    init {
        analyticsProvider?.let { analyticsProvider ->
            analyticsProvider.startSession {
                analyticsProvider.getConfig { analyticsConfig ->
                    this.analyticsConfig = analyticsConfig
                }
            }
        }
    }

    fun createActionRecord(
        rootView: RootView,
        origin: View,
        action: ActionAnalytics,
        analyticsHandleEvent: AnalyticsHandleEvent? = null
    ) {
        val config = createAConfigFromActionAnalyticsOrAnalyticsConfig(action)
        if (shouldReport(config)) {
            reportAction(rootView, origin, action, analyticsHandleEvent, config)
        }
    }

    private fun reportAction(
        rootView: RootView,
        origin: View,
        action: ActionAnalytics,
        analyticsHandleEvent: AnalyticsHandleEvent? = null,
        actionAnalyticsConfig: ActionAnalyticsConfig
    ) {
        try {
            analyticsProvider?.createRecord(
                ActionRecordCreator.createRecord(rootView, origin, actionAnalyticsConfig, action, analyticsHandleEvent)
            )
        } catch (e: Exception) {

        }
    }

    private fun shouldReport(actionAnalyticsConfig: ActionAnalyticsConfig) = actionAnalyticsConfig.enable

    private fun createAConfigFromActionAnalyticsOrAnalyticsConfig(action: ActionAnalytics): ActionAnalyticsConfig {
        action.analytics?.let { actionAnalytics ->
            return ActionAnalyticsConfig(
                enable = actionAnalytics.enable,
                attributes = actionAnalytics.attributes,
                additionalEntries = actionAnalytics.additionalEntries)
        }
        return actionAnalyticsFromConfig(action)
    }

    private fun actionAnalyticsFromConfig(action: ActionAnalytics): ActionAnalyticsConfig {
        val key = action.type
        val attributeList = analyticsConfig.actions[key]
        return ActionAnalyticsConfig(enable = attributeList != null, attributes = attributeList)
    }

    fun createScreenRecord(isLocalScreen: Boolean, screenIdentifier: String) {
        if (isAnalyticsConfigInitialized()) {
            reportScreen(isLocalScreen, screenIdentifier)
        }
    }

    private fun reportScreen(isLocalScreen: Boolean, screenIdentifier: String) {
        if (shouldReportScreen()) {
            if (isLocalScreen)
                analyticsProvider?.createRecord(ScreenReportCreator.createScreenLocalReport(screenIdentifier))
            else
                analyticsProvider?.createRecord(ScreenReportCreator.createScreenRemoteReport(screenIdentifier))
        }
    }

    private fun isAnalyticsConfigInitialized() = this::analyticsConfig.isInitialized

    private fun shouldReportScreen() = analyticsConfig.enableScreenAnalytics ?: false

}