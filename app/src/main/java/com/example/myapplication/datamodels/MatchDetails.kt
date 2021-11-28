package com.example.myapplication.datamodels

data class MatchDetails(
    val results: Results
) {
    data class Results(
        val fixture: Fixture,
        val live_details: LiveDetails
    ) {
        data class Fixture(
            val away: Away,
            val dates: List<Date>,
            val end_date: String,
            val home: Home,
            val id: Int,
            val match_title: String,
            val series: Series,
            val series_id: Int,
            val start_date: String,
            val venue: String
        ) {
            data class Away(
                val code: String,
                val id: Int,
                val name: String
            )

            data class Date(
                val date: String,
                val match_subtitle: String
            )

            data class Home(
                val code: String,
                val id: Int,
                val name: String
            )

            data class Series(
                val season: String,
                val series_id: Int,
                val series_name: String,
                val status: String,
                val updated_at: String
            )
        }

        data class LiveDetails(
            val match_summary: MatchSummary,
            val officials: Officials,
            val scorecard: List<Scorecard>,
            val stats: Stats,
            val teamsheets: Teamsheets
        ) {
            data class MatchSummary(
                val away_scores: String,
                val home_scores: String,
                val in_play: String,
                val result: String,
                val status: String,
                val toss: String
            )

            data class Officials(
                val referee: String,
                val umpire_1: String,
                val umpire_2: String,
                val umpire_reserve: String,
                val umpire_tv: String
            )

            data class Scorecard(
                val batting: List<Batting>,
                val bowling: List<Bowling>,
                val current: Boolean,
                val extras: Int,
                val extras_detail: String,
                val fow: String,
                val innings_number: Int,
                val overs: String,
                val runs: Int,
                val still_to_bat: List<StillToBat>,
                val title: String,
                val wickets: String
            ) {
                data class Batting(
                    val balls: Int,
                    val bat_order: Int,
                    val fours: Int,
                    val how_out: String,
                    val minutes: String,
                    val player_id: Int,
                    val player_name: String,
                    val runs: Int,
                    val sixes: Int,
                    val strike_rate: String
                )

                data class Bowling(
                    val dot_balls: Int,
                    val economy: String,
                    val extras: String,
                    val fours: Int,
                    val maidens: Int,
                    val overs: String,
                    val player_id: Int,
                    val player_name: String,
                    val runs_conceded: Int,
                    val sixes: Int,
                    val wickets: Int
                )

                data class StillToBat(
                    val player_id: Int,
                    val player_name: String
                )
            }

            data class Stats(
                val current_run_rate: String,
                val last_18_balls: List<Last18Ball>,
                val last_update: String,
                val min_remaining_overs: String,
                val partnership_overs: String,
                val partnership_player_1: String,
                val partnership_player_1_balls: Int,
                val partnership_player_1_runs: Int,
                val partnership_player_2: String,
                val partnership_player_2_balls: Int,
                val partnership_player_2_runs: Int,
                val partnership_run_rate: String,
                val partnership_runs: Int
            ) {
                data class Last18Ball(
                    val currentOver: Int,
                    val currentOverBall: Int,
                    val isBoundary: Boolean,
                    val isBye: Boolean,
                    val isDismissal: Boolean,
                    val isLegBye: Boolean,
                    val isMaiden: Boolean,
                    val isNoBall: Boolean,
                    val isOverEnd: Boolean,
                    val isWide: Boolean,
                    val runs: Int
                )
            }

            data class Teamsheets(
                val away: List<Away>,
                val home: List<Home>
            ) {
                data class Away(
                    val player_id: Int,
                    val player_name: String,
                    val position: String
                )

                data class Home(
                    val player_id: Int,
                    val player_name: String,
                    val position: String
                )
            }
        }
    }
}