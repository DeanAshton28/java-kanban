package manager;

import task.Task;
import task.Epic;
import task.Subtask;
import task.Status;

import java.util.*;

public class TaskManager {

    private static final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private int nextID = 1;

    public static void deleteTaskByID(int id) {

            tasks.remove(id);
        }

        public void deleteEpicByID(int id) {
            Epic epic = epics.remove(id);
            for (Subtask subtask : epic.getSubtaskList()) {
                subtasks.remove(subtask.getId());
            }
        }

        public void deleteSubtaskByID(int id) {
            Subtask subtask = subtasks.remove(id);
            Epic epic = epics.get(subtask.getEpicID());
            epic.getSubtaskList().remove(subtask);
            updateEpicStatus(epic);
        }

    private int getNextID() {
        return nextID++;
    }

    public Task updateTask(Task task) {
        int taskID = task.getId();
        if (!tasks.containsKey(taskID)) {
            return null;
        }
        tasks.replace(taskID, task);
        return task;
    }

    public Task addTask(Task task) {
        task.setId(getNextID());
        tasks.put(task.getId(), task);
        return task;
    }

    public Epic addEpic(Epic epic) {
        epic.setId(getNextID());
        epics.put(epic.getId(), epic);
        return epic;
    }

    public Subtask addSubtask(Subtask subtask) {
        subtask.setId(getNextID());
        Epic epic = epics.get(subtask.getEpicID());
        epic.addSubtask(subtask);
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(epic);
        return subtask;
    }

    public Epic updateEpic(Epic epic) {
        int epicID = epic.getId();
        if (!epics.containsKey(epicID)) return null;
        epics.replace(epicID, epic);
        return epic;
    }

    public Subtask updateSubtask(Subtask subtask) {
        int subtaskID = subtask.getId();
        if (!subtasks.containsKey(subtaskID)) return null;

        Subtask oldSubtask = subtasks.get(subtaskID);
        Epic epic = epics.get(oldSubtask.getEpicID());
        epic.getSubtaskList().remove(oldSubtask);
        epic.addSubtask(subtask);

        subtasks.replace(subtaskID, subtask);
        updateEpicStatus(epic);
        return subtask;
    }

    private void updateEpicStatus(Epic epic) {
        ArrayList<Subtask> subtaskList = epic.getSubtaskList();
        if (subtaskList.isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }

        int doneCount = 0;
        int newCount = 0;

        for (Subtask subtask : subtaskList) {
            if (subtask.getStatus() == Status.DONE) doneCount++;
            else if (subtask.getStatus() == Status.NEW) newCount++;
        }

        if (doneCount == subtaskList.size()) epic.setStatus(Status.DONE);
        else if (newCount == subtaskList.size()) epic.setStatus(Status.NEW);
        else epic.setStatus(Status.IN_PROGRESS);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public Task getTaskByID(int id) {
        return tasks.get(id);
    }

    public Epic getEpicByID(int id) {
        return epics.get(id);
    }

    public Subtask getSubtaskByID(int id) {
        return subtasks.get(id);
    }

    public void clearTasks() {
        tasks.clear();
    }

    public void clearEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void clearSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubtaskList().clear();
            updateEpicStatus(epic);
        }


    }
}





