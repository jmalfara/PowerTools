package com.jmat.conversions.ui.fragment

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jmat.conversions.R
import com.jmat.conversions.databinding.FragmentConversionL100kmToMpgBinding
import com.jmat.conversions.di.ConversionsComponent
import com.jmat.conversions.di.ConversionsInjectionInitializer
import com.jmat.conversions.di.InjectionInitializer
import com.jmat.conversions.ui.model.ConversionEvent
import com.jmat.conversions.ui.viewmodel.ConversionFavouritesViewModel
import com.jmat.conversions.ui.viewmodel.ConversionFavouritesViewModelFactory
import com.jmat.conversions.ui.viewmodel.ConversionL100KmToMPGViewModel
import com.jmat.powertools.base.textwatchers.NumberFormattingTextWatcher
import com.jmat.powertools.base.delegate.viewBinding
import com.jmat.powertools.base.extensions.NavigationMode
import com.jmat.powertools.base.extensions.addFocusedOnTextChangeListener
import com.jmat.powertools.base.extensions.setupToolbar
import com.jmat.powertools.base.extensions.showEndIconOnFocus
import com.jmat.powertools.data.preferences.UserPreferencesRepository
import com.jmat.powertools.modules.conversions.DEEPLINK_CONVERSIONS_L100KM_TO_MPG
import com.jmat.powertools.modules.conversions.ID_CONVERSIONS_L100KM_TO_MPG
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

class ConversionLiters100KmToMPGFragment : Fragment(R.layout.fragment_conversion_l100km_to_mpg),
    InjectionInitializer<ConversionsComponent> by ConversionsInjectionInitializer() {

    private val binding: FragmentConversionL100kmToMpgBinding by viewBinding(
        FragmentConversionL100kmToMpgBinding::bind
    )

    @Inject
    lateinit var userPreferencesRepository: UserPreferencesRepository

    private val favouriteViewModel: ConversionFavouritesViewModel by viewModels {
        ConversionFavouritesViewModelFactory(
            userPreferencesRepository = userPreferencesRepository,
            favouriteId = ID_CONVERSIONS_L100KM_TO_MPG,
            favouriteActionLink = DEEPLINK_CONVERSIONS_L100KM_TO_MPG
        )
    }

    private val viewModel: ConversionL100KmToMPGViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        buildComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(
            toolbar = binding.toolbar,
            navigationMode = NavigationMode.CLOSE
        ).apply {
            setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.favourite) {
                    favouriteViewModel.toggleFavourite()
                    true
                } else false
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    favouriteViewModel.isFavourite.collect { isFavourite ->
                        val color = if (isFavourite) {
                            ResourcesCompat.getColor(resources, com.jmat.powertools.R.color.favourite, null)
                        } else {
                            val typedValue = TypedValue()
                            val theme = requireContext().theme
                            theme.resolveAttribute(
                                com.google.android.material.R.attr.colorOnPrimary,
                                typedValue,
                                true
                            )
                            typedValue.data
                        }

                        binding.toolbar.menu.findItem(R.id.favourite).icon.setTint(color)
                    }
                }

                launch {
                    viewModel.events.collect { event ->
                        when (event) {
                            is ConversionEvent.UpdateToAmount -> {
                                binding.toAmount.editText?.setText(
                                    event.amount.toPlainString(),
                                    TextView.BufferType.EDITABLE
                                )
                            }
                            is ConversionEvent.UpdateFromAmount -> {
                                binding.fromAmount.editText?.setText(
                                    event.amount.toPlainString(),
                                    TextView.BufferType.EDITABLE
                                )
                            }
                        }
                    }
                }
            }
        }

        with(binding) {
            fromAmount.showEndIconOnFocus()
            fromAmount.editText?.addTextChangedListener(NumberFormattingTextWatcher())
            fromAmount.editText?.addFocusedOnTextChangeListener { s ->
                val amount = s.toString().takeIf { it.isEmpty().not() }?.toBigDecimal() ?: BigDecimal.ZERO
                viewModel.calculateMpg(amount)
            }

            toAmount.showEndIconOnFocus()
            toAmount.editText?.addTextChangedListener(NumberFormattingTextWatcher())
            toAmount.editText?.addFocusedOnTextChangeListener { s ->
                val amount = s.toString().takeIf { it.isEmpty().not() }?.toBigDecimal() ?: BigDecimal.ZERO
                viewModel.calculateL100Km(amount)
            }

            useUsGallonSwitch.setOnCheckedChangeListener { _, isChecked ->
                viewModel.setUseUsGallon(isChecked)
            }
        }
    }
}