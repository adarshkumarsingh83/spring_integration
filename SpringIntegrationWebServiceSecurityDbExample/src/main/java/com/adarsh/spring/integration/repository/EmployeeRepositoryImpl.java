package com.adarsh.spring.integration.repository;

import com.adarsh.spring.integration.entities.Employee;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Adarsh on 3/27/15.
 */
@Repository(value = "employeeRepositoryImpl")
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private static final Logger logger = Logger.getLogger(EmployeeRepositoryImpl.class);

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    @Override
    public Employee getEmployeeById(Integer employeeId) {
        final Object result = openSession().load(Employee.class, employeeId);
        if (result != null) {
            logger.info("Employee Found For "+employeeId);
            return (Employee) result;
        }
        return null;
    }

    @Override
    public List<Employee> getEmployeeList(){
        final Session session=this.openSession();
        Criteria criteria = session.createCriteria(Employee.class);
        final List<Employee>employeeList= criteria.list();
        logger.info("Employee Found in List "+employeeList);
        return employeeList;
    }

    private final Session openSession() {
        return sessionFactory.getCurrentSession();
    }
}
