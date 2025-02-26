package test.Manager;

import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Status;
import task.Epic;
import task.Subtask;
import task.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    private static TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefault();
    }

    @Test
    public void getHistoryShouldReturnListOf10Tasks() {
        for (int i = 0; i < 20; i++) {
            taskManager.addTask(new Task("Some name", "Some description"));
        }

        List<Task> tasks = taskManager.getTasks();
        for (Task task : tasks) {
            taskManager.getTaskByID(task.getId());
        }

        List<Task> list = taskManager.getHistory();
        assertEquals(10, list.size(), "Неверное количество элементов в истории ");
    }

    @Test
    public void getHistoryShouldReturnOldTaskAfterUpdate() {
        Task readNietzsche = new Task("Прочитать книгу Ницше", "На немецком языке");
        taskManager.addTask(readNietzsche);
        taskManager.getTaskByID(readNietzsche.getId());
        taskManager.updateTask(new Task(readNietzsche.getId(), "Прочитать книгу Ницше",
                "Для начала на русском языке", Status.IN_PROGRESS));
        List<Task> tasks = taskManager.getHistory();
        Task oldTask = tasks.getFirst();
        assertEquals(readNietzsche.getName(), oldTask.getName(), "В истории не сохранилась старая версия задачи");
        assertEquals(readNietzsche.getDescription(), oldTask.getDescription(),
                "В истории не сохранилась старая версия задачи");
    }

    @Test
    public void getHistoryShouldReturnOldEpicAfterUpdate() {
        Epic yandexEducation = new Epic("Сдать все задания первого модуля", "Нужно успеть до понедельника");
        taskManager.addEpic(yandexEducation);
        taskManager.getEpicByID(yandexEducation.getId());
        taskManager.updateEpic(new Epic(yandexEducation.getId(), "Новое имя", "новое описание",
                Status.IN_PROGRESS));
        List<Task> epics = taskManager.getHistory();
        Epic oldEpic = (Epic) epics.getFirst();
        assertEquals(yandexEducation.getName(), oldEpic.getName(),
                "В истории не сохранилась старая версия эпика");
        assertEquals(yandexEducation.getDescription(), oldEpic.getDescription(),
                "В истории не сохранилась старая версия эпика");
    }

    @Test
    public void getHistoryShouldReturnOldSubtaskAfterUpdate() {
        Epic yandexEducation = new Epic("Сдать все задания первого модуля", "Нужно успеть до понедельника");
        taskManager.addEpic(yandexEducation);
        Subtask yandexEducationSubtask3 = new Subtask("Сдать 5й спринт", "До 3го марта",
                yandexEducation.getId());
        taskManager.addSubtask(yandexEducationSubtask3);
        taskManager.getSubtaskByID(yandexEducationSubtask3.getId());
        taskManager.updateSubtask(new Subtask(yandexEducationSubtask3.getId(), "Новое имя",
                "новое описание", Status.IN_PROGRESS, yandexEducation.getId()));
        List<Task> subtasks = taskManager.getHistory();
        Subtask oldSubtask = (Subtask) subtasks.getFirst();
        assertEquals(yandexEducationSubtask3.getName(), oldSubtask.getName(),
                "В истории не сохранилась старая версия подзадачи");
        assertEquals(yandexEducationSubtask3.getDescription(), oldSubtask.getDescription(),
                "В истории не сохранилась старая версия подзадачи");
    }
}