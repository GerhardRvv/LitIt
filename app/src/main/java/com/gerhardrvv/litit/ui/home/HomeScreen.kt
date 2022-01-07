package com.gerhardrvv.litit.ui.home

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.gerhardrvv.litit.ui.theme.LitItTheme
import com.gerhardrvv.litit.viewmodel.InputTypes.AVAILABLE_COLORS
import com.gerhardrvv.litit.viewmodel.InputTypes.AVAILABLE_LIGHT_BULBS
import com.gerhardrvv.litit.viewmodel.InputTypes.NUMBER_OF_RANDOM_LIGHT_BULBS
import com.gerhardrvv.litit.viewmodel.InputTypes.QTY_OF_EACH_LIGHT_BULB_COLOR
import com.gerhardrvv.litit.viewmodel.MainViewModel
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: MainViewModel
) {

    val scrollState = rememberScrollState(0)

    HomeScreen(
        onRunClick = { values: Map<String, Int> -> viewModel.generateLightBulbs(values) },
        scrollState = scrollState,
    )
}

@Composable
fun HomeScreen(
    onRunClick: (Map<String, Int>) -> Unit = {},
    scrollState: ScrollState,
) {
    val focusManager = LocalFocusManager.current

    var numberOfLbAvailable by rememberSaveable { mutableStateOf("") }
    var numberOfLbColors by rememberSaveable { mutableStateOf("") }
    var qtyOfEachLbColor by rememberSaveable { mutableStateOf("") }
    var numberOfRandomLb by rememberSaveable { mutableStateOf("") }
    var numberOfRepeats by rememberSaveable { mutableStateOf("") }

    val scaffoldState = rememberScaffoldState() // this contains the `SnackbarHostState`
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = LitItTheme.colors.background,
                contentColor = LitItTheme.colors.primary,
                elevation = 4.dp
            ) {
                Text(
                    text = "Lit It !!",
                    style = MaterialTheme.typography.subtitle1,
                    color = LitItTheme.colors.primary,
                    modifier = Modifier
                        .align(CenterVertically)
                        .semantics { contentDescription = "Home Screen" }
                )
            }
        }
    ) {
        Column(modifier = Modifier.padding(8.dp).verticalScroll(scrollState)) {
            Surface(
                shape = MaterialTheme.shapes.small,
                color = LitItTheme.colors.background,
                elevation = 4.dp
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp)
                ) {
                    var (
                        numberOfLbAvailableInput,
                        numberOfLbColorsInput,
                        qtyOfEachLbColorInput,
                        numberOfLightBulbs,
                        numberOfRandomLbInput,
                        numberOfRepeatsInput,
                        run
                    ) = createRefs()

                    OutlinedTextField(
                        value = numberOfLbAvailable,
                        onValueChange = { numberOfLbAvailable = it },
                        label = { Text(text = "# of Light Bulbs") },
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .constrainAs(numberOfLbAvailableInput) {
                                linkTo(
                                    top = parent.top,
                                    topMargin = 16.dp,
                                    bottom = numberOfLbColorsInput.top,
                                    bottomMargin = 16.dp
                                )
                                start.linkTo(parent.start)
                            }
                            .semantics { contentDescription = "Number of Light Bulbs" },
                        maxLines = 1,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            capitalization = KeyboardCapitalization.Sentences,
                            autoCorrect = true,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
                        })
                    )
                    OutlinedTextField(
                        value = numberOfLbColors,
                        onValueChange = { numberOfLbColors = it },
                        label = { Text(text = "# Light Bulb Colors") },
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .constrainAs(numberOfLbColorsInput) {
                                linkTo(
                                    top = numberOfLbAvailableInput.bottom,
                                    topMargin = 16.dp,
                                    bottom = qtyOfEachLbColorInput.top,
                                    bottomMargin = 16.dp
                                )
                                start.linkTo(parent.start)
                            }
                            .semantics { contentDescription = "# Light Bulb Colors" },
                        maxLines = 1,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            capitalization = KeyboardCapitalization.Sentences,
                            autoCorrect = true,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
                        })
                    )
                    OutlinedTextField(
                        value = qtyOfEachLbColor,
                        onValueChange = { qtyOfEachLbColor = it },
                        label = { Text(text = "# Light Bulb of Each Colors") },
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .constrainAs(qtyOfEachLbColorInput) {
                                linkTo(
                                    top = numberOfLbColorsInput.bottom,
                                    topMargin = 16.dp,
                                    bottom = numberOfLightBulbs.top,
                                    bottomMargin = 16.dp
                                )
                                start.linkTo(parent.start)
                            }
                            .semantics { contentDescription = "# Light Bulb of Each Colors" },
                        maxLines = 1,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            capitalization = KeyboardCapitalization.Sentences,
                            autoCorrect = true,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
                        })
                    )
                    OutlinedTextField(
                        value = numberOfRandomLb,
                        onValueChange = { numberOfRandomLb = it },
                        label = { Text(text = "Pick random # of Lb") },
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .constrainAs(numberOfRandomLbInput) {
                                linkTo(
                                    top = qtyOfEachLbColorInput.bottom,
                                    topMargin = 16.dp,
                                    bottom = numberOfRepeatsInput.top,
                                    bottomMargin = 16.dp
                                )
                                start.linkTo(parent.start)
                            }
                            .semantics { contentDescription = "Pick random # of Lb" },
                        maxLines = 1,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            capitalization = KeyboardCapitalization.Sentences,
                            autoCorrect = true,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
                        })
                    )
                    OutlinedTextField(
                        value = numberOfRepeats,
                        onValueChange = { numberOfRepeats = it },
                        label = { Text(text = "# of Repeats") },
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 16.dp)
                            .constrainAs(numberOfRepeatsInput) {
                                top.linkTo(numberOfRandomLbInput.bottom)
                                start.linkTo(parent.start)
                            }
                            .semantics { contentDescription = " of Repeats" },
                        maxLines = 1,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            capitalization = KeyboardCapitalization.Sentences,
                            autoCorrect = true,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
                        })
                    )
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 16.dp)
                            .constrainAs(run) {
                                top.linkTo(numberOfRepeatsInput.bottom)
                                end.linkTo(parent.end)
                            }
                            .semantics { contentDescription = " of Repeats" }
                    ) {
                        Text(
                            "Hello World", textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(150.dp)
                                .padding(2.dp)
                        )

                        ExtendedFloatingActionButton(
                            onClick = {
                                if (numberOfLbAvailable.isNotEmpty() &&
                                    numberOfLbColors.isNotEmpty() &&
                                    qtyOfEachLbColor.isNotEmpty() &&
                                    numberOfRandomLb.isNotEmpty() &&
                                    numberOfRepeats.isNotEmpty()
                                ) {
                                    if (numberOfLbAvailable.toInt() > numberOfRandomLb.toInt()) {
                                        onRunClick(
                                            mapOf(
                                                AVAILABLE_LIGHT_BULBS.value to numberOfLbAvailable.toInt(),
                                                AVAILABLE_COLORS.value to numberOfLbColors.toInt(),
                                                QTY_OF_EACH_LIGHT_BULB_COLOR.value to qtyOfEachLbColor.toInt(),
                                                NUMBER_OF_RANDOM_LIGHT_BULBS.value to numberOfRandomLb.toInt(),
                                                NUMBER_OF_RANDOM_LIGHT_BULBS.value to numberOfRepeats.toInt()
                                            )
                                        )
                                    } else {
                                        coroutineScope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar("Random # of Light Bulbs Can't be grater that the # of light Bulbs available")
                                        }
                                    }
                                } else {
                                    coroutineScope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar("Ops.. You forgot some information.")
                                    }
                                }
                            },
                            icon = {
                                Icon(
                                    Icons.Filled.Build,
                                    contentDescription = "Run"
                                )
                            },
                            text = { Text("Run") },
                        )
                    }
                }
            }
        }
    }
}
