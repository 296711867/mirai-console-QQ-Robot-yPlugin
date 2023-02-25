package yplugin.Commands;


public abstract class PureCommand implements Command{
    private final String commond;
    private final String name;

    public PureCommand(String commond, String name) {
        this.commond = commond;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCommond() {
        return commond;
    }
}
