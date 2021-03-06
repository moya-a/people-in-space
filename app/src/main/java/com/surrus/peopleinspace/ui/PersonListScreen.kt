package com.surrus.peopleinspace.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.surrus.common.remote.Assignment
import com.surrus.common.remote.IssPosition
import dev.chrisbanes.accompanist.coil.CoilImage
import org.koin.androidx.compose.getViewModel

@Composable
fun PersonListScreen(personSelected : (person : Assignment) -> Unit) {
    val peopleInSpaceViewModel = getViewModel<PeopleInSpaceViewModel>()
    val peopleState = peopleInSpaceViewModel.peopleInSpace.collectAsState()

    val issPosition = peopleInSpaceViewModel.issPosition.observeAsState(IssPosition(0.0, 0.0))

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("People In Space") })
        }) {
        Column {
            LazyColumn {
                items(peopleState.value) { person ->
                    val personImageUrl = peopleInSpaceViewModel.getPersonImage(person.name)
                    PersonView(personImageUrl, person, personSelected)
                }
            }
        }
    }
}


@Composable
fun PersonView(personImageUrl: String, person: Assignment, personSelected : (person : Assignment) -> Unit) {
    Row(
        modifier =  Modifier.fillMaxWidth().clickable(onClick = { personSelected(person) })
            .padding(16.dp), verticalAlignment = Alignment.CenterVertically
    ) {

        if (personImageUrl.isNotEmpty()) {
            CoilImage(data = personImageUrl, modifier = Modifier.size(60.dp), contentDescription = person.name)
        } else {
            Spacer(modifier = Modifier.size(60.dp))
        }

        Spacer(modifier = Modifier.size(12.dp))

        Column {
            Text(text = person.name, style = TextStyle(fontSize = 20.sp))
            Text(text = person.craft, style = TextStyle(color = Color.DarkGray, fontSize = 14.sp))
        }
    }
}

