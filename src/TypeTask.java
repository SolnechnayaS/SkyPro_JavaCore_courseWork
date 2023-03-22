public enum TypeTask {
    WORK ("Рабочая"),
    PERSONAL ("Личная");

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    TypeTask(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
