package com.example.todolistmvvm.ui.theme.todo_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.todolistmvvm.data.Todo
import com.example.todolistmvvm.data.TodoRepository
import com.example.todolistmvvm.util.Routes
import com.example.todolistmvvm.util.UiEvent
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TodoListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: TodoListViewModel
    private val repository: TodoRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        coEvery { repository.getTodos() } returns flowOf(emptyList())
        viewModel = TodoListViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `OnTodoClick sends navigation event`() = runTest {
        val todo = Todo(
            id = 1,
            title = "Test",
            description = "Test description",
            isDone = false
        )
        val event = TodoListEvent.OnTodoClick(todo)

        viewModel.onEvent(event)

        val result = viewModel.uiEvent.first()
        assertEquals(
            UiEvent.Navigate(Routes.ADD_EDIT_TODO + "?todoId=${todo.id}"),
            result
        )
    }

    @Test
    fun `OnAddTodoClick sends navigation event`() = runTest {
        val event = TodoListEvent.OnAddTodoClick

        viewModel.onEvent(event)

        val result = viewModel.uiEvent.first()
        assertEquals(
            UiEvent.Navigate(Routes.ADD_EDIT_TODO),
            result
        )
    }
}
