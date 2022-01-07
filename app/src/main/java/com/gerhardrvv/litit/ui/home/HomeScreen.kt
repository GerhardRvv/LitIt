package com.gerhardrvv.litit.ui.home

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material.TextFieldDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.gerhardrvv.litit.data.InputTypes.AVAILABLE_COLORS
import com.gerhardrvv.litit.data.InputTypes.AVAILABLE_LIGHT_BULBS
import com.gerhardrvv.litit.data.InputTypes.NUMBER_OF_RANDOM_LIGHT_BULBS
import com.gerhardrvv.litit.data.InputTypes.QTY_OF_EACH_LIGHT_BULB_COLOR
import com.gerhardrvv.litit.data.InputTypes.REPEATS
import com.gerhardrvv.litit.ui.theme.LitItTheme
import com.gerhardrvv.litit.ui.theme.Shapes
import com.gerhardrvv.litit.viewmodel.MainViewModel
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: MainViewModel
) {
    val scrollState = rememberScrollState(0)
    HomeScreen(
        viewModel = viewModel,
        onRunClick = { values: Map<String, Int> -> viewModel.generateLightBulbs(values) },
        scrollState = scrollState,
    )
}

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    onRunClick: (Map<String, Int>) -> Unit = {},
    scrollState: ScrollState = ScrollState(0)
) {
    val focusManager = LocalFocusManager.current

    val averageResult by rememberSaveable { mutableStateOf(viewModel.averageResult) }

    var numberOfLbAvailable by rememberSaveable { mutableStateOf("") }
    var numberOfLbColors by rememberSaveable { mutableStateOf("") }
    var qtyOfEachLbColor by rememberSaveable { mutableStateOf("") }
    var numberOfRandomLb by rememberSaveable { mutableStateOf("") }
    var numberOfRepeats by rememberSaveable { mutableStateOf("") }

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        backgroundColor = LitItTheme.colors.background,
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxHeight(),
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = LitItTheme.colors.onBackground,
                contentColor = LitItTheme.colors.primary,
                elevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "Lit It !!",
                        style = MaterialTheme.typography.h5,
                        color = LitItTheme.colors.primary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .semantics { contentDescription = "Home Screen" }
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxHeight()
        ) {
            Surface(
                color = LitItTheme.colors.background,
                contentColor = LitItTheme.colors.primary,
                modifier = Modifier.fillMaxHeight()
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp)
                ) {
                    val (
                        numberOfLbAvailableInput,
                        numberOfLbColorsInput,
                        qtyOfEachLbColorInput,
                        numberOfLightBulbs,
                        numberOfRandomLbInput,
                        numberOfRepeatsInput,
                        resultText,
                        runButton
                    ) = createRefs()

                    OutlinedTextField(
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = LitItTheme.colors.primary,
                            unfocusedBorderColor = LitItTheme.colors.primary,
                            cursorColor = LitItTheme.colors.primary
                        ),
                        value = numberOfLbAvailable,
                        onValueChange = { numberOfLbAvailable = it },
                        label = {
                            Text(
                                text = "# of Light Bulbs Available",
                                style = TextStyle(
                                    color = LitItTheme.colors.primary,
                                )
                            )
                        },
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .constrainAs(numberOfLbAvailableInput) {
                                linkTo(
                                    top = parent.top,
                                    topMargin = 16.dp,
                                    bottom = numberOfLbColorsInput.top,
                                    bottomMargin = 6.dp
                                )
                                start.linkTo(parent.start)
                            }
                            .semantics { contentDescription = "Number of Light Bulbs" },
                        maxLines = 1,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            autoCorrect = true,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
                        })
                    )
                    OutlinedTextField(
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = LitItTheme.colors.primary,
                            unfocusedBorderColor = LitItTheme.colors.primary,
                            cursorColor = LitItTheme.colors.primary
                        ),
                        value = numberOfLbColors,
                        onValueChange = { numberOfLbColors = it },
                        label = {
                            Text(
                                text = "# of Light Bulb Colors Available",
                                style = TextStyle(
                                    color = LitItTheme.colors.primary,
                                )
                            )
                        },
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
                            autoCorrect = true,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
                        })
                    )
                    OutlinedTextField(
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = LitItTheme.colors.primary,
                            unfocusedBorderColor = LitItTheme.colors.primary,
                            cursorColor = LitItTheme.colors.primary
                        ),
                        value = qtyOfEachLbColor,
                        onValueChange = { qtyOfEachLbColor = it },
                        label = {
                            Text(
                                text = "# of Light Bulb of Each Colors",
                                style = TextStyle(
                                    color = LitItTheme.colors.primary,
                                )
                            )
                        },
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
                            autoCorrect = true,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
                        })
                    )
                    OutlinedTextField(
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = LitItTheme.colors.primary,
                            unfocusedBorderColor = LitItTheme.colors.primary,
                            cursorColor = LitItTheme.colors.primary
                        ),
                        value = numberOfRandomLb,
                        onValueChange = { numberOfRandomLb = it },
                        label = {
                            Text(
                                text = "# of Random Light Bulbs",
                                style = TextStyle(
                                    color = LitItTheme.colors.primary,
                                )
                            )
                        },
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
                            autoCorrect = true,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
                        })
                    )
                    OutlinedTextField(
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = LitItTheme.colors.primary,
                            unfocusedBorderColor = LitItTheme.colors.primary,
                            cursorColor = LitItTheme.colors.primary
                        ),
                        value = numberOfRepeats,
                        onValueChange = { numberOfRepeats = it },
                        label = {
                            Text(
                                text = "# of Repeats",
                                style = TextStyle(
                                    color = LitItTheme.colors.primary,
                                )
                            )
                        },
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
                            .fillMaxWidth()
                            .background(color = LitItTheme.colors.onBackground, shape = Shapes.small)
                            .constrainAs(resultText) {
                                top.linkTo(numberOfRepeatsInput.bottom)
                                start.linkTo(numberOfRepeatsInput.start)
                                end.linkTo(numberOfRepeatsInput.end)
                            }
                            .border(2.dp, LitItTheme.colors.primary, shape = Shapes.small)
                            .semantics { contentDescription = " of Repeats" }
                    ) {
                        Text(
                            "Average Result", textAlign = TextAlign.Start,
                            modifier = Modifier
                                .width(150.dp)
                                .padding(10.dp)
                        )
                        Text(
                            averageResult.value.toString(), textAlign = TextAlign.End,
                            modifier = Modifier
                                .width(150.dp)
                                .padding(10.dp)
                        )
                    }
                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .padding(24.dp)
                            .constrainAs(runButton) {
                                top.linkTo(resultText.bottom)
                                end.linkTo(parent.end)
                            },
                        backgroundColor = LitItTheme.colors.secondary,
                        contentColor = LitItTheme.colors.primary,
                        onClick = {
                            if (numberOfLbAvailable.isNotEmpty() &&
                                numberOfLbColors.isNotEmpty() &&
                                qtyOfEachLbColor.isNotEmpty() &&
                                numberOfRandomLb.isNotEmpty() &&
                                numberOfRepeats.isNotEmpty()
                            ) {
                                if (numberOfLbAvailable.toInt() >= numberOfRandomLb.toInt()) {
                                    if (numberOfLbAvailable.toInt() < numberOfLbColors.toInt() * qtyOfEachLbColor.toInt()) {
                                        onRunClick(
                                            mapOf(
                                                AVAILABLE_LIGHT_BULBS.value to numberOfLbAvailable.toInt(),
                                                AVAILABLE_COLORS.value to numberOfLbColors.toInt(),
                                                QTY_OF_EACH_LIGHT_BULB_COLOR.value to qtyOfEachLbColor.toInt(),
                                                NUMBER_OF_RANDOM_LIGHT_BULBS.value to numberOfRandomLb.toInt(),
                                                REPEATS.value to numberOfRepeats.toInt()
                                            )
                                        )
                                    } else {
                                        coroutineScope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar("Number of Available Bulbs can' be greater than number of colors * qty")
                                        }
                                    }
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
