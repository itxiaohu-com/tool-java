package com.itxiaohu.tool.screw;

import cn.smallbun.screw.core.process.ProcessConfig;
import cn.smallbun.screw.extension.pojo.PojoConfiguration;
import cn.smallbun.screw.extension.pojo.execute.PojoExecute;
import cn.smallbun.screw.extension.pojo.strategy.HumpNameStrategy;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.ArrayList;

public class PojoGeneration {

    public static void main(String[] args) {
        pojoGeneration();
    }

    /**
     * pojo生成
     */
    public static void pojoGeneration() {
        //数据源
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/example_flink_source");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("123456");
        //设置可以获取tables remarks信息
        hikariConfig.addDataSourceProperty("useInformationSchema", "true");
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setMaximumPoolSize(5);
        DataSource dataSource = new HikariDataSource(hikariConfig);

        ProcessConfig processConfig = ProcessConfig.builder()
                //指定生成逻辑、当存在指定表、指定表前缀、指定表后缀时，将生成指定表，其余表不生成、并跳过忽略表配置
                //根据名称指定表生成
                .designatedTableName(new ArrayList<>())
                //根据表前缀生成
                .designatedTablePrefix(new ArrayList<>())
                //根据表后缀生成
                .designatedTableSuffix(new ArrayList<>()).build();

        //设置生成pojo相关配置
        PojoConfiguration config = new PojoConfiguration();
        //设置文件存放路径
        config.setPath("D:///cn//smallbun//screw//");
        //设置包名
        config.setPackageName("cn.smallbun.screw");
        //设置是否使用lombok
        config.setUseLombok(true);
        //设置数据源
        config.setDataSource(dataSource);
        //设置命名策略
        config.setNameStrategy(new HumpNameStrategy());
        //设置表过滤逻辑
        config.setProcessConfig(processConfig);
        //执行生成
        new PojoExecute(config).execute();
    }

}