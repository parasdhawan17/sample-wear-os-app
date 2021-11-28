package com.example.myapplication.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import com.example.myapplication.Constants
import com.example.myapplication.R
import com.example.myapplication.datamodels.Fixtures
import com.example.myapplication.datastore.DataStore
import com.example.myapplication.retrofit.CricketRepository
import com.example.myapplication.retrofit.Result
import com.example.myapplication.retrofit.RetrofitClient
import com.example.myapplication.setCountryFlag
import com.example.myapplication.twoDigit
import com.example.myapplication.ui.fixtures.FixturesActivity
import com.example.myapplication.ui.fixtures.FixturesActivityViewModel
import com.example.myapplication.ui.fixtures.FixturesActivityViewModelFactory
import com.jwang123.flagkit.FlagKit

class MainActivity : ComponentActivity() {

    private lateinit var startForResult : ActivityResultLauncher<Intent>
    private lateinit var llMatchView : LinearLayout
    private lateinit var llSelectionView : LinearLayout
    private lateinit var tvScore : TextView
    private lateinit var tvInningTitle : TextView
    private lateinit var loading : ProgressBar
    private lateinit var rlTeams : RelativeLayout
    private lateinit var tvRefresh : TextView
    private lateinit var tvTitle : TextView
    private lateinit var tvSubTitle : TextView
    private lateinit var ivLeft : ImageView
    private lateinit var ivRight : ImageView

    private val repository = CricketRepository(RetrofitClient.getApiService())
    private val viewModel : MainActivityViewModel by viewModels { MainActivityViewModelFactory(repository,DataStore(applicationContext)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val btAdd = findViewById<AppCompatButton>(R.id.btAdd)
        llMatchView = findViewById(R.id.llMatchView)
        llSelectionView = findViewById(R.id.llSelectionView)
        tvScore = findViewById(R.id.tvScore)
        tvInningTitle = findViewById(R.id.tvInningTitle)
        loading = findViewById(R.id.loading)
        rlTeams = findViewById(R.id.rlTeams)
        tvRefresh = findViewById(R.id.tvRefresh)
        tvTitle = findViewById<TextView>(R.id.tvTitle)
        tvSubTitle = findViewById<TextView>(R.id.tvSubTitle)
        ivLeft = findViewById<ImageView>(R.id.ivLeft)
        ivRight = findViewById<ImageView>(R.id.ivRight)

        btAdd.setOnClickListener {
           startListActivity()
        }

        rlTeams.setOnClickListener {
            startListActivity()
        }

        tvRefresh.setOnClickListener {
            viewModel.refresh()
        }

        listenToMatchDetails()

        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let {
                    val id = it.getIntExtra(Constants.MATCH_ID,0)
                    val match = it.getSerializableExtra(Constants.MATCH) as Fixtures.Result
                    viewModel.setMatchId(id)
                }
            }
        }
    }

    private fun listenToMatchDetails(){
        viewModel.matchDetails.observe(this, Observer {

            when(it){
                is Result.Error -> {
                    llSelectionView.visibility = View.VISIBLE
                }
                is Result.Loading -> {

                    if(it.isLoading){
                        llSelectionView.visibility = View.GONE
                        loading.visibility = View.VISIBLE
                    }else{
                        loading.visibility = View.GONE
                    }
                }
                is Result.Success -> {

                    llMatchView.visibility = View.VISIBLE

                    tvTitle.text = it.data.results.fixture.match_title
                    tvSubTitle.text = it.data.results.fixture.venue

                    setCountryFlag(this,ivLeft,it.data.results.fixture.home.code)
                    setCountryFlag(this,ivRight,it.data.results.fixture.away.code)

                    when{
                        it.data.results.live_details==null ->{
                            tvScore.text = "Yet To Start"
                            tvInningTitle.visibility=View.GONE

                        }
                        it.data.results.live_details.match_summary.result == "Yes" ->{
                            tvScore.text = it.data.results.live_details.match_summary.status
                            tvInningTitle.visibility=View.GONE
                            tvSubTitle.text = it.data.results.live_details.match_summary.status
                        }
                        it.data.results.live_details.match_summary.result == "No" -> {
                            val scoreCard =it.data.results.live_details.scorecard.last()
                            tvScore.text = "${scoreCard.runs}/${scoreCard.wickets} (${scoreCard.overs})"
                            tvInningTitle.text = scoreCard.title
                            tvSubTitle.text = it.data.results.live_details.match_summary.status

                            tvScore.visibility = View.VISIBLE
                            tvInningTitle.visibility = View.VISIBLE
                        }
                    }
                }
            }
        })
    }

    private fun startListActivity(){
        startForResult.launch(Intent(this, FixturesActivity::class.java))
    }
}