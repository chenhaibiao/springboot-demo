package com.springboot.controller;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.kotlin.KotlinFile;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.internal.util.StringUtility;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

public class MyCommentGenerator implements CommentGenerator {

    public static void main(String[] args) {
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        // 给出generatorConfig.xml文件的位置，绝对地址或者类路径下
        File configFile = new File(MyCommentGenerator.class.getClassLoader().getResource("generator/generatorConfig.xml").getFile());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        try {
            Configuration config = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Properties properties;

    private boolean suppressDate;

    private boolean suppressAllComments;

    private boolean addRemarkComments;

    private SimpleDateFormat dateFormat;

    public MyCommentGenerator() {
        super();
        properties = new Properties();
        suppressDate = false;
        suppressAllComments = false;
        addRemarkComments = false;
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);

        suppressDate = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));
        suppressAllComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
        addRemarkComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_ADD_REMARK_COMMENTS));

        String dateFormatString = properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_DATE_FORMAT);
        if (StringUtility.stringHasValue(dateFormatString)) {
            dateFormat = new SimpleDateFormat(dateFormatString);
        }
    }


    /**
     * Java文件注释（一般用于生成版权信息）
     */
    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        // Dao类注释
        // JavaMapperGenerator中没有调用addClassComment()方法
        if (compilationUnit instanceof Interface) {
            Interface tInterface = (Interface) compilationUnit;
            tInterface.addJavaDocLine("/**");
            tInterface.addJavaDocLine(" *");
            tInterface.addJavaDocLine(" * @author chb");
            tInterface.addJavaDocLine(" * @date " + getDateString());
            tInterface.addJavaDocLine(" */");
        }
    }

    /**
     * 类注释
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
    }

    /**
     * 类注解
     */
    @Override
    public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> imports) {
        // 实体类添加 @Data、@Builder、@NoArgsConstructor、@AllArgsConstructor注解
        // 这里比较尴尬的是BaseRecordGenerator类没有配置不生成getter、setter方法的入口，所以@Data就不需要了
        if (innerClass instanceof TopLevelClass) {
            TopLevelClass topLevelClass = (TopLevelClass) innerClass;
            topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.Data"));
            topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.Builder"));
            topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.NoArgsConstructor"));
            topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.AllArgsConstructor"));
            topLevelClass.addAnnotation("@Data");
            topLevelClass.addAnnotation("@Builder");
            topLevelClass.addAnnotation("@NoArgsConstructor");
            topLevelClass.addAnnotation("@AllArgsConstructor");
        }
    }

    /**
     * 实体类的注释
     */
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (!addRemarkComments) {
            return;
        }

        // 实体类注释
        // BaseRecordGenerator类没有调用addClassComment()方法，调用了addModelClassComment()，所以在这里添加实体类的注释
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" *");
        topLevelClass.addJavaDocLine(" * @author chb");
        topLevelClass.addJavaDocLine(" * @date " + getDateString());
        topLevelClass.addJavaDocLine(" */");

        // 添加实体类的注解
        // BaseRecordGenerator类没有调用addClassAnnotation()
        addClassAnnotation(topLevelClass, introspectedTable, null);
    }

    /**
     * 实体类字段注释
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }

        field.addJavaDocLine("/**");
        field.addJavaDocLine(" * " + introspectedColumn.getRemarks());
        field.addJavaDocLine(" */");
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
    }

    /**
     * 通用方法注释
     *
     * <p>如Dao中的方法
     */
    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
    }

    /**
     * getter方法的注释
     */
    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
    }

    /**
     * setter方法的注释
     */
    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
    }

    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable,
                                           Set<FullyQualifiedJavaType> imports) {
    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable,
                                           IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> imports) {
    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable,
                                   Set<FullyQualifiedJavaType> imports) {
    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable,
                                   IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> imports) {
    }


    @Override
    public void addFileComment(KotlinFile kotlinFile) {
    }

    @Override
    public void addComment(XmlElement xmlElement) {
    }

    @Override
    public void addRootComment(XmlElement rootElement) {
    }

    /**
     * 返回一个格式化日期
     *
     * @return
     */
    protected String getDateString() {
        if (suppressDate) {
            return null;
        } else if (dateFormat != null) {
            return dateFormat.format(new Date());
        } else {
            return new Date().toString();
        }
    }
}
