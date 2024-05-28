package com.example.todolistmvvm.ui.theme.todo_list

import androidx.lifecycle.ViewModel
import com.example.todolistmvvm.data.TodoRepository
import com.example.todolistmvvm.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository
): ViewModel() {
    val todos = repository.getTodos()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
}