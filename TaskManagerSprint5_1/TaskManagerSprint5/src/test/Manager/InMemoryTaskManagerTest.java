package test.Manager;



import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Status;
import task.Task;
import task.Subtask;
import task.Epic;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    private static TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefault();
    }

    @Test
    void addNewTask() {

        final Task task = taskManager.addTask(new Task("Test addNewTask", "Test addNewTask description"));
        final Task savedTask = taskManager.getTaskByID(task.getId());
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getTasks();
        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.getFirst(), "Задачи не совпадают.");
    }

    @Test
    void addNewEpicAndSubtasks() {

        final Epic yandexEducation = taskManager.addEpic(new Epic("Сдать все задания первого модуля",
                "Нужно успеть до понедельника"));
        final Subtask yandexEducationSubtask1 = taskManager.addSubtask(new Subtask("Сдать 4й спринт",
                "Написать трекер задач", yandexEducation.getId()));
        final Subtask yandexEducationSubtask2 = taskManager.addSubtask(new Subtask("Изучить теорию 5го спринта",
                "Это было непросто, особенно полиморфизм", yandexEducation.getId()));
        final Subtask flatRenovationSubtask3 = taskManager.addSubtask(new Subtask("Сдать 5й спринт", "До 3го марта",
                yandexEducation.getId()));
        final Epic savedEpic = taskManager.getEpicByID(yandexEducation.getId());
        final Subtask savedSubtask1 = taskManager.getSubtaskByID(yandexEducationSubtask1.getId());
        final Subtask savedSubtask2 = taskManager.getSubtaskByID(yandexEducationSubtask2.getId());
        task.Subtask yandexEducationSubtask3;
        final Subtask savedSubtask3 = taskManager.getSubtaskByID(yandexEducationSubtask3.getId());
        assertNotNull(savedEpic, "Эпик не найден.");
        assertNotNull(savedSubtask2, "Подзадача не найдена.");
        assertEquals(yandexEducation, savedEpic, "Эпики не совпадают.");
        assertEquals(yandexEducationSubtask1, savedSubtask1, "Подзадачи не совпадают.");
        assertEquals(yandexEducationSubtask3, savedSubtask3, "Подзадачи не совпадают.");

        final List<Epic> epics = taskManager.getEpics();
        assertNotNull(epics, "Эпики не возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество эпиков.");
        assertEquals(flatRenovation, epics.getFirst(), "Эпики не совпадают.");

        final List<Subtask> subtasks = taskManager.getSubtasks();
        assertNotNull(subtasks, "Подзадачи не возвращаются.");
        assertEquals(3, subtasks.size(), "Неверное количество подзадач.");
        assertEquals(savedSubtask1, subtasks.getFirst(), "Подзадачи не совпадают.");
    }

    @Test
    public void updateTaskShouldReturnTaskWithTheSameId() {
        final Task expected = new Task("имя", "описание");
        taskManager.addTask(expected);
        final Task updatedTask = new Task(expected.getId(), "новое имя", "новое описание", Status.DONE);
        final Task actual = taskManager.updateTask(updatedTask);
        assertEquals(expected, actual, "Вернулась задачи с другим id");
    }

    @Test
    public void updateEpicShouldReturnEpicWithTheSameId() {
        final Epic expected = new Epic("имя", "описание");
        taskManager.addEpic(expected);
        final Epic updatedEpic = new Epic(expected.getId(), "новое имя", "новое описание", Status.DONE);
        final Epic actual = taskManager.updateEpic(updatedEpic);
        assertEquals(expected, actual, "Вернулся эпик с другим id");
    }

    @Test
    public void updateSubtaskShouldReturnSubtaskWithTheSameId() {
        final Epic epic = new Epic("имя", "описание");
        taskManager.addEpic(epic);
        final Subtask expected = new Subtask("имя", "описание", epic.getId());
        taskManager.addSubtask(expected);
        final Subtask updatedSubtask = new Subtask(expected.getId(), "новое имя", "новое описание",
                Status.DONE, epic.getId());
        final Subtask actual = taskManager.updateSubtask(updatedSubtask);
        assertEquals(expected, actual, "Вернулась подзадача с другим id");
    }

    @Test
    public void deleteTasksShouldReturnEmptyList() {
        taskManager.addTask(new Task("Сходить на тренировку", "Сделать 100 берпи"));
        taskManager.addTask(new Task("Прочитать книгу Ницше", "На немецком языке"));
        taskManager.deleteTasks();
        List<Task> tasks = taskManager.getTasks();
        assertTrue(tasks.isEmpty(), "После удаления задач список должен быть пуст.");
    }

    @Test
    public void deleteEpicsShouldReturnEmptyList() {
        taskManager.addEpic(new Epic("Сдать все задания первого модуля", "Нужно успеть до понедельника"));
        taskManager.deleteEpics();
        List<Epic> epics = taskManager.getEpics();
        assertTrue(epics.isEmpty(), "После удаления эпиков список должен быть пуст.");
    }

    @Test
    public void deleteSubtasksShouldReturnEmptyList() {
        Epic yandexEducation = new Epic("Сдать все задания первого модуля", "Нужно успеть до понедельника");
        taskManager.addEpic(yandexEducation);
        taskManager.addSubtask(new Subtask("Сдать 4й спринт", "Нужно успеть до вечера понедельника",
                yandexEducation.getId()));
        taskManager.addSubtask(new Subtask("Изучить теорию 5го спринта", "Это было непросто, особенно полиморфизм",
                yandexEducation.getId()));
        taskManager.addSubtask(new Subtask("Сдать 5й спринт", "До 3го марта",
                yandexEducation.getId()));

        taskManager.deleteSubtasks();
        List<Subtask> subtasks = taskManager.getSubtasks();
        assertTrue(subtasks.isEmpty(), "После удаления подзадач список должен быть пуст.");
    }

    @Test
    public void deleteTaskByIdShouldReturnNullIfKeyIsMissing() {
        taskManager.addTask(new Task(1, "Сходить на тренировку", "Сделать 100 берпи", Status.NEW));
        taskManager.addTask(new Task(2, "Прочитать книгу Ницше", "На немецком языке", Status.DONE));
        assertNull(taskManager.deleteTaskByID(3));
    }

    @Test
    public void deleteEpicByIdShouldReturnNullIfKeyIsMissing() {
        taskManager.addEpic(new Epic(1, "Сдать все задания первого модуля", "Нужно успеть до понедельника", Status.IN_PROGRESS));
        taskManager.deleteEpicByID(1);
        assertNull(taskManager.deleteTaskByID(1));
    }

    @Test
    public void deleteSubtaskByIdShouldReturnNullIfKeyIsMissing() {
        Epic flatRenovation = new Epic("Сдать все задания первого модуля", "Нужно успеть до понедельника");
        taskManager.addEpic(flatRenovation);
        taskManager.addSubtask(new Subtask("Сдать 4й спринт", "Написать трекер задач",
                flatRenovation.getId()));
        taskManager.addSubtask(new Subtask("Изучить теорию 5го спринта", "Это было непросто, особенно полиморфизм",
                flatRenovation.getId()));
        taskManager.addSubtask(new Subtask("Сдать5й спринт", "До 3го марта",
                flatRenovation.getId()));
        assertNull(taskManager.deleteSubtaskByID(5));
    }


    @Test
    void TaskCreatedAndTaskAddedShouldHaveSameVariables() {
        Task expected = new Task(1, "Прочитать книгу Ницше", "На немецком языке", Status.DONE);
        taskManager.addTask(expected);
        List<Task> list = taskManager.getTasks();
        Task actual = list.getFirst();
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getStatus(), actual.getStatus());
    }
}