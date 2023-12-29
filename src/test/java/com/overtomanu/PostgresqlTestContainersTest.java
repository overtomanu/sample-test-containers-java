package com.overtomanu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.slf4j.Logger;

/*
Other useful articles:
https://proitcsolution.com.ve/how-to-use-testcontainers-with-integration-tests/
https://github.com/JuanMorenoDeveloper/testcontainers-samples/blob/master/src/test/java/uy/com/proitc/usecases/postgresql/DefaultRepositoryIntegrationTest.java
https://testcontainers.com/guides/getting-started-with-testcontainers-for-java/
*/

@Testcontainers
public class PostgresqlTestContainersTest {

    private static final Logger log = LoggerFactory.getLogger(PostgresqlTestContainersTest.class);

    @Container
    // This postgres instance will be shared between tests
    // as the variable is declared static
    static final PostgreSQLContainer<?> postgreDBContainer = new PostgreSQLContainer<>("postgres:12")
            .withInitScript("employee_department.sql");

    private static final class DbConnectionProviderHolder {
        static final DBConnectionProvider dbConnectionProvider = new DBConnectionProvider(postgreDBContainer.getJdbcUrl(), postgreDBContainer.getUsername(), postgreDBContainer.getPassword());
    }

    @BeforeEach
    void setup(){
    }

    @Test
    public void dbInsertAndQueryTest() {

        try (Connection conn = DbConnectionProviderHolder.dbConnectionProvider.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
        """
            select
                e.first_name||' '||e.last_name as employee_name,
                e.email,
                d.department_id,
                d."name" as department_name
             from employees e
                left join departments d on e.department_id = d.department_id
             """
            );
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                log.info(String.format("%30s %30s %10s %20s", rs.getObject(1), rs.getObject(2), rs.getObject(3), rs.getObject(4)));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
