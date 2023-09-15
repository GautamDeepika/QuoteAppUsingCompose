package com.example.appusingcompose.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.appusingcompose.model.Quote


@Composable
fun QuoteList(data:Array<Quote>, onClick:(quote:Quote)-> Unit) {

    LazyColumn(content = {
        items(data){
            QuoteListItem(quote = it, onClick)
        }
    })
}