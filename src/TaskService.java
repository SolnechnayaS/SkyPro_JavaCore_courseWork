import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.stream;

public class TaskService {
    static Scanner scanner = new Scanner(System.in);
    static DateTimeFormatter dtfOnlyDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static Map<Integer, Task> taskMap = new HashMap<>();
    private static Collection<Task> removedTasks = new ArrayList<>();

    public static void add() {
        System.out.println("Введите название задачи: ");
        String title = scanner.next();
        System.out.println("Введите описание задачи: ");
        String description = scanner.next();

        System.out.println("Выберите тип задачи (по умолчанию значение " + Task.typeDefault + "):\n" +
                "1 - Личная;\t" +
                "2 - Рабочая");
        TypeTask type = setTypeTask();

        System.out.println("Выберите повторяемость задачи (по умолчанию значение " + Task.appearsInDefault + "):\n" +
                "1 - Разовая (однократная);\t" +
                "2 - Ежедневная;\t" +
                "3 - Еженедельная;\t" +
                "4 - Ежемесячная;\t" +
                "5 - Ежегодная;\t"
        );
        AppearsIn appearsIn = setAppearsIn();

        Task newTask = new Task(title, description, type, appearsIn);
        taskMap.put(newTask.getId(), newTask);
    }




    public static Task updateTitle(int id, String title) {
        Task temp = taskMap.get(id);
        temp.setTitle(title);
        return taskMap.put(temp.getId(), temp);
    }

    public static Task updateDescription(int id, String description) {
        Task temp = taskMap.get(id);
        temp.setDescription(description);
        return taskMap.put(temp.getId(), temp);
    }

    static AppearsIn setAppearsIn() {
        int appearsInNumber = scanner.nextInt();
        AppearsIn appearsIn = null;
        switch (appearsInNumber) {
            case 1:
                System.out.println("Выбран тип - Однократная задача\n");
                appearsIn = AppearsIn.ONE_TIME_TASK;
                break;
            case 2:
                System.out.println("Выбран тип - Ежедневная задача\n");
                appearsIn = AppearsIn.DAILY_TASK;
                break;
            case 3:
                System.out.println("Выбран тип - Еженедельная задача\n");
                appearsIn = AppearsIn.WEEKLY_TASK;
                break;
            case 4:
                System.out.println("Выбран тип - Ежемесячная задача\n");
                appearsIn = AppearsIn.MONTHLY_TASK;
                break;
            case 5:
                System.out.println("Выбран тип - Ежегодная задача\n");
                appearsIn = AppearsIn.YEARLY_TASK;
                break;
            default:
                System.out.println("Выбран некорректный тип, оставлено значение по умолчанию\n");
                break;
        }
        return appearsIn;
    }

    static TypeTask setTypeTask() {
        int typeNumber = scanner.nextInt();
        TypeTask type = null;
        switch (typeNumber) {
            case 1:
                System.out.println("Выбран тип - Личная задача\n");
                type = TypeTask.PERSONAL;
                break;
            case 2:
                System.out.println("Выбран тип - Рабочая задача\n");
                type = TypeTask.WORK;
                break;
            default:
                System.out.println("Выбран некорректный тип, оставлено значение по умолчанию\n");
                break;
        }
        return type;
    }

    public static Task updateTypeTask(int id, TypeTask typeTask) {
        Task temp = taskMap.get(id);
        temp.setType(typeTask);
        return taskMap.put(temp.getId(), temp);
    }

    public static Task updateRepeatTask(int id, AppearsIn appearsIn) {
        Task temp = taskMap.get(id);
        temp.setAppearsIn(appearsIn);
        return taskMap.put(temp.getId(), temp);
    }

    public static void remove(int id) {
        removedTasks.add(taskMap.get(id));
        taskMap.remove(id);
    }

    public static Collection<Task> getRemovedTasks() {
        return removedTasks;
    }

    public static Map<Integer, Task> getTaskMap() {
        return taskMap;
    }

    public static Collection<Task> getAllByDate(LocalDateTime localDate) {
        Predicate<Task> taskPredicateDate = new Predicate<>() {
            @Override
            public boolean test(Task task) {
                return task.getDataTime().toLocalDate().equals(localDate.toLocalDate());
            }
        };

        Collection<Task> allTask = taskMap
                .values()
                .stream()
                .filter(taskPredicateDate)
                .collect(Collectors.toList());
        return allTask;
    }

    public static Map<LocalDate, Collection<Task>> getAllGroupeByDate() {

        Map<LocalDate, Collection<Task>> allGroupeByDate = new HashMap<>();
        Collection<LocalDateTime> allDateTask = taskMap
                .values()
                .stream()
                .map(Task::getDataTime)
                .collect(Collectors.toList());

        for (LocalDateTime date : allDateTask
        ) {
            allGroupeByDate.put(date.toLocalDate(), getAllByDate(date));
        }

        return allGroupeByDate;
    }

}
