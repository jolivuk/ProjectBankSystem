package bank.app.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan("bank.app")
@EnableTransactionManagement
public class MySQL_Connection {
    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource source = new ComboPooledDataSource();
        try {
            source.setDriverClass("com.mysql.cj.jdbc.Driver");
            source.setJdbcUrl("jdbc:mysql://localhost:3306/BankSystem?useSSL=false");
            source.setUser("root");
            source.setPassword("1111");
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        return source;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean session = new LocalSessionFactoryBean();
        session.setDataSource(dataSource());
        session.setPackagesToScan("bank.app.entity");
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        session.setHibernateProperties(hibernateProperties);

        return session;
    }

    @Bean
    public HibernateTransactionManager manager (){
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory().getObject());
        return hibernateTransactionManager;
    }
}
