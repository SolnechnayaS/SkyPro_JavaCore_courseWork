public enum AppearsIn {
    YEARLY_TASK("Ежегодная"),
    MONTHLY_TASK("Ежемесячная"),
    WEEKLY_TASK("Еженедельная"),
    DAILY_TASK("Ежедневная"),
    ONE_TIME_TASK("Разовая");

    private String appearsIn;

    AppearsIn(String appearsIn) {
        this.appearsIn = appearsIn;
    }

    public String getAppearsIn() {
        return appearsIn;
    }

    public void setAppearsIn(String appearsIn) {
        this.appearsIn = appearsIn;
    }


    @Override
    public String toString() {
        return appearsIn;
    }
}
