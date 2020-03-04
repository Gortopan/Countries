package app.test.countries.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.test.countries.CountriesAdapter
import app.test.countries.ICountrySelectedListener
import app.test.countries.R
import app.test.countries.api.models.Countries
import app.test.countries.repository.CountriesRepository
import app.test.countries.repository.SelectedCountryRepository
import app.test.countries.room.CountriesDatabase
import app.test.countries.schedulers.DefaultSchedulersProvider
import app.test.countries.view_models.CountriesListViewModel
import kotlinx.android.synthetic.main.countries_list_fragment.*

class CountriesListFragment : Fragment(), ICountrySelectedListener {

    private lateinit var viewModel: CountriesListViewModel
    private lateinit var adapter: CountriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)
            .get(CountriesListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.countries_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dao = CountriesDatabase.getInstance(context ?: return).selectedCountriesDao()
        viewModel.countries.observe(viewLifecycleOwner, Observer {
            adapter.countries = it
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            progressBar.visibility = if (it) VISIBLE else GONE
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
        viewModel.start(
            SelectedCountryRepository(dao),
            CountriesRepository(),
            DefaultSchedulersProvider()
        )

        setupRecycler()
    }

    private fun setupRecycler() {
        adapter = CountriesAdapter(context ?: return, this)
        countriesRecycler.layoutManager = LinearLayoutManager(context)
        countriesRecycler.adapter = adapter
    }

    override fun onCountrySelected(countries: Countries) {
        viewModel.setCountrySelected(countries, DefaultSchedulersProvider())
    }
}
