import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.EnumSet;
import java.util.Iterator;

public class JavaClassVisitor extends VoidVisitorAdapter<JavaClassFileModel> {
    @Override
    public void visit(ClassOrInterfaceDeclaration coid, JavaClassFileModel classFileModel) {
        String className = coid.getName().toString();
        EnumSet<Modifier> mods = coid.getModifiers();
        JavaClassModel classModel = new JavaClassModel();
        classFileModel.classModel = classModel;

        mods.forEach(modifier -> {
            classModel.className = className;
            classModel.classAccessModifiers.add(JavaClassGenerator.GetClassAccessModifierFromAstModifier(modifier));
        });
    }
}
