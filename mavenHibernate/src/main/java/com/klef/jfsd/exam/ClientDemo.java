package com.klef.jfsd.exam;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class ClientDemo {
    public static void main(String[] args) {
        Configuration config = new Configuration();
        config.configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            Transaction tx = session.beginTransaction();

            Customer c1 = new Customer();
            c1.setName("sania");
            c1.setEmail("sania@gmail.com");
            c1.setAge(25);
            c1.setLocation("New York");

            Customer c2 = new Customer();
            c2.setName("lakia");
            c2.setEmail("lakia@gmail.com");
            c2.setAge(30);
            c2.setLocation("Los Angeles");

            Customer c3 = new Customer();
            c3.setName("heloo");
            c3.setEmail("hello@gmail.com");
            c3.setAge(35);
            c3.setLocation("Chicago");

            session.save(c1);
            session.save(c2);
            session.save(c3);

            tx.commit();

            System.out.println("Records inserted successfully.");

            System.out.println("\nCustomers from New York:");
            Criteria criteria = session.createCriteria(Customer.class);
            criteria.add(Restrictions.eq("location", "New York"));
            @SuppressWarnings("unchecked")
            List<Customer> customers = (List<Customer>) criteria.list();
            if (customers != null && !customers.isEmpty()) {
                customers.forEach(c -> System.out.println(c.getName()));
            } else {
                System.out.println("No customers found.");
            }

            System.out.println("\nCustomers older than 30:");
            criteria = session.createCriteria(Customer.class);
            criteria.add(Restrictions.gt("age", 30));
            customers = (List<Customer>) criteria.list();
            if (customers != null && !customers.isEmpty()) {
                customers.forEach(c -> System.out.println(c.getName()));
            } else {
                System.out.println("No customers found.");
            }

            System.out.println("\nCustomers with email ending in 'example.com':");
            criteria = session.createCriteria(Customer.class);
            criteria.add(Restrictions.like("email", "%example.com"));
            customers = (List<Customer>) criteria.list();
            if (customers != null && !customers.isEmpty()) {
                customers.forEach(c -> System.out.println(c.getName()));
            } else {
                System.out.println("No customers found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
        }
    }
}
