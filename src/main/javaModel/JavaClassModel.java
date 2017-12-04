import java.util.HashSet;

public class JavaClassModel {
    public String className;
    public HashSet<JavaClassAccessModifier> classAccessModifiers;

    public JavaClassModel() {
        this.className = new String();
        this.classAccessModifiers = new HashSet<>();
    }
}
