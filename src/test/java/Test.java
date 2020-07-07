import java.lang.reflect.Field;

public class Test {

    public int i;
    public Object o;

    public static void main(String[] args) throws Exception {
        Class<?> myType = String.class;
        Object o = new Test();

        for (Field field : o.getClass().getFields()) {
            if (field.getType().isAssignableFrom(myType)) {
                System.out.println("Field " + field + " is assignable from type " + o.getClass());
            }
        }
    }
}