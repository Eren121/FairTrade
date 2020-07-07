package fr.rafoudiablol.internationalization;

public class Variable implements MessagePart {
    private final int index;

    public Variable(int index) {
        this.index = index;

        if(index < 0) {
            throw new IllegalArgumentException("Negative argument index.");
        }
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String translate(Context context) {
        final int count = context.getVariablesCount();

        if(index >= count) {
            throw new IllegalArgumentException("Invalid argument index: message " + context.getMessage().name() + " has only " + count + " arguments, and variable has index " + index + ". Wrong numbers of arguments to a call to UnlocalizedMessage.translate(...)");
        }

        return context.getVariable(index);
    }
}
