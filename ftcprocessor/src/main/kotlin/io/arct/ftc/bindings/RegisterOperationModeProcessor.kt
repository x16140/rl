package io.arct.ftc.bindings

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.plusParameter
import io.arct.ftc.eventloop.LinearOperationMode
import io.arct.ftc.eventloop.OperationMode
import io.arct.ftc.eventloop.bindings.LinearOpModeBinding
import io.arct.ftc.eventloop.bindings.OpModeBinding
import java.io.File
import java.util.*
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

class RegisterOperationModeProcessor : AbstractProcessor() {
    val classes: MutableList<String> = mutableListOf()

    override fun process(set: Set<TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        val elements = roundEnv?.getElementsAnnotatedWith(OperationMode.Register::class.java)

        elements?.forEach {
            if (it.kind != ElementKind.CLASS) {
                processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, "Only classes can be used with the OperationMode.Register annotation")
                return true
            }

            generate(it as TypeElement)
        }

        classes.addAll(elements?.map { (it as TypeElement).qualifiedName.toString() + "_Binding" } ?: listOf())
        generateManifest()

        return true
    }

    private fun generate(element: TypeElement) {
        val name = element.simpleName.toString()
        val fileName = "${name}_Binding"
        val pack = processingEnv.elementUtils.getPackageOf(element).toString()

        val annotation = element.getAnnotation(OperationMode.Register::class.java)

        if (annotation == null) {
            processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, "Could not find type for operation mode $name")
            return
        }

        val boundAnnotation = when (annotation.type) {
            OperationMode.Type.Autonomous -> Autonomous::class.java
            OperationMode.Type.Operated -> TeleOp::class.java
            OperationMode.Type.Disabled -> Disabled::class.java
        }

        val opModeType = when (element.superclass.asTypeName()) {
            OperationMode::class.asTypeName() -> OpModeBinding::class.asTypeName()
            LinearOperationMode::class.asTypeName() -> LinearOpModeBinding::class.asTypeName()
            else -> return run {
                processingEnv.messager.printMessage(
                    Diagnostic.Kind.ERROR,
                    "${element.simpleName} is not an operation mode!"
                )
            }
        }

        val generated = FileSpec.builder(pack, fileName)
            .addType(
                TypeSpec.classBuilder(fileName)
                    .addAnnotation(
                        AnnotationSpec.builder(boundAnnotation)
                            .addMember("name = %S", if (annotation.name != "") annotation.name else name)
                            .addMember("group = %S", annotation.group)
                            .build()
                    )
                    .addProperty(
                        PropertySpec.builder("name", String::class)
                            .initializer("\"${if (annotation.name != "") annotation.name else name}\"")
                            .build()
                    )
                    .addProperty(
                        PropertySpec.builder("group", String::class)
                            .initializer("\"${annotation.group}\"")
                            .build()
                    )
                    .addProperty(
                        PropertySpec.builder("type", OperationMode.Type::class)
                            .initializer("io.arct.ftc.eventloop.OperationMode.Type.${annotation.type.name}")
                            .build()
                    )
                    .superclass(opModeType)
                    .addFunction(
                        FunSpec.builder("getOperationMode")
                            .returns(element.superclass.asTypeName())
                            .addModifiers(KModifier.PROTECTED, KModifier.OVERRIDE)
                            .addStatement("return $name()")
                            .build()
                    )
                    .build()
            )
            .build()

        val dir = processingEnv.options["kapt.kotlin.generated"] ?:
            return processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, "Can't find the target directory for generated Kotlin files.")
        val file = File(dir, fileName)

        file.parentFile.mkdirs()
        generated.writeTo(file)
    }

    private fun generateManifest() {
        val fileName = "ProgramManifest"
        val generated = FileSpec.builder("io.arct.ftc.manifest", fileName)
            .addType(
                TypeSpec.classBuilder(fileName)
                    .addFunction(
                        FunSpec.builder("programs")
                            .returns(List::class.asTypeName().plusParameter(String::class.asTypeName()))
                            .addStatement("return kotlin.collections.listOf(${"%S,".repeat(classes.size)})", *classes.toTypedArray())
                            .build()
                    )
                    .build()
            )
            .build()

        val dir = processingEnv.options["kapt.kotlin.generated"] ?:
            return processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, "Can't find the target directory for generated Kotlin files.")
        val file = File(dir, fileName)

        file.parentFile.mkdirs()
        generated.writeTo(file)
    }

    override fun getSupportedAnnotationTypes(): Set<String> =
        Collections.singleton(OperationMode.Register::class.java.canonicalName)

    override fun getSupportedSourceVersion(): SourceVersion =
        SourceVersion.latestSupported()
}