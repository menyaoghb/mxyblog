package com.mxy.generator.controller;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.mxy.common.core.entity.BeautifulWords;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 孟小耀
 * @Description 代码生成器
 * @since 2021-17-18
 */
public class CodeGenerator {

    public static void main(String[] args) {
        String schemaName = "mxy_blog";
        // 表名，多个英文逗号分割
        //String[] tableName = new String[] { "sys_role_menu","sys_oper_log","sys_menu","sys_dict_type","sys_dict_data" };
        String[] tableName = new String[]{"order_monitor_analysis"};

        String url = "jdbc:mysql://110.42.135.120:3306/mxy_blog?serverTimezone=GMT%2B8";
        String driverName = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "Aa123456.";
        String author = "孟小耀";

        String parent = "com.mxy";
        String moduleName = "system";
        String entityPath = "entity";
        String controllerPath = "controller";
        String servicePath = "service";
        String serviceImplPath = "service.impl";
        String mapperXmlPath = "mapper.xml";
        String mapperPath = "mapper";

        String path = "G:\\B\\mxy_blog\\mxy_business\\mxy_system";
        String genPath = "G:\\B\\mxy_blog\\mxy_common\\mxy_common_core";
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        // 生成文件的输出目录
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(path + "/src/main/java");
        // 是否打开输出目录，默认true
        gc.setOpen(false);
        // 是否覆盖已有文件,默认false
        gc.setFileOverride(true);
        // 是否在xml中添加二级缓存配置，默认false
        gc.setEnableCache(false);
        // 开发者
        gc.setAuthor(author);
        // 开启 swagger2 模式，默认false
        gc.setSwagger2(true);
        // 开启 BaseResultMap，默认false
        gc.setBaseResultMap(true);
        // 开启 通用查询结果列
        gc.setBaseColumnList(true);
        // 指定生成的主键的ID类型，默认null
        gc.setIdType(IdType.ID_WORKER_STR);
        //设置datetime类型为Date
        gc.setDateType(DateType.ONLY_DATE);
        //开启 ActiveRecord 模式
        gc.setActiveRecord(true);
        gc.setEntityName("%s");
        gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setSchemaName(schemaName);
        dsc.setUrl(url);
        dsc.setDriverName(driverName);
        dsc.setUsername(userName);
        dsc.setPassword(password);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(parent)
                .setModuleName(moduleName)
                .setMapper(mapperPath)
                .setService(servicePath)
                .setServiceImpl(serviceImplPath)
                .setController(controllerPath)
                .setEntity(entityPath)
                .setXml(mapperXmlPath);
        mpg.setPackageInfo(pc);


        // 自定义需要填充的字段 数据库中的字段
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("create_time", FieldFill.INSERT));
        tableFillList.add(new TableFill("update_time", FieldFill.UPDATE));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 表名生成策略(下划线转驼峰命名)
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 列名生成策略(下划线转驼峰命名)
        //strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 是否生成实体时，生成字段注解
        strategy.setEntityTableFieldAnnotationEnable(false);
        // 是否启动Lombok配置
        strategy.setEntityLombokModel(true);
        // 是否启动REST风格配置
        strategy.setRestControllerStyle(true);
        //自动填充设置
        strategy.setTableFillList(tableFillList);
        //逻辑删除属性名称
        strategy.setLogicDeleteFieldName("is_delete");

        strategy.setSuperEntityClass("com.mxy.common.core.entity.vo.BaseEntity");

        // 自定义实体父类
//        strategy.setSuperEntityClass("com.baomidou.mybatisplus.extension.activerecord.Model");
        // 自定义controller父类
//        strategy.setSuperControllerClass("pro.nbbt.base.controller.BaseController");
        // 自定义service父接口
//        strategy.setSuperServiceClass("com.baomidou.mybatisplus.extension.service.IService");
        // 自定义service实现类
//        strategy.setSuperServiceImplClass("com.baomidou.mybatisplus.extension.service.impl.ServiceImpl");
        // 自定义mapper接口
//        strategy.setSuperMapperClass("com.baomidou.mybatisplus.core.mapper.BaseMapper");
        // 自定义基础的Entity类，公共字段
//        strategy.setSuperEntityColumns("user_id");
        // 表名
        strategy.setInclude(tableName);
        mpg.setStrategy(strategy);


        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();


        // 调整 xml 生成目录演示
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return path + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        //调整 entityVO 生成目录
        focList.add(new FileOutConfig("/templates/entityVo.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path + "/src/main/java/com/mxy/system/entity/vo/" + tableInfo.getEntityName() + "VO.java";
            }
        });

        //调整 entity 生成目录
        focList.add(new FileOutConfig("/templates/entity.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return genPath + "/src/main/java/com/mxy/common/core/entity/" + tableInfo.getEntityName() + ".java";
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        TemplateConfig tc = new TemplateConfig();
        tc.setController("/templates/controller.java.vm");
        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。
        tc.setEntity(null);
        tc.setService("/templates/service.java.vm");
        tc.setServiceImpl("/templates/serviceImpl.java.vm");
        tc.setMapper("/templates/mapper.java.vm");
        tc.setXml(null);
        mpg.setTemplate(tc);

        // 执行
        mpg.execute();
    }

}
