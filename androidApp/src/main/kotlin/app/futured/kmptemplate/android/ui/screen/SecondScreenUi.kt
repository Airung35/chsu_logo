@file:OptIn(ExperimentalMaterial3Api::class)

package app.futured.kmptemplate.android.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.futured.kmptemplate.feature.ui.secondScreen.CardState
import app.futured.kmptemplate.feature.ui.secondScreen.MemoryCard
import app.futured.kmptemplate.feature.ui.secondScreen.SecondScreen
import app.futured.kmptemplate.feature.ui.secondScreen.SecondViewState
import app.futured.kmptemplate.resources.MR
import app.futured.kmptemplate.resources.kmpStringResource

@Composable
fun SecondScreenUi(
    screen: SecondScreen,
    modifier: Modifier = Modifier,
) {
    val viewState by screen.viewState.collectAsStateWithLifecycle()
    Content(viewState = viewState, actions = screen.actions, modifier = modifier)
}

@Composable
private fun Content(
    viewState: SecondViewState,
    actions: SecondScreen.Actions,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(kmpStringResource(res = MR.strings.second_screen_title)) },
                modifier = Modifier.fillMaxWidth(),
                windowInsets = WindowInsets.navigationBars,
                navigationIcon = {
                    IconButton(onClick = { actions.onBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 64.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(viewState.cards, key = { it.id }) { card ->
                    MemoryCardUi(card = card, onClick = { actions.onCardClick(card.id) })
                }
            }

            if (viewState.isGameFinished) {
                Text(
                    text = kmpStringResource(res = MR.strings.second_screen_victory_game, viewState.moves),
                    modifier = Modifier.align(Alignment.BottomCenter),
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
        }
    }
}

@Composable
private fun MemoryCardUi(card: MemoryCard, onClick: () -> Unit) {
    val backgroundColor = when (card.state) {
        CardState.CLOSED -> Color(0xFF616161)
        CardState.OPEN -> Color(card.color)
        CardState.MATCHED -> Color(card.color).copy(alpha = 0.4f) // угаданные приглушаем
    }
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick),
    )
}
