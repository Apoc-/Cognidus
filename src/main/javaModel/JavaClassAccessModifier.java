import java.lang.reflect.Modifier;

public enum JavaClassAccessModifier {
    PRIVATE (Modifier.PRIVATE),
    PROTECTED (Modifier.PROTECTED),
    PUBLIC (Modifier.PUBLIC);

    private Modifier value;

    JavaClassAccessModifier(Modifier mod) {
        this.value = mod;
    }

    public Modifier getValue() {
        return this.value;
    }
}
