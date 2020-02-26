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
import app.test.countries.R
import app.test.countries.repository.CountriesRepository
import app.test.countries.repository.SelectedCountryRepository
import app.test.countries.room.CountriesDatabase
import app.test.countries.schedulers.DefaultSchedulersProvider
import app.test.countries.view_models.CountryViewModel
import kotlinx.android.synthetic.main.country_fragment.*

class CountryFragment : Fragment() {

    private lateinit var viewModel: CountryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.country_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dao = CountriesDatabase.getInstance(context ?: return).selectedCountriesDao()
        viewModel.selectedCountryCode.observe(viewLifecycleOwner, Observer {
            progressBar.visibility = if (it == null) VISIBLE else GONE
        })
        viewModel.countryDetails.observe(viewLifecycleOwner, Observer {
            selectItemText.visibility = GONE
            detailsLayout.visibility = VISIBLE
            nameText.text = getString(R.string.name, it.name)
            nativeText.text = getString(R.string.native_text, it.native)
            flagText.text = getString(R.string.flag, it.emoji)
            currencyText.text = getString(R.string.currency, it.currency)

            val stringBuilder = StringBuilder()
            it.languages.forEachIndexed { index, language ->
                stringBuilder.append(
                    getString(
                        R.string.language_name_code,
                        language.name, language.code
                    )
                )
                if (index != it.languages.lastIndex)
                    stringBuilder.append(", ")
            }
            languagesText.text = getString(R.string.languages, stringBuilder.toString())
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            progressBar.visibility = if (it) VISIBLE else GONE
        })
        viewModel.start(
            SelectedCountryRepository(dao),
            CountriesRepository(),
            DefaultSchedulersProvider()
        )
    }
}
