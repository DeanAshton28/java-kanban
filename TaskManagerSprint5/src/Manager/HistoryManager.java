package Manager;

import Task.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task Task);

    List<Task> getHistory();
}