package actions;
// the idea behind using abstract right now is so we don't have to impliment the execute() method yet
public abstract class SpecialSkill implements Action {
    private String name;
    private String description;

    public SpecialSkill(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
