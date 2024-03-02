package com.example.notes.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notes.R
import com.example.notes.database.NoteEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteInfoScreen(
    note: NoteEntity,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onBack: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xff1D1D1D)),
            navigationIcon = {
                IconButton(onClick = { onBack() }) {
                    Icon(painter = painterResource(id = R.drawable.back_icon), contentDescription = "", tint = Color.White)
                }
            },
            title = {
                Text(text = note.title, maxLines = 1, overflow = TextOverflow.Ellipsis, color = Color.White)
            },
            actions = {
                IconButton(onClick = onEdit) {
                    Icon(painter = painterResource(id = R.drawable.edit_icon), contentDescription = "", tint = Color.White)
                }
                IconButton(onClick = onDelete) {
                    Icon(painter = painterResource(id = R.drawable.delete_icon), contentDescription = "", tint = Color.White)
                }
            }
        )
        HorizontalDivider(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth(),
            color = Color(0xFF414143)
        )
        Column(modifier = Modifier
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp)) {
            BasicTextField(
                value = note.title,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .border(0.dp, Color.Transparent),
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 22.sp
                ),
            )
            BasicTextField(
                value = note.content,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
                    .border(0.dp, Color.Transparent),
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp
                ),
            )
        }
    }
}