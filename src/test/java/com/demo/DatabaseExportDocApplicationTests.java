package com.demo;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class DatabaseExportDocApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void createDataDoc() {
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        EngineConfig engineConfig = EngineConfig.builder()
                .fileOutputDir("D:\\")
                .openOutputDir(false)
                .fileType(EngineFileType.WORD)
                .produceType(EngineTemplateType.freemarker)
                .build();
        Configuration configuration = Configuration.builder()
                .version("2.0.0")
                .description("数据库设计文档生成")
                .dataSource(dataSource)
                .engineConfig(engineConfig)
                .produceConfig(getProcessConfig())
                .build();
        new DocumentationExecute(configuration).execute();
    }

    /**
     * 配置想要生成的表+配置需要忽略的表
     *
     * @return 生成表配置
     */
    private static ProcessConfig getProcessConfig() {
        List<String> ignoreTableName = Arrays.asList("sys_user_role", "sys_role_menu");
        return ProcessConfig.builder()
                .designatedTableName(Arrays.asList("sys_user", "sys_role", "sys_menu", "sys_login_log", "sys_dictionary", "sys_dept"))
                .ignoreTableName(ignoreTableName)
                .build();
    }

}
