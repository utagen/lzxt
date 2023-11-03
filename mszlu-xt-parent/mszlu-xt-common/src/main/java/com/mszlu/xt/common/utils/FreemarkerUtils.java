package com.mszlu.xt.common.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FreemarkerUtils {

    public static void main(String[] args) throws IOException, TemplateException {

        Configuration configuration = new Configuration(new Version("2.3.31"));
        File dir = new File("E:\\code\\mszlu\\mszlu-xt-parent\\mszlu-xt-common\\src\\main\\resources\\templates");
        //设置模板所在的文件目录
        configuration.setDirectoryForTemplateLoading(dir);
        //设置编码
        configuration.setDefaultEncoding("utf-8");
        //根据模板名称获取模板
        Template template = configuration.getTemplate("index.ftl");
        //填充数据 并且生成模板
        Map<String, String> dataModel = new HashMap<>();
        dataModel.put("name", "mszlu");
        FileWriter out = new FileWriter(new File("E:\\code\\mszlu\\mszlu-xt-parent\\mszlu-xt-common\\src\\main\\resources\\templates\\index.html"));
        template.process(dataModel, out);
        out.close();
    }
}