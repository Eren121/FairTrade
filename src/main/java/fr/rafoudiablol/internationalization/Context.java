package fr.rafoudiablol.internationalization;

public class Context {
    private final UnlocalizedMessage message;
    private final String[] variables;

    public Context(UnlocalizedMessage message, String[] variables) {
        this.message = message;
        this.variables = variables;
    }

    public UnlocalizedMessage getMessage() {
        return message;
    }

    public String getVariable(int index) {
        return variables[index];
    }

    public int getVariablesCount() {
        return variables.length;
    }
}
