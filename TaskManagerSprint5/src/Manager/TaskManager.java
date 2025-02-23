package Manager;

import Task.Task;
import Task.Epic;
import Task.Subtask;

import java.util.List;


public interface TaskManager {
    int getNextID();

    Task addTask(Task Task);

    Epic addEpic(Epic Epic);

    Subtask addSubtask(Subtask Subtask);

    Task updateTask(Task Task);

    Epic updateEpic(Epic Epic);

    Subtask updateSubtask(Subtask Subtask);

    Task getTaskByID(int id);

    Epic getEpicByID(int id);

    Subtask getSubtaskByID(int id);

    List<Task> getTasks();

    List<Epic> getEpics();

    List<Subtask> getSubtasks();

    List<Subtask> getEpicSubtasks(Epic epic);

    void deleteTasks();

    void deleteEpics();

    void deleteSubtasks();

    Task deleteTaskByID(int id);

    Epic deleteEpicByID(int id);

    Subtask deleteSubtaskByID(int id);

    List<Task> getHistory();
}