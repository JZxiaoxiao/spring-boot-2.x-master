> `springboot-storm`：基于SpingBoot 2.x 的storm 流式拓扑处理框架。

- 技术概览：springboot 2.X+ storm + kafka + redis + Hadoop +habase +mybatis + HikariCP
- 项目初衷：公司需要开发一套话单实时预处理，类账，入库的项目。而storm 作为当前较火的实时流式处理框架，而kafka 也是现在热门的消息中间件，用springboot 则可以很方便整合其它中间件（redis，mybatis等等），本项目通过springboot 整合hbase，redis，mysql，storm，kafka，在开发过程中，遭遇了许多问题，但是通过参考资料，最终完成项目，通过压测。
- 本项目秉着即开即用的原则，只是提供一个较为基础的模板，在linux 上已经测试通过（单机和集群模式）。
- 有参考部分开发者的设计，在这里表示感谢。

# storm单机模式
- 以springboot 方式启动.

- 参考：springboot-storm.sh<br>


      JAR_PATH=${HOME}/springboot-storm/springboot-storm-0.0.1-SNAPSHOT.jar
      LIB_PATH=${HOME}/springboot-storm/libs
      CONFIG_PATH=${HOME}/springboot-storm/resources
      java -jar -Dloader.path=${LIB_PATH},${CONFIG_PATH} ${JAR_PATH}

- jar包冲突：单机模式防止storm日志和springboot日志冲突.<br>

        <dependency>
            <groupId>org.springframework.boot</groupId>    
            <artifactId>spring-boot-starter</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.apache.logging.log4j</groupId>
                    <artifactId>log4j-to-slf4j</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>ch.qos.logback</groupId>
                    <artifactId>logback-classic</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-logging</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        
        <dependency>
            <groupId>org.apache.storm</groupId>
            <artifactId>storm-core</artifactId>
            <version>${storm.version}</version>
            <scope>compile</scope>
        </dependency>
        
- 打包方式，传统jar包方式这里不细说，生产环境多是依赖，配置文件，核心jar包分离模式.<br>
       
       <build>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <!--打包时排除掉资源文件-->
                <excludes>
                    <exclude>**/**</exclude>
                </excludes>
            </resource>
        </resources>
        <plugins>
            <plugin>
                <!--打包时去除第三方依赖-->
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <mainClass>com.jun.springbootstorm.Application</mainClass>
                    <layout>ZIP</layout>
                    <includes>
                        <include>
                            <groupId>non-exists</groupId>
                            <artifactId>non-exists</artifactId>
                        </include>
                    </includes>
                </configuration>
            </plugin>
            <!--拷贝第三方依赖文件到指定目录-->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-dependency-plugin</artifactId>
                <executions>
                    <execution>
                        <id>copy-dependencies</id>
                        <phase>package</phase>
                        <goals>
                            <goal>copy-dependencies</goal>
                        </goals>
                        <configuration>
                            <!--target/3rd-libs是依赖jar包的输出目录，根据自己喜好配置-->
                            <outputDirectory>target/libs</outputDirectory>
                            <excludeTransitive>false</excludeTransitive>
                            <stripVersion>false</stripVersion>
                            <includeScope>runtime</includeScope>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
            <!--maven打包时，跳过测试-->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <configuration>
                    <skip>true</skip>
                </configuration>
            </plugin>
        </plugins>
      </build>
      
springboot-storm.sh 启动.
    

# storm集群模式

- 以storm 方式启动，springboot作为storm 容器下的容器，在storm分发拓扑后，再启动spring上下文.


- 参考：springboot-storm-cluster.sh<br>

      STORM_PATH=${HOME}/apache-storm-1.2.2
      JAR_PATH=${HOME}/springboot-storm/original-springboot-storm-0.0.1-SNAPSHOT.jar
      MAIN_CLASS="com.jun.springbootstorm.TopologyApp"
      TOPOLOGY_NAME="springboot-storm"
      TOPOLOGY_WORKER="1"
      TOPOLOGY_ACKER="1"
      ${STORM_PATH}/bin/storm jar ${JAR_PATH} ${MAIN_CLASS} ${TOPOLOGY_NAME} ${TOPOLOGY_WORKER} ${TOPOLOGY_ACKER}
      
- 依赖需要打成普通jar.其中original开头jar包为核心jar，以springboot-storm-cluster.sh启动，另外一个依赖jar包需要放到extlib目录下

      <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <createDependencyReducedPom>false</createDependencyReducedPom>
                    <artifactSet>
                        <excludes>
                            <exclude>commons-logging:commons-logging</exclude>
                            <exclude>javax.servlet:servlet-api</exclude>
                            <exclude>javax.mail:javax.mail-api</exclude>
                        </excludes>
                    </artifactSet>
                </configuration>
            </plugin>
        </plugins>
      </build>
      
 - 代码较之单机版本，有所改变，storm通过nimbus发布拓扑，并序列化，Supervisor通过zookeeper取得拓扑信息，并下载拓扑反序列化，每个spot和bolt都是单独的进程。为了在集群模式获取spring bean，可以通过工具类SpringBeanUtil 取得上下文。
 
        public class SpringBeanUtil implements ApplicationContextAware{
          	@Override
	          public void setApplicationContext(ApplicationContext context) throws BeansException {
		          if (SpringBeanUtil.context == null) {
			          SpringBeanUtil.context = context;
		          }
	          }
        ...
- 在springboot 启动类设置上下文

        public synchronized static void run(String ...args) {
            if(SpringBootApplication.context == null) {
            System.out.println("SpringBootApplication 开始启动");
            try {
                SpringApplication app = new SpringApplication(SpringBootApplication.class);
                app.setWebApplicationType(WebApplicationType.NONE);
                app.setBannerMode(Banner.Mode.OFF);
                app.setLogStartupInfo(false);
                context = app.run(args);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("SpringBootApplication 启动失败：" + e.getMessage());
            }
          }
          
- 而在bolt的prepare 方法和spot中的 open方法使用就可以了

        @Override
	      public void open(Map map, TopologyContext arg1, SpoutOutputCollector collector) {
		        PreprocessTopologyApplication.run();
		        app = SpringBeanUtil.getBean(ApplicationConfiguration.class);
            
 # 实测效果
 
      Thread-6-INSERT_MYSQL_BOLT-executor[2 2] [INFO] Preparing bolt INSERT_MYSQL_BOLT:(2)
      Thread-20-INSERT_REDIS_BOLT-executor[4 4] [INFO] Preparing bolt INSERT_REDIS_BOLT:(4)
      Thread-8-INSERT_SPOUT-executor[7 7] [INFO] Opening spout INSERT_SPOUT:(7)
      Thread-10-INSERT_MYSQL_BOLT-executor[3 3] [INFO] Preparing bolt INSERT_MYSQL_BOLT:(3)
      Thread-12-INSERT_HBASE_BOLT-executor[1 1] [INFO] Preparing bolt INSERT_HBASE_BOLT:(1)
      Thread-14-INSERT_SPOUT-executor[6 6] [INFO] Opening spout INSERT_SPOUT:(6)
      InsertSpout Thread-14-INSERT_SPOUT-executor[6 6] [INFO] 未拉取到数据...
      InsertSpout Thread-8-INSERT_SPOUT-executor[7 7] [INFO] 接收到数据:[{"userId": "1","userName": "jun","userAge": "27"}]

