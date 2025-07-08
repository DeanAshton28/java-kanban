package manager;


import task.Epic;
import task.Subtask;
import task.Task;

import java.util.List;

public class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager() {
            @Override
            public int getNextID() {
                return 0;
            }

            @Override
            public Task updateTask(Task Task) {
                return null;
            }

            @Override
            public Epic updateEpic(Epic Epic) {
                return null;
            }

            @Override
            public Subtask updateSubtask(Subtask Subtask) {
                return null;
            }

            @Override
            public Task getTaskByID(int id) {
                return null;
            }

            @Override
            public Epic getEpicByID(int id) {
                return null;
            }

            @Override
            public Subtask getSubtaskByID(int id) {
                return null;
            }

            @Override
            public List<Task> getTasks() {
                return List.of();
            }

            @Override
            public List<Epic> getEpics() {
                return List.of();
            }

            @Override
            public List<Subtask> getSubtasks() {
                return List.of();
            }

            @Override
            public List<Subtask> getEpicSubtasks(Epic epic) {
                return List.of();
            }

            @Override
            public void deleteTasks() {

            }

            @Override
            public void deleteEpics() {

            }

            @Override
            public void deleteSubtasks() {

            }

            @Override
            public Task deleteTaskByID(int id) {
                return null;
            }

            @Override
            public Epic deleteEpicByID(int id) {
                return null;
            }

            @Override
            public Subtask deleteSubtaskByID(int id) {
                return null;
            }

            @Override
            public List<Task> getHistory() {
                return List.of();
            }
        };
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}