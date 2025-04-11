package manager;

import task.*;
import java.util.*;

public abstract class InMemoryTaskManager implements TaskManager {
    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();
    protected final Map<Integer, Subtask> subtasks = new HashMap<>();
    protected final HistoryManager historyManager = Managers.getDefaultHistory();
    protected int nextId = 1;

    @Override
    public int getNextId() {
        return nextId++;
    }

    @Override
    public Task addTask(Task task) {
        task.setId(getNextId());
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public Epic addEpic(Epic epic) {
        epic.setId(getNextId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public Subtask addSubtask(Subtask subtask) {
        Epic epic = epics.get(subtask.getEpicID());
        if (epic == null) return null;
        subtask.setId(getNextId());
        subtasks.put(subtask.getId(), subtask);
        epic.addSubtask(subtask);
        updateEpicStatus(epic);
        return subtask;
    }

    private void updateEpicStatus(Epic epic) {
        List<Subtask> subs = getEpicSubtasks(epic);
        if (subs.isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }
        boolean allDone = true;
        boolean allNew = true;
        for (Subtask sub : subs) {
            if (sub.getStatus() != Status.DONE) allDone = false;
            if (sub.getStatus() != Status.NEW) allNew = false;
        }
        if (allDone) epic.setStatus(Status.DONE);
        else if (allNew) epic.setStatus(Status.NEW);
        else epic.setStatus(Status.IN_PROGRESS);
    }

    // Остальные методы реализованы аналогично с вызовами historyManager.remove()
}