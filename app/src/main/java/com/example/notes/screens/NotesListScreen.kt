package com.example.notes.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notes.R
import com.example.notes.database.NoteEntity
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesListScreen(
    notes: List<NoteEntity>,
    toNote: (id: UUID) -> Unit,
    onCreateNote: () -> Unit,
    onDelete: (index: Int) -> Unit
) {

    Column {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xff1D1D1D)),
            title = {
                Text(text = "Заметки", color = Color.White)
            },
            actions = {
                IconButton(onClick = onCreateNote) {
                    Icon(painter = painterResource(id = R.drawable.edit_icon), contentDescription = "", tint = Color.White)
                }
            }
        )
        HorizontalDivider(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth(),
            color = Color(0xFF414143)
        )
        if(notes.isEmpty()) {
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(), contentAlignment = Alignment.Center) {
                Text("Нет созданных заметок\nна данный момент ", color = Color.White, textAlign = TextAlign.Center)
            }
        }
        else {
            NotesList(
                notes = notes,
                onDelete = onDelete,
                toNote = toNote
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesList(
    notes: List<NoteEntity>,
    onDelete: (index: Int) -> Unit,
    toNote: (id: UUID) -> Unit,
) {

    LazyColumn {
        itemsIndexed(items = notes) { index, note ->

            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = {
                    if(it == SwipeToDismissBoxValue.EndToStart) {
                        if(index < notes.size) {
                            onDelete(index)
                        }
                    }
                    if(it == SwipeToDismissBoxValue.StartToEnd) {

                    }
                    false
                }
            )

            SwipeToDismissBox(
                state = dismissState,
                enableDismissFromStartToEnd = false,
                backgroundContent = {
                    val direction = dismissState.dismissDirection

                    val color = animateColorAsState(
                        when (dismissState.targetValue) {
                            SwipeToDismissBoxValue.StartToEnd -> Color.Green
                            SwipeToDismissBoxValue.EndToStart -> Color.Red
                            else -> Color(0xFF1d1d1d)
                        }, label = ""
                    )

                    val alignment: Alignment.Horizontal = when (direction) {
                        SwipeToDismissBoxValue.StartToEnd -> Alignment.Start
                        SwipeToDismissBoxValue.EndToStart -> Alignment.End
                        else -> Alignment.CenterHorizontally
                    }

                    val icon = when (direction) {
                        SwipeToDismissBoxValue.StartToEnd -> Icons.Default.Done
                        SwipeToDismissBoxValue.EndToStart -> Icons.Default.Delete
                        else -> Icons.Default.Info
                    }

                    val scale = animateFloatAsState(
                        if (dismissState.targetValue == SwipeToDismissBoxValue.Settled) 0.5f else 1f,
                        label = ""
                    )

                    Box(modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .background(color.value, RectangleShape)
                                .padding(horizontal = 32.dp, vertical = 4.dp),
                            horizontalAlignment = alignment,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                icon,
                                contentDescription = "",
                                modifier = Modifier.scale(scale.value)
                                , tint = Color.White
                            )
                        }
                    }
                },
            ) {
                Box(modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 16.dp)
                    .fillMaxWidth()
                    .clickable { toNote(note.id) }) {
                    Column(modifier = Modifier
                        .background(color = Color(0xFF272728), shape = RoundedCornerShape(8.dp))
                        .fillMaxWidth()
                        .padding(8.dp)
                    ) {
                        Text(text = note.title, color = Color.White, maxLines = 1, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 2.dp),  overflow = TextOverflow.Ellipsis)
                        Text(text = note.content, color = Color.White, maxLines = 2, fontSize = 12.sp, lineHeight = 18.sp, overflow = TextOverflow.Ellipsis)
                    }
                }
            }
        }
    }
}
