import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TaskService {
    static Scanner scanner = new Scanner(System.in);
    private final static Map<Integer, Task> taskMap = new HashMap<>();
    private final static Collection<Task> removedTasks = new ArrayList<>();


    public static void add() throws IncorrectArgumentException {
        System.out.println("Введите название задачи: ");
        String title = scanner.useDelimiter("\n").next();
        System.out.println("Введите описание задачи: ");
        String description = scanner.useDelimiter("\n").next();

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

        repeatTask(title, description, type, appearsIn);
    }

    public static void updateTitle(int id, String title) {
        taskMap.get(id).setTitle(title);
    }

    public static void updateDescription(int id, String description) {
        taskMap.get(id).setDescription(description);
    }

    static AppearsIn setAppearsIn() throws IncorrectArgumentException {
        int appearsInNumber = Main.inputInt();
        AppearsIn appearsIn;
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
                appearsIn = Task.appearsInDefault;
                break;
        }
        return appearsIn;
    }

    static TypeTask setTypeTask() throws IncorrectArgumentException {
        int typeNumber = Main.inputInt();
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
        Predicate<Task> taskPredicateDate = task -> task.getDataTime().toLocalDate().equals(localDate.toLocalDate());

        return taskMap
                .values()
                .stream()
                .filter(taskPredicateDate)
                .collect(Collectors.toList());
    }


    public static Map<LocalDate, Collection<Task>> getAllGroupeByDate() {

        Comparator<LocalDateTime> order = (o1, o2) -> {
            try {
                return o1.compareTo(o2);
            } catch (NullPointerException e) {
                return 0;
            }
        };
        Map<LocalDate, Collection<Task>> allGroupeByDate = new TreeMap<>();
        Collection<LocalDateTime> allDateTask = taskMap
                .values()
                .stream()
                .map(Task::getDataTime)
                .sorted(order)
                .collect(Collectors.toList());

        for (LocalDateTime date : allDateTask
        ) {
            allGroupeByDate.put(date.toLocalDate(), getAllByDate(date));
        }

        return allGroupeByDate;
    }

    public static int findTask() throws IncorrectArgumentException, TaskNotFoundException {
        System.out.println("Введите id задачи: ");
        int idNumber = Main.inputInt();
        try {
            taskMap.get(idNumber).toString();
            //только при добавлении .toString или иного метода начинает обрабатывать исключение, в противном случае работает с null-задачей
        } catch (RuntimeException e) {
            //throw new TaskNotFoundException ("Некорректный id");
            //можем выбрасывать исключение и останавливать работу программы или продолжать запрашивать корректный id
            System.out.println("Некорректный id");
            idNumber = findTask();
        }
        return idNumber;

    }

    public static void repeatTask(String title, String description, TypeTask type, AppearsIn appearsIn) {
        LocalDateTime firstLDT = LocalDateTime.now();

        switch (appearsIn) {
            case DAILY_TASK:
                for (int i = 0; i < LocalDateTime.now().toLocalDate().lengthOfYear()+1; i++) {
                    Task temp = new Task(title, description, type, AppearsIn.DAILY_TASK, firstLDT.plusDays(i));
                    taskMap.put(temp.getId(), temp);
                }
                break;
            case WEEKLY_TASK:
                for (int i = 0; i < (LocalDateTime.now().toLocalDate().lengthOfYear()/7+1); i++) {
                    Task temp = new Task(title, description, type, AppearsIn.WEEKLY_TASK, firstLDT.plusWeeks(i));
                    taskMap.put(temp.getId(), temp);
                }
            case MONTHLY_TASK:
                for (int i = 0; i < 13; i++) {
                    Task temp = new Task(title, description, type, AppearsIn.MONTHLY_TASK, firstLDT.plusMonths(i));
                    taskMap.put(temp.getId(), temp);
                }
                break;
            case YEARLY_TASK:
                for (int i = 0; i < 2; i++) {
                    Task temp = new Task(title, description, type, AppearsIn.YEARLY_TASK, firstLDT.plusYears(i));
                    taskMap.put(temp.getId(), temp);
                }
                break;
            case ONE_TIME_TASK:
                Task temp = new Task(title, description, type, AppearsIn.ONE_TIME_TASK, firstLDT);
                taskMap.put(temp.getId(), temp);
                break;
            default:
                Task taskDefault = new Task(title, description, type, Task.appearsInDefault, firstLDT);
                taskMap.put(taskDefault.getId(), taskDefault);
                break;

        }

    }

}
