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

package br.com.zup.beagle.micronaut.filter

import br.com.zup.beagle.cache.BeagleCacheHandler
import br.com.zup.beagle.constants.BEAGLE_CACHE_ENABLED
import br.com.zup.beagle.constants.BEAGLE_CACHE_EXCLUDES
import br.com.zup.beagle.constants.BEAGLE_CACHE_INCLUDES
import br.com.zup.beagle.constants.BEAGLE_CACHE_TTL
import br.com.zup.beagle.micronaut.STRING
import br.com.zup.beagle.micronaut.configuration.BeagleMicronautCacheProperties
import br.com.zup.beagle.micronaut.containsBeans
import br.com.zup.beagle.micronaut.getProperty
import br.com.zup.beagle.platform.BeaglePlatformUtil
import io.micronaut.context.ApplicationContext
import io.micronaut.core.convert.value.MutableConvertibleValuesMap
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.filter.ServerFilterChain
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.junit.jupiter.api.Test
import java.time.Duration
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class BeagleCacheFilterTest {
    @Test
    fun `Test beagleCacheFilter is in context by default`() {
        assertTrue { ApplicationContext.run().containsBeans(BeagleCacheFilter::class) }
    }

    @Test
    fun `Test beagleCacheFilter is in context when enabled is true`() {
        assertTrue {
            ApplicationContext.run(mapOf(BEAGLE_CACHE_ENABLED to true)).containsBeans(BeagleCacheFilter::class)
        }
    }

    @Test
    fun `Test beagleCacheFilter is not in context when enabled is false`() {
        assertFalse {
            ApplicationContext.run(mapOf(BEAGLE_CACHE_ENABLED to false)).containsBeans(BeagleCacheFilter::class)
        }
    }

    @Test
    fun `Test beagleCacheFilter when properties are configured`() {
        ApplicationContext.run(
            mapOf(
                BEAGLE_CACHE_INCLUDES to STRING,
                BEAGLE_CACHE_EXCLUDES to STRING,
                "${BEAGLE_CACHE_TTL}.$STRING" to "5m"
            )
        ).getBean(BeagleCacheFilter::class.java).also { filter ->
            val properties = filter.getProperty("properties") as BeagleMicronautCacheProperties
            properties.includePatterns.forEach { assertEquals(STRING, it.pattern) }
            properties.excludePatterns.forEach { assertEquals(STRING, it.pattern) }
            assertEquals(mapOf(STRING to Duration.ofMinutes(5)), properties.ttlPattern)
        }
    }

    @Test
    fun `Test doFilter when all parameters are valid`() {
        val properties = mockk<BeagleMicronautCacheProperties>()
        val request = mockk<HttpRequest<*>>()
        val headers = mockk<HttpHeaders>()
        val response = HttpResponse.ok(Unit)
        val chain = mockk<ServerFilterChain>()

        every { properties.includePatterns } returns listOf(STRING.toRegex())
        every { properties.excludePatterns } returns emptyList()
        every { properties.ttlPattern } returns emptyMap()
        every { request.path } returns STRING
        every { request.attributes } returns MutableConvertibleValuesMap()
        every { request.headers } returns headers
        every { headers[any()] } returns STRING
        every { chain.proceed(any()) } returns Flowable.just(response)

        val result = BeagleCacheFilter(properties).doFilter(request, chain)

        assertNotNull(result)
        TestSubscriber<MutableHttpResponse<*>>().also {
            result.subscribe(it)
            it.assertComplete()
            it.assertValue(response)
        }
        verifyAll {
            request.path
            request.attributes
            request.headers
            headers[BeagleCacheHandler.CACHE_HEADER]
            headers[BeaglePlatformUtil.BEAGLE_PLATFORM_HEADER]
            chain.proceed(request)
        }
    }
}