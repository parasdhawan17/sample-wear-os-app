/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.myapplication

import androidx.core.content.ContextCompat
import androidx.wear.tiles.*
import androidx.wear.tiles.DimensionBuilders.*
import com.example.myapplication.datastore.DataStore
import com.example.myapplication.retrofit.CricketRepository
import com.example.myapplication.retrofit.RetrofitClient
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.guava.future

private const val RESOURCES_VERSION = "1"

class ScoreTileService : TileService(){

    private val serviceScope = CoroutineScope(Dispatchers.IO)
    private val repository = CricketRepository(RetrofitClient.getApiService())

    override fun onTileRequest(requestParams: RequestBuilders.TileRequest)= serviceScope.future {

        val matchId = DataStore(applicationContext).getInt(Constants.SHARED_PREF_KEY_MATCH_ID)
        val matchDetails = repository.getMatchDetailsById(matchId)
        val scoreCard = matchDetails.results.live_details.scorecard.last()
        // Creates Tile.
        TileBuilders.Tile.Builder()
            // If there are any graphics/images defined in the Tile's layout, the system will
            // retrieve them via onResourcesRequest() and match them with this version number.
            .setResourcesVersion(RESOURCES_VERSION)
            .setFreshnessIntervalMillis(10*1000)

            // Creates a timeline to hold one or more tile entries for a specific time periods.
            .setTimeline(
                TimelineBuilders.Timeline.Builder()
                    .addTimelineEntry(
                        TimelineBuilders.TimelineEntry.Builder()
                            .setLayout(
                                LayoutElementBuilders.Layout.Builder()
                                    .setRoot(
                                        LayoutElementBuilders.Text.Builder()
                                            .setText(
                                                "${scoreCard.runs}/${scoreCard.wickets} (${scoreCard.overs})"
                                            )
                                            .build()
                                    )
                                    .build()
                            )
                            .build()
                    )
                    .build()
            ).build()
    }

    override fun onResourcesRequest(requestParams: RequestBuilders.ResourcesRequest) = serviceScope.future {
        ResourceBuilders.Resources.Builder()
            .setVersion(RESOURCES_VERSION)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

}
