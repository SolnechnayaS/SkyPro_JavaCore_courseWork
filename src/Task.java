
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class Task {
    static DateTimeFormatter dtfFull = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private static int idGenerator =0;
    private final int id;
    private final LocalDateTime dataTime;
    private String title;
    private String description;
    private TypeTask type;
    private AppearsIn appearsIn;

    static final String titleDefault = "Без названия";
    static final String descriptionDefault = "Описание не заполнено";
    static final TypeTask typeDefault = TypeTask.PERSONAL;
    static final AppearsIn appearsInDefault=AppearsIn.ONE_TIME_TASK;

    public int getIdGenerator() {
        return idGenerator;
    }

    public void setIdGenerator(int idGenerator) {
        this.idGenerator = idGenerator;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDataTime() {
        return dataTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeTask getType() {
        return type;
    }

    public void setType(TypeTask type) {
        this.type = type;
    }

    public AppearsIn getAppearsIn() {
        return appearsIn;
    }

    public void setAppearsIn(AppearsIn appearsIn) {
        this.appearsIn = appearsIn;
    }

    public Task(String title, String description, TypeTask type, AppearsIn appearsIn) {
        this.id = getIdGenerator()+1;
        setIdGenerator(this.id);
        this.dataTime = LocalDateTime.now();

        if (title==null || title.isBlank()) {
            this.title =titleDefault;
        }
        else {
            this.title = title;
        }

        if (description==null || description.isBlank()) {
            this.description =descriptionDefault;
        }
        else {
            this.description = description;
        }

        if (type==null) {
            this.type =typeDefault;
        }
        else {
            this.type = type;
        }

        if (appearsIn==null) {
            this.appearsIn =appearsInDefault;
        }
        else {
            this.appearsIn = appearsIn;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return id == task.id && dataTime.equals(task.dataTime) && Objects.equals(title, task.title) && description.equals(task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataTime, title, description);
    }

    @Override
    public String toString() {
        return "{id=" + id +
                ", дата/время: " + dataTime.format(dtfFull) +
                ", заголовок: " + title +
                ", описание: " + description +
                ", тип: " + type +
                ", повторяемость: " + appearsIn +
                "}";
    }

}