package test.manager;

import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryTaskManagerTest {
    private TaskManager manager;
    private Task task;
    private Epic epic;
    private Subtask subtask;

    @BeforeEach
    void setUp() {
        manager = Managers.getDefault();
        task = manager.addTask(new Task("Task", "Description"));
        epic = manager.addEpic(new Epic("Epic", "Description"));
        subtask = manager.addSubtask(new Subtask("Subtask", "Description", epic.getId()));
    }

    @Test
    void shouldCreateAndFindAllTaskTypes() {
        assertNotNull(manager.getTaskByID(task.getId()));
        assertNotNull(manager.getEpicByID(epic.getId()));
        assertNotNull(manager.getSubtaskByID(subtask.getId()));
    }

    @Test
    void shouldUpdateTaskStatus() {
        Task updatedTask = new Task(task.getId(), "Updated", "Desc", Status.DONE);
        manager.updateTask(updatedTask);

        assertEquals(Status.DONE, manager.getTaskByID(task.getId()).getStatus());
    }

    @Test
    void shouldUpdateEpicStatusBasedOnSubtasks() {
        Subtask newSubtask = manager.addSubtask(new Subtask("New", "Desc", epic.getId()));
        manager.updateSubtask(new Subtask(newSubtask.getId(), "New", "Desc", Status.DONE, epic.getId()));

        assertEquals(Status.IN_PROGRESS, manager.getEpicByID(epic.getId()).getStatus());
    }

    @Test
    void shouldDeleteTaskAndRemoveFromHistory() {
        manager.getTaskByID(task.getId());
        manager.deleteTaskByID(task.getId());

        assertNull(manager.getTaskByID(task.getId()));
        assertFalse(manager.getHistory().contains(task));
    }

    @Test
    void shouldDeleteEpicWithSubtasks() {
        manager.deleteEpicByID(epic.getId());

        assertNull(manager.getEpicByID(epic.getId()));
        assertTrue(manager.getSubtasks().isEmpty());
    }

    @Test
    void shouldReturnEmptyListsWhenNoTasks() {
        manager.deleteTasks();
        manager.deleteEpics();

        assertTrue(manager.getTasks().isEmpty());
        assertTrue(manager.getEpics().isEmpty());
        assertTrue(manager.getSubtasks().isEmpty());
    }

    @Test
    void shouldNotAllowSubtaskWithoutEpic() {
        Subtask orphanSubtask = new Subtask("Orphan", "Desc", 999);
        assertNull(manager.addSubtask(orphanSubtask));
    }