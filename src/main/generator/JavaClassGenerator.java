import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.squareup.javapoet.TypeSpec;

import java.io.*;

public class JavaClassGenerator {
    JavaClassFileModel classFile;

    public JavaClassGenerator() {
        classFile = new JavaClassFileModel();
    }

    public void GenerateClassFromClassFile(File file) throws FileNotFoundException {
        CompilationUnit cu = JavaParser.parse(file);
        JavaClassFileModel fm = new JavaClassFileModel();

        VoidVisitor<JavaClassFileModel> jcv = new JavaClassVisitor();
        jcv.visit(cu, fm);

        GenerateJavaFileFromModel();
    }

    private void GenerateJavaFileFromModel(JavaClassFileModel fm) {
        JavaClassModel cm = fm.classModel;

        TypeSpec helloWorld = TypeSpec.classBuilder(cm.className)
                .addModifiers()
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build();

        JavaFile javaFile = JavaFile.builder("com.example.helloworld", helloWorld)
                .build();

        javaFile.writeTo(System.out);
    }

    public static JavaClassAccessModifier GetClassAccessModifierFromAstModifier(Modifier mod) {
        JavaClassAccessModifier modifier;

        switch (mod) {
            case PUBLIC:
                modifier = JavaClassAccessModifier.PUBLIC;
                break;
            default:
                modifier = JavaClassAccessModifier.DEFAULT;
        }

        return modifier;
    }
}
