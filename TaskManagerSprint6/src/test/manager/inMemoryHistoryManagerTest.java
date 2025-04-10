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

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryHistoryManagerTest {
    private final HistoryManager manager = new InMemoryHistoryManager();

    @Test
    void shouldAddTasksToHistory() {
        Task task1 = new Task(1, "Task 1", "Description", Status.NEW);
        Task task2 = new Task(2, "Task 2", "Description", Status.IN_PROGRESS);

        manager.add(task1);
        manager.add(task2);

        List<Task> history = manager.getHistory();
        assertEquals(2, history.size());
        assertEquals(task1, history.get(0));
        assertEquals(task2, history.get(1));
    }

    @Test
    void shouldRemoveDuplicatesWhenAddingSameTask() {
        Task task = new Task(1, "Task", "Description", Status.NEW);

        manager.add(task);
        manager.add(task);

        List<Task> history = manager.getHistory();
        assertEquals(1, history.size());
        assertEquals(task, history.get(0));
    }

    @Test
    void shouldRemoveTaskFromHistory() {
        Task task = new Task(1, "Task", "Description", Status.NEW);
        manager.add(task);

        manager.remove(1);

        assertTrue(manager.getHistory().isEmpty());
    }

    @Test
    void shouldMaintainInsertionOrderAfterRemoval() {
        Task t1 = new Task(1, "T1", "D", Status.NEW);
        Task t2 = new Task(2, "T2", "D", Status.NEW);
        Task t3 = new Task(3, "T3", "D", Status.NEW);

        manager.add(t1);
        manager.add(t2);
        manager.add(t3);
        manager.remove(2);

        List<Task> history = manager.getHistory();
        assertEquals(2, history.size());
        assertEquals(t1, history.get(0));
        assertEquals(t3, history.get(1));
    }

    @Test
    void shouldHandleEmptyHistory() {
        assertTrue(manager.getHistory().isEmpty());
        manager.remove(999);
        assertTrue(manager.getHistory().isEmpty());
    }
}    }