//mybatis에서 pageable 쓸 수 있게

@Configuration
public class MyBatisConfig {

    @Bean
    public PageInterceptor pageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        // 필요한 설정을 추가할 수 있습니다.
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }

    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return configuration -> {
            configuration.addInterceptor(pageInterceptor());
        };
    }

}
